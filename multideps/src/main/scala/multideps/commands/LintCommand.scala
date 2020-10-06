package multideps.commands

import scala.collection.JavaConverters._

import multideps.diagnostics.MultidepsEnrichments._
import multideps.indexes.DependenciesIndex
import multideps.indexes.TargetIndex
import multideps.resolvers.SimpleDependency

import moped.annotations.CommandName
import moped.annotations.PositionalArguments
import moped.cli.Application
import moped.cli.Command
import moped.cli.CommandParser
import moped.json.DecodingResult
import moped.json.ValueResult
import moped.reporters.Diagnostic
import org.scalameta.bazel_multideps.Build.QueryResult
import org.scalameta.bazel_multideps.Build.Target

@CommandName("lint")
case class LintCommand(
    @PositionalArguments queryExpressions: List[String] = Nil,
    app: Application = Application.default
) extends Command {
  private def runQuery(queryExpression: String): QueryResult = {
    val command = List(
      "bazel",
      "query",
      "--notool_deps",
      "--noimplicit_deps",
      queryExpression,
      "--output=proto"
    )
    val process = os
      .proc(command)
      .call(cwd = os.Path(app.env.workingDirectory), stderr = os.Pipe)
    QueryResult.parseFrom(process.out.bytes)
  }

  def run(): Int = app.complete(runResult())

  def runResult(): DecodingResult[Unit] = {
    val expr = queryExpressions.mkString(" ")
    val result = runQuery(s"deps($expr)")
    val roots =
      runQuery(expr).getTargetList().asScala.map(_.getRule().getName())
    val index = new DependenciesIndex(result)
    roots.foreach { root =>
      val deps = index.dependencies(root)
      val errors = deps.groupBy(_.dependency.map(_.module)).collect {
        case (Some(dep), ts) if ts.size > 1 =>
          dep -> ts.collect {
            case TargetIndex(_, _, _, Some(dep)) => dep.version
          }
      }
      val isTransitive = errors.toList.flatMap {
        case (m, vs) =>
          for {
            v <- vs
            dep = SimpleDependency(m, v)
            tdep <- index.dependencies(dep)
            if tdep.dependency != Some(dep)
          } yield tdep
      }.toSet
      val redundant = errors.foreach {
        case (module, versions) =>
          val deps = versions
            .map(v => SimpleDependency(module, v))
            .flatMap(index.byDependency.get(_))
          if (!deps.exists(isTransitive)) {
            app.reporter.error(
              s"target '$root' depends on conflicting versions of the 3rdparty dependency '${module.repr}:{${versions.commas}}'.\n" +
                s"\tTo fix this problem, the dependency list of this target so that it only depends on one version of the 3rdparty module '${module.repr}'"
            )
          }
      }
    }
    ValueResult(())
  }

  private def lintTarget(
      t: Target,
      index: DependenciesIndex
  ): Option[Diagnostic] = {
    pprint.log(t.getRule().getName())
    // pprint.log(deps)
    // pprint.log(tags)
    // pprint.log(dependency)
    None
  }

}

object LintCommand {
  val default: LintCommand = LintCommand()
  implicit val parser: CommandParser[LintCommand] =
    CommandParser.derive(default)
}
