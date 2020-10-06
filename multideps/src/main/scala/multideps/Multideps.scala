package multideps

import multideps.commands._

import moped.cli.Application
import moped.cli.CommandParser
import moped.commands.CompletionsCommand
import moped.commands.HelpCommand
import moped.commands.VersionCommand

object Multideps {
  val app: Application = Application.fromName(
    "bm",
    BuildInfo.version,
    List(
      CommandParser[VersionCommand],
      CommandParser[HelpCommand],
      CommandParser[ExportCommand],
      CommandParser[PantsExportCommand],
      CommandParser[LintCommand],
      CommandParser[CompletionsCommand]
    )
  )
  def main(args: Array[String]): Unit = {
    app.runAndExitIfNonZero(args.toList)
  }
}
