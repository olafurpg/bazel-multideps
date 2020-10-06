def _http_files_impl(ctx):
    content = '''
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_http_files():
    http_file(
      name = "org_mockito_mockito_all_1_10_19_jar",
      urls = ["https://repo1.maven.org/maven2/org/mockito/mockito-all/1.10.19/mockito-all-1.10.19.jar"],
      sha256 = "d1a7a7ef14b3db5c0fc3e0a63a81b374b510afe85add9f7984b97911f4c70605",
    )

'''
    ctx.file("deps.bzl", content, executable = False)
    build_content = '''
package(default_visibility = [\"//visibility:public\"])

genrule(
  name = "org_mockito_mockito_all_1_10_19_extension",
  srcs = ["@org_mockito_mockito_all_1_10_19_jar//file"],
  outs = ["@maven//:v1/org_mockito_mockito_all_1_10_19.jar"],
  cmd = "cp $< $@",
)

java_import(
  name = "org_mockito_mockito_all_1_10_19",
  jars = ["@maven//:v1/org_mockito_mockito_all_1_10_19.jar"],
)
'''
    ctx.file("BUILD", build_content, executable = False)

http_files = repository_rule(
    implementation = _http_files_impl,
)


def all_deps():
  http_files(name = "maven")
