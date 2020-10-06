workspace(name = "bazel_multideps")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# bazel-skylib 0.8.0 released 2019.03.20 (https://github.com/bazelbuild/bazel-skylib/releases/tag/0.8.0)
skylib_version = "0.8.0"
http_archive(
    name = "bazel_skylib",
    type = "tar.gz",
    url = "https://github.com/bazelbuild/bazel-skylib/releases/download/{}/bazel-skylib.{}.tar.gz".format (skylib_version, skylib_version),
    sha256 = "2ef429f5d7ce7111263289644d233707dba35e39696377ebab8b0bc701f7818e",
)

rules_scala_version="a2f5852902f5b9f0302c727eead52ca2c7b6c3e2" # update this as needed

http_archive(
    name = "io_bazel_rules_scala",
    strip_prefix = "rules_scala-%s" % rules_scala_version,
    type = "zip",
    url = "https://github.com/bazelbuild/rules_scala/archive/%s.zip" % rules_scala_version,
    sha256 = "8c48283aeb70e7165af48191b0e39b7434b0368718709d1bced5c3781787d8e7",
)

# local_repository(
#     name = "io_bazel_rules_scala",
#     path = "../bazelbuild/rules_scala"
# )

# load("@io_bazel_rules_scala//:version.bzl", "bazel_version")
# bazel_version(name = "bazel_version")

load("@io_bazel_rules_scala//scala:toolchains.bzl", "scala_register_toolchains")
scala_register_toolchains()

load("@io_bazel_rules_scala//scala:scala.bzl", "scala_repositories")
scala_repositories()

protobuf_version="3.11.3"
protobuf_version_sha256="cf754718b0aa945b00550ed7962ddc167167bd922b842199eeb6505e6f344852"

http_archive(
    name = "com_google_protobuf",
    url = "https://github.com/protocolbuffers/protobuf/archive/v%s.tar.gz" % protobuf_version,
    strip_prefix = "protobuf-%s" % protobuf_version,
    sha256 = protobuf_version_sha256,
)

# Dependencies needed for google_protobuf.
# You may need to modify this if your project uses google_protobuf for other purposes.
load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")
protobuf_deps()


# For bazel-multideps
load("//:3rdparty/jvm_deps.bzl", "jvm_deps")
jvm_deps()
load("@maven//:jvm_deps.bzl", "load_jvm_deps")
load_jvm_deps()

