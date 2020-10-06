package multideps.outputs

import org.typelevel.paiges.Doc

final case class DepsOutput(
    artifacts: Seq[ArtifactOutput]
) {
  def render: String = {
    val width = 120
    val httpFiles = Doc
      .intercalate(Doc.line, artifacts.map(_.httpFile.toDoc))
      .nested(4)
      .render(width)
    val builds = Doc
      .intercalate(Docs.blankLine, artifacts.map(_.build))
      .render(width)
    s"""# DO NOT EDIT: this file is auto-generated
def _jvm_deps_impl(ctx):
    content = '''
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_jvm_deps():
    $httpFiles

'''
    ctx.file("jvm_deps.bzl", content, executable = False)
    build_content = '''
load(\"@io_bazel_rules_scala//scala:scala_import.bzl\", \"scala_import\")

$builds
'''
    ctx.file("BUILD", build_content, executable = False)

jvm_deps_rule = repository_rule(
    implementation = _jvm_deps_impl,
)
def jvm_deps():
  jvm_deps_rule(name = "maven")
""".stripMargin
  }
}
