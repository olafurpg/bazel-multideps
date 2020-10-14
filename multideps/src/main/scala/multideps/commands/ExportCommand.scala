package multideps.commands

import java.io.PrintWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration
import java.{util => ju}
import coursier.version.VersionCompatibility

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext
import scala.util.Try

import multideps.configs.ThirdpartyConfig
import multideps.diagnostics.ConflictingTransitiveDependencyDiagnostic
import multideps.diagnostics.MultidepsEnrichments._
import multideps.loggers._
import multideps.outputs.ArtifactOutput
import multideps.outputs.DepsOutput
import multideps.outputs.ResolutionIndex
import multideps.resolvers.CoursierThreadPools
import multideps.resolvers.ResolvedDependency
import multideps.resolvers.Sha256

import coursier.cache.CachePolicy
import coursier.cache.FileCache
import coursier.paths.Util
import coursier.util.Task
import moped.annotations.CommandName
import moped.annotations._
import moped.cli.Application
import moped.cli.Command
import moped.cli.CommandParser
import moped.json.DecodingResult
import moped.json.ErrorResult
import moped.json.ValueResult
import moped.progressbars.InteractiveProgressBar
import moped.progressbars.ProgressRenderer
import moped.reporters.Diagnostic
import moped.reporters.Input
import moped.reporters.NoPosition

