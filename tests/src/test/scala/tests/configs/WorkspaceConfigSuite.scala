package tests.configs

import java.io.ByteArrayOutputStream
import java.io.PrintStream

import multideps.configs._

import moped.json.ErrorResult
import moped.json.JsonString
import moped.json.ValueResult
import moped.reporters.ConsoleReporter
import moped.reporters.Input
import munit.TestOptions

class WorkspaceConfigSuite extends munit.FunSuite {
  val out = new ByteArrayOutputStream()
  val reporter: ConsoleReporter = ConsoleReporter(new PrintStream(out))
  private def parseConfig(
      name: TestOptions,
      text: String,
      onSuccess: ThirdpartyConfig => Unit,
      onError: String => Unit
  ): Unit = {
    out.reset()
    reporter.reset()
    ThirdpartyConfig.parseYaml(
      Input.filename(name.name + ".yaml", text)
    ) match {
      case ValueResult(value) => onSuccess(value)
      case ErrorResult(error) =>
        reporter.log(error)
        onError(out.toString())
    }
  }
  def check(
      name: TestOptions,
      original: String,
      expected: ThirdpartyConfig
  )(implicit loc: munit.Location): Unit = {
    test(name) {
      parseConfig(
        name,
        original,
        onSuccess = { obtained => assertEquals(obtained, expected) },
        onError = { error =>
          fail(error)
        }
      )
    }
  }

  def checkError(
      name: TestOptions,
      original: String,
      expected: String
  )(implicit loc: munit.Location): Unit = {
    test(name) {
      parseConfig(
        name,
        original,
        onSuccess = { obtained =>
          fail(s"expected an error but parsed successfully:\n$obtained")
        },
        onError = { obtained =>
          assertNoDiff(obtained, expected)
        }
      )
    }
  }

  check(
    "basic",
    """|scala: 2.12.12
       |dependencies:
       |  - organization: org.scalameta
       |    name: munit
       |    version: 0.7.13
       |    lang: scala
       |""".stripMargin,
    ThirdpartyConfig(
      scala = VersionsConfig("2.12.12"),
      dependencies = List(
        DependencyConfig(
          organization = JsonString("org.scalameta"),
          name = "munit",
          version = "0.7.13",
          lang = ScalaLanguagesConfig
        )
      )
    )
  )

  check(
    "custom-version",
    """|dependencies:
       |  - organization: org.scalameta
       |    name: munit
       |    version: "0.7.13"
       |    lang: scala
       |""".stripMargin,
    ThirdpartyConfig(
      dependencies = List(
        DependencyConfig(
          organization = JsonString("org.scalameta"),
          name = "munit",
          version = "0.7.13",
          lang = ScalaLanguagesConfig
        )
      )
    )
  )

  checkError(
    "typo",
    """|deps:
       |  - organization: org.scalameta
       |    name: munit
       |    version: "0.7.13"
       |    lang: scala
       |""".stripMargin,
    """|typo.yaml:1 error: unknown field name 'deps'
       |deps:
       |^
       |""".stripMargin
  )

  check(
    "cross-library",
    """|dependencies:
       |  - dependency: com.google.guava:guava:29.0-jre
       |    crossVersions:
       |      - name: old
       |        version: 25.0-jre
       |        forceVersions:
       |          - com.google.guava:guava:old
       |""".stripMargin,
    ThirdpartyConfig(
      dependencies = List(
        DependencyConfig(
          organization = JsonString("com.google.guava"),
          name = "guava",
          version = "29.0-jre",
          crossVersions = List(
            CrossVersionsConfig(
              name = JsonString("old"),
              version = JsonString("25.0-jre"),
              forceVersions = ForceVersionsConfig(
                Map(ModuleConfig("com.google.guava", "guava") -> "old")
              )
            )
          )
        )
      )
    )
  )
}
