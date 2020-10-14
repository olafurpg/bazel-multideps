package tests.commands

import java.nio.file.Paths

import moped.internal.console.Utils

class ExportCommandSuite extends tests.BaseSuite {

  def bazelWorkspace: String = {
    new StringBuilder()
      .append("/WORKSPACE\n")
      .append(Utils.readFile(Paths.get("WORKSPACE")))
      .append("/.bazeliskrc\n")
      .append(Utils.readFile(Paths.get(".bazeliskrc")))
      .append("/BUILD\n")
      .append("# Empty package")
      .toString()
  }

  checkOutput(
    "basic",
    arguments = List("export", "--no-use-ansi-output"),
    expectedOutput =
      """|info: generated: /workingDirectory/3rdparty/jvm_deps.bzl
         |""".stripMargin,
    workingDirectoryLayout = s"""|/3rdparty.yaml
                                 |scala: 2.12.12
                                 |dependencies:
                                 |  - dependency: com.google.guava:guava:29.0-jre
                                 |    crossVersions:
                                 |      - name: old
                                 |        version: 27.1-jre
                                 |  - dependency: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
                                 |$bazelWorkspace
                                 |""".stripMargin
  )

}