@CommandName("export")
case class ExportCommand(
    useAnsiOutput: Boolean = Util.useAnsiOutput(),
    app: Application = Application.default
) extends Command {
  def run(): Int = {
    app.complete(runResult())
  }
  def runResult(): DecodingResult[Unit] = {
    parseThirdpartyConfig().flatMap(t => runResult(t))
  }
  def runResult(thirdparty: ThirdpartyConfig): DecodingResult[Unit] = {
    withThreadPool[DecodingResult[Unit]] { threads =>
      val cache: FileCache[Task] = FileCache().noCredentials
        .withCachePolicies(List(CachePolicy.FetchMissing))
        .withPool(threads.downloadPool)
        .withChecksums(Nil)
      // .withLocation(
      //   app.env.workingDirectory.resolve("target").resolve("16cache").toFile
      // )
      for {
        index <- resolveDependencies(thirdparty, cache)
        _ <- lintPostResolution(index)
        output <- unifyDependencies(index, cache)
        _ = app.info(s"generated: $output")
        lint <- LintCommand()
          .copy(
            queryExpressions = List("@maven//:all"),
            app = app
          )
          .runResult()
      } yield lint
    }
  }

  private def parseThirdpartyConfig(): DecodingResult[ThirdpartyConfig] = {
    val configPath =
      app.env.workingDirectory.resolve("3rdparty.yaml")
    if (!Files.isRegularFile(configPath)) {
      ErrorResult(
        Diagnostic.error(
          s"no such file: $configPath\n\tTo fix this problem, change your working directory or create this file"
        )
      )
    } else {
      ThirdpartyConfig.parseYaml(Input.path(configPath))
    }
  }

  private val schemes = Map[VersionCompatibility, String](
    VersionCompatibility.Always -> "always",
    VersionCompatibility.Default -> "default",
    VersionCompatibility.EarlySemVer -> "early-semver",
    VersionCompatibility.SemVerSpec -> "semver-spec",
    VersionCompatibility.PackVer -> "pvp",
    VersionCompatibility.Strict -> "strict"
  )

  private def resolveDependencies(
      thirdparty: ThirdpartyConfig,
      cache: FileCache[Task]
  ): DecodingResult[ResolutionIndex] = {
    val deps = thirdparty.coursierDeps
    val progressBar = new ResolveProgressRenderer(deps.length)
    val resolveResults = deps.map {
      case (dep, cdep) =>
        thirdparty.toResolve(dep, cache, progressBar, cdep)
    }
    for {
      resolves <- DecodingResult.fromResults(resolveResults)
      resolutions <- DecodingResult.fromResults(
        runParallelTasks(
          resolves.map(_.io.toDecodingResult),
          progressBar,
          cache.ec
        )
      )
    } yield ResolutionIndex.fromResolutions(thirdparty, resolutions)
  }

  def unifyDependencies(
      index: ResolutionIndex,
      cache: FileCache[Task]
  ): DecodingResult[Path] = {
    val artifacts = index.resolutions
      .flatMap(_.dependencyArtifacts().map {
        case (d, p, a) => ResolvedDependency(d, p, a)
      })
      .distinctBy(_.dependency.repr)
    val outputs = new ju.HashMap[String, ArtifactOutput]
    val progressBar = new DownloadProgressRenderer(artifacts.length)
    val files = artifacts.map { r =>
      val logger = progressBar.loggers.newCacheLogger(r.dependency)
      cache.withLogger(logger).file(r.artifact).run.map {
        case Right(file) =>
          Try {
            val output = ArtifactOutput(
              index = index,
              outputs = outputs.asScala,
              dependency = r.dependency,
              artifact = r.artifact,
              artifactSha256 = Sha256.compute(file)
            )
            outputs.put(r.dependency.repr, output)
            output
          }.toEither

        case Left(value) => Left(value)
      }
    }
    val all = runParallelTasks(files, progressBar, cache.ec)
    val errors = all.collect { case Left(error) => Diagnostic.exception(error) }
    Diagnostic.fromDiagnostics(errors.toList) match {
      case Some(error) =>
        ErrorResult(error)
      case None =>
        val artifacts = all.collect { case Right(a) => a }
        val rendered = DepsOutput(artifacts).render
        val out =
          app.env.workingDirectory.resolve("3rdparty").resolve("jvm_deps.bzl")
        Files.createDirectories(out.getParent())
        Files.write(out, rendered.getBytes(StandardCharsets.UTF_8))
        ValueResult(out)
    }
  }

  def lintPostResolution(index: ResolutionIndex): DecodingResult[Unit] = {
    // return ValueResult(())
    val errors = for {
      (module, versions) <- index.artifacts.toList
      if versions.size > 1
      diagnostic <- index.thirdparty.depsByModule.get(module) match {
        case Some(declaredDeps) =>
          val allDeclaredVersions = declaredDeps.flatMap(_.allVersions)
          val unspecified =
            (versions.map(_.version) -- allDeclaredVersions).toList
          unspecified match {
            case Nil =>
              Nil
            case _ =>
              List(
                new ConflictingTransitiveDependencyDiagnostic(
                  module,
                  unspecified.toList,
                  allDeclaredVersions,
                  versions.iterator.flatMap(index.roots.get(_)).flatten.toList,
                  NoPosition
                )
              )
          }
        case None =>
          Nil
      }
      if diagnostic.declaredVersions.nonEmpty
    } yield diagnostic
    Diagnostic.fromDiagnostics(errors) match {
      case Some(diagnostic) => ErrorResult(diagnostic)
      case None => ValueResult(())
    }
  }

  private def withThreadPool[T](fn: CoursierThreadPools => T): T = {
    val threads = new CoursierThreadPools()
    try fn(threads)
    finally threads.close()
  }
  private def withProgressBar[T](renderer: ProgressRenderer)(thunk: => T): T = {
    val out = new PrintWriter(app.err)
    val p =
      if (useAnsiOutput)
        new InteractiveProgressBar(
          out = out,
          renderer = renderer,
          intervalDuration = Duration.ofMillis(100),
          terminal = app.terminal
        )
      else {
        new StaticProgressBar(
          renderer = renderer,
          out = out,
          terminal = app.terminal
        )
      }
    try {
      p.start()
      thunk
    } finally {
      p.stop()
    }
  }

  private def runParallelTasks[T](
      tasks: List[Task[T]],
      r: ProgressRenderer,
      ec: ExecutionContext
  ): Seq[T] =
    withProgressBar(r) {
      Task.gather.gather(tasks).unsafeRun()(ec)
    }

}

object ExportCommand {
  val default = new ExportCommand()
  implicit val parser: CommandParser[ExportCommand] =
    CommandParser.derive[ExportCommand](default)
}
