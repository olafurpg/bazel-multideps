package multideps.commands

import java.io.PrintWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger
import java.{util => ju}

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

import coursier.Resolve
import coursier.cache.CachePolicy
import coursier.cache.FileCache
import coursier.error.ResolutionError
import coursier.params.ResolutionParams
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
import moped.progressbars.ProgressBar
import moped.reporters.Diagnostic
import moped.reporters.Input
import moped.reporters.NoPosition
import moped.progressbars.ProgressRenderer

@CommandName("export")
case class ExportCommand(
    useAnsiOutput: Boolean = Util.useAnsiOutput(),
    quiet: Boolean = false,
    app: Application = Application.default
) extends Command {
  def runResult(thirdparty: ThirdpartyConfig): DecodingResult[Unit] = {
    val threads = new CoursierThreadPools()
    val cache: FileCache[Task] = FileCache().noCredentials
      .withCachePolicies(List(CachePolicy.FetchMissing))
      .withPool(threads.downloadPool)
      .withChecksums(Nil)
      .withLocation(
        app.env.workingDirectory.resolve("target").resolve("16cache").toFile
      )
    try {
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
    } finally {
      threads.close()
    }
  }
  def runResult(): DecodingResult[Unit] = {
    parseThirdpartyConfig().flatMap(t => runResult(t))
  }
  def run(): Int = {
    app.complete(runResult())
  }

  def parseThirdpartyConfig(): DecodingResult[ThirdpartyConfig] = {
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

  def resolveDependencies(
      thirdparty: ThirdpartyConfig,
      cache: FileCache[Task]
  ): DecodingResult[ResolutionIndex] = {
    val counter = new AtomicInteger(0)
    val N = thirdparty.dependencies.size
    val coursierDeps = (for {
      dep <- thirdparty.dependencies
      cdep <- dep.coursierDependencies(thirdparty.scala)
    } yield dep -> cdep).distinctBy(_._2)
    val r = new ResolveProgressRenderer(coursierDeps.length)
    val p = newProgressBar(r)
    p.start()
    val maxWidth =
      if (coursierDeps.isEmpty) 0
      else coursierDeps.map(_._2.repr.length()).max
    val total = coursierDeps.length
    try {
      val resolves = coursierDeps.map {
        case (dep, cdep) =>
          val forceVersions = dep.forceVersions.overrides.map {
            case (module, version) =>
              thirdparty.depsByModule.get(module.coursierModule) match {
                case Some(depsConfig) =>
                  depsConfig.getVersion(version) match {
                    case Some(forcedVersion) =>
                      ValueResult(
                        depsConfig.coursierModule(
                          thirdparty.scala
                        ) -> forcedVersion
                      )
                    case None =>
                      // TODO: report "did you mean?"
                      ErrorResult(
                        Diagnostic.error(
                          s"version '$version' not found",
                          module.name.position
                        )
                      )
                  }
                case None =>
                  ErrorResult(
                    Diagnostic.error(
                      s"module '${module.repr}' not found",
                      module.name.position
                    )
                  )
              }
          }
          for {
            forceVersions <- DecodingResult.fromResults(forceVersions)
          } yield Resolve(
            cache.withLogger(r.loggers.newCacheLogger(cdep))
          )
            .addDependencies(cdep)
            .withResolutionParams(
              ResolutionParams().addForceVersion(forceVersions: _*)
            )
            .addRepositories(
              thirdparty.repositories.flatMap(_.coursierRepository): _*
            )
      }

      for {
        rs <- DecodingResult.fromResults(resolves)
        resolutions <- {
          val tasks = rs.map(_.io.map(ValueResult(_)).handle {
            case ex: ResolutionError =>
              ErrorResult(Diagnostic.error(ex.getMessage()))
          })
          DecodingResult.fromResults(
            Task.gather.gather(tasks).unsafeRun()(cache.ec)
          )
        }
      } yield {
        ResolutionIndex.fromResolutions(thirdparty, resolutions)
      }
    } finally {
      p.stop()
    }
  }

  private def newProgressBar(renderer: ProgressRenderer): ProgressBar = {
    val out = new PrintWriter(app.err)
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
  }

  def unifyDependencies(
      index: ResolutionIndex,
      cache: FileCache[Task]
  ): DecodingResult[Path] = {
    // Step 1: download artifacts
    val artifacts = index.resolutions
      .flatMap(_.dependencyArtifacts().map {
        case (d, p, a) => ResolvedDependency(d, p, a)
      })
      .distinctBy(_.dependency.repr)
    val outputs = new ju.HashMap[String, ArtifactOutput]
    val counter = new AtomicInteger(0)
    val N = artifacts.size
    val pr = new DownloadProgressRenderer(artifacts.length)
    val p = newProgressBar(pr)
    p.start()
    val all: Seq[Either[Throwable, ArtifactOutput]] =
      try {
        val files = artifacts.map { r =>
          val logger = pr.loggers.newCacheLogger(r.dependency)
          cache.withLogger(logger).file(r.artifact).run.map {
            case Right(file) =>
              Try {
                import scala.collection.JavaConverters._
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
        Task.gather.gather(files).unsafeRun()(cache.ec)
      } finally {
        p.stop()
      }
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

  def lintPostGeneration(index: ResolutionIndex): Unit = {}
  def lintPostResolution(index: ResolutionIndex): DecodingResult[Unit] = {
    // return ValueResult(())
    val errors = for {
      (module, versions) <- index.artifacts.toList
      if versions.size > 1
      diagnostic <- index.thirdparty.depsByModule.get(module) match {
        case Some(declared) =>
          val unspecified =
            (versions.map(_.version) -- declared.allVersions).toList
          unspecified match {
            case Nil =>
              Nil
            case _ =>
              List(
                new ConflictingTransitiveDependencyDiagnostic(
                  module,
                  unspecified.toList,
                  declared.allVersions,
                  versions.iterator.flatMap(index.roots.get(_)).flatten.toList,
                  declared.organization.position
                )
              )
          }
        case None =>
          List(
            new ConflictingTransitiveDependencyDiagnostic(
              module,
              versions.map(_.version).toList,
              Nil,
              versions.iterator.flatMap(index.roots.get(_)).flatten.toList,
              NoPosition
            )
          )
      }
      if diagnostic.declaredVersions.nonEmpty
    } yield diagnostic
    Diagnostic.fromDiagnostics(errors) match {
      case Some(diagnostic) => ErrorResult(diagnostic)
      case None => ValueResult(())
    }
  }

}

object ExportCommand {
  val default = new ExportCommand()
  implicit val parser: CommandParser[ExportCommand] =
    CommandParser.derive[ExportCommand](default)
}
