# DO NOT EDIT: this file is auto-generated
def _jvm_deps_impl(ctx):
    content = '''
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_jvm_deps():
    http_file(
      name = "com.google.guava_guava_24.1.1-jre",
      urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/24.1.1-jre/guava-24.1.1-jre.jar"],
      sha256 = "490c16878c7a2c22e136728ad473c4190b21b82b46e261ba84ad2e4a5c28fbcf"
    )
    http_file(
      name = "com.google.code.findbugs_jsr305_1.3.9",
      urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar"],
      sha256 = "905721a0eea90a81534abb7ee6ef4ea2e5e645fa1def0a5cd88402df1b46c9ed"
    )
    http_file(
      name = "org.checkerframework_checker-compat-qual_2.0.0",
      urls = [
        "https://repo1.maven.org/maven2/org/checkerframework/checker-compat-qual/2.0.0/checker-compat-qual-2.0.0.jar"
      ],
      sha256 = "a40b2ce6d8551e5b90b1bf637064303f32944d61b52ab2014e38699df573941b"
    )
    http_file(
      name = "com.google.errorprone_error_prone_annotations_2.1.3",
      urls = [
        "https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.1.3/error_prone_annotations-2.1.3.jar"
      ],
      sha256 = "03d0329547c13da9e17c634d1049ea2ead093925e290567e1a364fd6b1fc7ff8"
    )
    http_file(
      name = "com.google.j2objc_j2objc-annotations_1.1",
      urls = ["https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/1.1/j2objc-annotations-1.1.jar"],
      sha256 = "2994a7eb78f2710bd3d3bfb639b2c94e219cedac0d4d084d516e78c16dddecf6"
    )
    http_file(
      name = "org.codehaus.mojo_animal-sniffer-annotations_1.14",
      urls = [
        "https://repo1.maven.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.14/animal-sniffer-annotations-1.14.jar"
      ],
      sha256 = "2068320bd6bad744c3673ab048f67e30bef8f518996fa380033556600669905d"
    )
    http_file(
      name = "com.google.guava_guava_27.1-jre",
      urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/27.1-jre/guava-27.1-jre.jar"],
      sha256 = "4a5aa70cc968a4d137e599ad37553e5cfeed2265e8c193476d7119036c536fe7"
    )
    http_file(
      name = "com.google.guava_failureaccess_1.0.1",
      urls = ["https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.1/failureaccess-1.0.1.jar"],
      sha256 = "a171ee4c734dd2da837e4b16be9df4661afab72a41adaf31eb84dfdaf936ca26"
    )
    http_file(
      name = "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
      urls = [
        "https://repo1.maven.org/maven2/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"
      ],
      sha256 = "b372a037d4230aa57fbeffdef30fd6123f9c0c2db85d0aced00c91b974f33f99"
    )
    http_file(
      name = "com.google.code.findbugs_jsr305_3.0.2",
      urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar"],
      sha256 = "766ad2a0783f2687962c8ad74ceecc38a28b9f72a2d085ee438b7813e928d0c7"
    )
    http_file(
      name = "org.checkerframework_checker-qual_2.5.2",
      urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-qual/2.5.2/checker-qual-2.5.2.jar"],
      sha256 = "64b02691c8b9d4e7700f8ee2e742dce7ea2c6e81e662b7522c9ee3bf568c040a"
    )
    http_file(
      name = "com.google.errorprone_error_prone_annotations_2.2.0",
      urls = [
        "https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.2.0/error_prone_annotations-2.2.0.jar"
      ],
      sha256 = "6ebd22ca1b9d8ec06d41de8d64e0596981d9607b42035f9ed374f9de271a481a"
    )
    http_file(
      name = "org.codehaus.mojo_animal-sniffer-annotations_1.17",
      urls = [
        "https://repo1.maven.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.17/animal-sniffer-annotations-1.17.jar"
      ],
      sha256 = "92654f493ecfec52082e76354f0ebf87648dc3d5cec2e3c3cdb947c016747a53"
    )
    http_file(
      name = "com.google.guava_guava_29.0-jre",
      urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/29.0-jre/guava-29.0-jre.jar"],
      sha256 = "b22c5fb66d61e7b9522531d04b2f915b5158e80aa0b40ee7282c8bfb07b0da25"
    )
    http_file(
      name = "org.checkerframework_checker-qual_2.11.1",
      urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-qual/2.11.1/checker-qual-2.11.1.jar"],
      sha256 = "015224a4b1dc6de6da053273d4da7d39cfea20e63038169fc45ac0d1dc9c5938"
    )
    http_file(
      name = "com.google.errorprone_error_prone_annotations_2.3.4",
      urls = [
        "https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.3.4/error_prone_annotations-2.3.4.jar"
      ],
      sha256 = "baf7d6ea97ce606c53e11b6854ba5f2ce7ef5c24dddf0afa18d1260bd25b002c"
    )
    http_file(
      name = "com.google.j2objc_j2objc-annotations_1.3",
      urls = ["https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/1.3/j2objc-annotations-1.3.jar"],
      sha256 = "21af30c92267bd6122c0e0b4d20cccb6641a37eaf956c6540ec471d584e64a7b"
    )
    http_file(
      name = "org.eclipse.lsp4j_org.eclipse.lsp4j_0.9.0",
      urls = ["https://repo1.maven.org/maven2/org/eclipse/lsp4j/org.eclipse.lsp4j/0.9.0/org.eclipse.lsp4j-0.9.0.jar"],
      sha256 = "3c73ad81b48be9ca2fa5b569577f6662e460d877381de17e54b959b5eea2451a"
    )
    http_file(
      name = "org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0",
      urls = [
        "https://repo1.maven.org/maven2/org/eclipse/lsp4j/org.eclipse.lsp4j.generator/0.9.0/org.eclipse.lsp4j.generator-0.9.0.jar"
      ],
      sha256 = "a2bbb4591613d7c87075e9acdf7a654cf91f0ac11c97bc5aa144291f2a0b7841"
    )
    http_file(
      name = "org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0",
      urls = [
        "https://repo1.maven.org/maven2/org/eclipse/lsp4j/org.eclipse.lsp4j.jsonrpc/0.9.0/org.eclipse.lsp4j.jsonrpc-0.9.0.jar"
      ],
      sha256 = "f4c06e8816063c8b64ba370c2e76b289ad77df877f1d1a5b10dc01f1be3b46f2"
    )
    http_file(
      name = "org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0",
      urls = [
        "https://repo1.maven.org/maven2/org/eclipse/xtend/org.eclipse.xtend.lib/2.19.0/org.eclipse.xtend.lib-2.19.0.jar"
      ],
      sha256 = "4594b99205e120d9e6a9225fcb4632fb317e3d28f881ef500ec5702762ec06f8"
    )
    http_file(
      name = "com.google.code.gson_gson_2.8.2",
      urls = ["https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.2/gson-2.8.2.jar"],
      sha256 = "b7134929f7cc7c04021ec1cc27ef63ab907e410cf0588e397b8851181eb91092"
    )
    http_file(
      name = "org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0",
      urls = [
        "https://repo1.maven.org/maven2/org/eclipse/xtext/org.eclipse.xtext.xbase.lib/2.19.0/org.eclipse.xtext.xbase.lib-2.19.0.jar"
      ],
      sha256 = "d7c6d266717f693e9974b70338fca96cafb58ad63353da9ba7142ccc7b54c513"
    )
    http_file(
      name = "org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0",
      urls = [
        "https://repo1.maven.org/maven2/org/eclipse/xtend/org.eclipse.xtend.lib.macro/2.19.0/org.eclipse.xtend.lib.macro-2.19.0.jar"
      ],
      sha256 = "71b79db96e5349f347f9163382a98a0b3b6ee95780240399403107c90703f296"
    )
    http_file(
      name = "com.google.protobuf_protobuf-java_3.13.0",
      urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.13.0/protobuf-java-3.13.0.jar"],
      sha256 = "97d5b2758408690c0dc276238707492a0b6a4d71206311b6c442cdc26c5973ff"
    )
    http_file(
      name = "com.google.protobuf_protobuf-java_2.6.1",
      urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/2.6.1/protobuf-java-2.6.1.jar"],
      sha256 = "55aa554843983f431df5616112cf688d38aa17c132357afd1c109435bfdac4e6"
    )

'''
    ctx.file("jvm_deps.bzl", content, executable = False)
    build_content = '''
load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")

genrule(
  name = "com.google.guava_guava_24.1.1-jre_extension",
  srcs = ["@com.google.guava_guava_24.1.1-jre//file"],
  outs = ["@maven//:com.google.guava/guava-24.1.1-jre.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.guava_guava_24.1.1-jre",
  jars = ["@maven//:com.google.guava/guava-24.1.1-jre.jar"],
  deps = [
    "com.google.code.findbugs_jsr305_1.3.9", "org.checkerframework_checker-compat-qual_2.0.0",
    "com.google.errorprone_error_prone_annotations_2.1.3", "com.google.j2objc_j2objc-annotations_1.1",
    "org.codehaus.mojo_animal-sniffer-annotations_1.14"
  ],
  exports = [
    "com.google.code.findbugs_jsr305_1.3.9", "org.checkerframework_checker-compat-qual_2.0.0",
    "com.google.errorprone_error_prone_annotations_2.1.3", "com.google.j2objc_j2objc-annotations_1.1",
    "org.codehaus.mojo_animal-sniffer-annotations_1.14"
  ],
  tags = ["jvm_module=com.google.guava:guava", "jvm_version=24.1.1-jre"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.code.findbugs_jsr305_1.3.9_extension",
  srcs = ["@com.google.code.findbugs_jsr305_1.3.9//file"],
  outs = ["@maven//:com.google.code.findbugs/jsr305-1.3.9.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.code.findbugs_jsr305_1.3.9",
  jars = ["@maven//:com.google.code.findbugs/jsr305-1.3.9.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.code.findbugs:jsr305", "jvm_version=1.3.9"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.checkerframework_checker-compat-qual_2.0.0_extension",
  srcs = ["@org.checkerframework_checker-compat-qual_2.0.0//file"],
  outs = ["@maven//:org.checkerframework/checker-compat-qual-2.0.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.checkerframework_checker-compat-qual_2.0.0",
  jars = ["@maven//:org.checkerframework/checker-compat-qual-2.0.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.checkerframework:checker-compat-qual", "jvm_version=2.0.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.errorprone_error_prone_annotations_2.1.3_extension",
  srcs = ["@com.google.errorprone_error_prone_annotations_2.1.3//file"],
  outs = ["@maven//:com.google.errorprone/error_prone_annotations-2.1.3.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.errorprone_error_prone_annotations_2.1.3",
  jars = ["@maven//:com.google.errorprone/error_prone_annotations-2.1.3.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.errorprone:error_prone_annotations", "jvm_version=2.1.3"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.j2objc_j2objc-annotations_1.1_extension",
  srcs = ["@com.google.j2objc_j2objc-annotations_1.1//file"],
  outs = ["@maven//:com.google.j2objc/j2objc-annotations-1.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.j2objc_j2objc-annotations_1.1",
  jars = ["@maven//:com.google.j2objc/j2objc-annotations-1.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.j2objc:j2objc-annotations", "jvm_version=1.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.codehaus.mojo_animal-sniffer-annotations_1.14_extension",
  srcs = ["@org.codehaus.mojo_animal-sniffer-annotations_1.14//file"],
  outs = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.14.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.codehaus.mojo_animal-sniffer-annotations_1.14",
  jars = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.14.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.codehaus.mojo:animal-sniffer-annotations", "jvm_version=1.14"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.guava_guava_27.1-jre_extension",
  srcs = ["@com.google.guava_guava_27.1-jre//file"],
  outs = ["@maven//:com.google.guava/guava-27.1-jre.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.guava_guava_27.1-jre",
  jars = ["@maven//:com.google.guava/guava-27.1-jre.jar"],
  deps = [
    "com.google.guava_failureaccess_1.0.1",
    "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
    "com.google.code.findbugs_jsr305_3.0.2", "org.checkerframework_checker-qual_2.5.2",
    "com.google.errorprone_error_prone_annotations_2.2.0", "com.google.j2objc_j2objc-annotations_1.1",
    "org.codehaus.mojo_animal-sniffer-annotations_1.17"
  ],
  exports = [
    "com.google.guava_failureaccess_1.0.1",
    "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
    "com.google.code.findbugs_jsr305_3.0.2", "org.checkerframework_checker-qual_2.5.2",
    "com.google.errorprone_error_prone_annotations_2.2.0", "com.google.j2objc_j2objc-annotations_1.1",
    "org.codehaus.mojo_animal-sniffer-annotations_1.17"
  ],
  tags = ["jvm_module=com.google.guava:guava", "jvm_version=27.1-jre"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.guava_failureaccess_1.0.1_extension",
  srcs = ["@com.google.guava_failureaccess_1.0.1//file"],
  outs = ["@maven//:com.google.guava/failureaccess-1.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.guava_failureaccess_1.0.1",
  jars = ["@maven//:com.google.guava/failureaccess-1.0.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.guava:failureaccess", "jvm_version=1.0.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava_extension",
  srcs = ["@com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava//file"],
  outs = ["@maven//:com.google.guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
  jars = ["@maven//:com.google.guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.guava:listenablefuture", "jvm_version=9999.0-empty-to-avoid-conflict-with-guava"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.code.findbugs_jsr305_3.0.2_extension",
  srcs = ["@com.google.code.findbugs_jsr305_3.0.2//file"],
  outs = ["@maven//:com.google.code.findbugs/jsr305-3.0.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.code.findbugs_jsr305_3.0.2",
  jars = ["@maven//:com.google.code.findbugs/jsr305-3.0.2.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.code.findbugs:jsr305", "jvm_version=3.0.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.checkerframework_checker-qual_2.5.2_extension",
  srcs = ["@org.checkerframework_checker-qual_2.5.2//file"],
  outs = ["@maven//:org.checkerframework/checker-qual-2.5.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.checkerframework_checker-qual_2.5.2",
  jars = ["@maven//:org.checkerframework/checker-qual-2.5.2.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.checkerframework:checker-qual", "jvm_version=2.5.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.errorprone_error_prone_annotations_2.2.0_extension",
  srcs = ["@com.google.errorprone_error_prone_annotations_2.2.0//file"],
  outs = ["@maven//:com.google.errorprone/error_prone_annotations-2.2.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.errorprone_error_prone_annotations_2.2.0",
  jars = ["@maven//:com.google.errorprone/error_prone_annotations-2.2.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.errorprone:error_prone_annotations", "jvm_version=2.2.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.codehaus.mojo_animal-sniffer-annotations_1.17_extension",
  srcs = ["@org.codehaus.mojo_animal-sniffer-annotations_1.17//file"],
  outs = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.17.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.codehaus.mojo_animal-sniffer-annotations_1.17",
  jars = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.17.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.codehaus.mojo:animal-sniffer-annotations", "jvm_version=1.17"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.guava_guava_29.0-jre_extension",
  srcs = ["@com.google.guava_guava_29.0-jre//file"],
  outs = ["@maven//:com.google.guava/guava-29.0-jre.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.guava_guava_29.0-jre",
  jars = ["@maven//:com.google.guava/guava-29.0-jre.jar"],
  deps = [
    "com.google.guava_failureaccess_1.0.1",
    "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
    "com.google.code.findbugs_jsr305_3.0.2", "org.checkerframework_checker-qual_2.11.1",
    "com.google.errorprone_error_prone_annotations_2.3.4", "com.google.j2objc_j2objc-annotations_1.3"
  ],
  exports = [
    "com.google.guava_failureaccess_1.0.1",
    "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
    "com.google.code.findbugs_jsr305_3.0.2", "org.checkerframework_checker-qual_2.11.1",
    "com.google.errorprone_error_prone_annotations_2.3.4", "com.google.j2objc_j2objc-annotations_1.3"
  ],
  tags = ["jvm_module=com.google.guava:guava", "jvm_version=29.0-jre"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.checkerframework_checker-qual_2.11.1_extension",
  srcs = ["@org.checkerframework_checker-qual_2.11.1//file"],
  outs = ["@maven//:org.checkerframework/checker-qual-2.11.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.checkerframework_checker-qual_2.11.1",
  jars = ["@maven//:org.checkerframework/checker-qual-2.11.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.checkerframework:checker-qual", "jvm_version=2.11.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.errorprone_error_prone_annotations_2.3.4_extension",
  srcs = ["@com.google.errorprone_error_prone_annotations_2.3.4//file"],
  outs = ["@maven//:com.google.errorprone/error_prone_annotations-2.3.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.errorprone_error_prone_annotations_2.3.4",
  jars = ["@maven//:com.google.errorprone/error_prone_annotations-2.3.4.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.errorprone:error_prone_annotations", "jvm_version=2.3.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.j2objc_j2objc-annotations_1.3_extension",
  srcs = ["@com.google.j2objc_j2objc-annotations_1.3//file"],
  outs = ["@maven//:com.google.j2objc/j2objc-annotations-1.3.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.j2objc_j2objc-annotations_1.3",
  jars = ["@maven//:com.google.j2objc/j2objc-annotations-1.3.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.j2objc:j2objc-annotations", "jvm_version=1.3"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j_0.9.0_extension",
  srcs = ["@org.eclipse.lsp4j_org.eclipse.lsp4j_0.9.0//file"],
  outs = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j-0.9.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j_0.9.0",
  jars = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j-0.9.0.jar"],
  deps = ["org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0", "org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0"],
  exports = [
    "org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0", "org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0"
  ],
  tags = ["jvm_module=org.eclipse.lsp4j:org.eclipse.lsp4j", "jvm_version=0.9.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0_extension",
  srcs = ["@org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0//file"],
  outs = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j.generator-0.9.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0",
  jars = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j.generator-0.9.0.jar"],
  deps = ["org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0", "org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0"],
  exports = ["org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0", "org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0"],
  tags = ["jvm_module=org.eclipse.lsp4j:org.eclipse.lsp4j.generator", "jvm_version=0.9.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0_extension",
  srcs = ["@org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0//file"],
  outs = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j.jsonrpc-0.9.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0",
  jars = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j.jsonrpc-0.9.0.jar"],
  deps = ["com.google.code.gson_gson_2.8.2"],
  exports = ["com.google.code.gson_gson_2.8.2"],
  tags = ["jvm_module=org.eclipse.lsp4j:org.eclipse.lsp4j.jsonrpc", "jvm_version=0.9.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0_extension",
  srcs = ["@org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0//file"],
  outs = ["@maven//:org.eclipse.xtend/org.eclipse.xtend.lib-2.19.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0",
  jars = ["@maven//:org.eclipse.xtend/org.eclipse.xtend.lib-2.19.0.jar"],
  deps = [
    "org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0", "org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0"
  ],
  exports = [
    "org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0", "org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0"
  ],
  tags = ["jvm_module=org.eclipse.xtend:org.eclipse.xtend.lib", "jvm_version=2.19.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.code.gson_gson_2.8.2_extension",
  srcs = ["@com.google.code.gson_gson_2.8.2//file"],
  outs = ["@maven//:com.google.code.gson/gson-2.8.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.code.gson_gson_2.8.2",
  jars = ["@maven//:com.google.code.gson/gson-2.8.2.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.code.gson:gson", "jvm_version=2.8.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0_extension",
  srcs = ["@org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0//file"],
  outs = ["@maven//:org.eclipse.xtext/org.eclipse.xtext.xbase.lib-2.19.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0",
  jars = ["@maven//:org.eclipse.xtext/org.eclipse.xtext.xbase.lib-2.19.0.jar"],
  deps = ["com.google.guava_guava_27.1-jre"],
  exports = ["com.google.guava_guava_27.1-jre"],
  tags = ["jvm_module=org.eclipse.xtext:org.eclipse.xtext.xbase.lib", "jvm_version=2.19.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0_extension",
  srcs = ["@org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0//file"],
  outs = ["@maven//:org.eclipse.xtend/org.eclipse.xtend.lib.macro-2.19.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0",
  jars = ["@maven//:org.eclipse.xtend/org.eclipse.xtend.lib.macro-2.19.0.jar"],
  deps = ["org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0"],
  exports = ["org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0"],
  tags = ["jvm_module=org.eclipse.xtend:org.eclipse.xtend.lib.macro", "jvm_version=2.19.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.protobuf_protobuf-java_3.13.0_extension",
  srcs = ["@com.google.protobuf_protobuf-java_3.13.0//file"],
  outs = ["@maven//:com.google.protobuf/protobuf-java-3.13.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.protobuf_protobuf-java_3.13.0",
  jars = ["@maven//:com.google.protobuf/protobuf-java-3.13.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.protobuf:protobuf-java", "jvm_version=3.13.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.protobuf_protobuf-java_2.6.1_extension",
  srcs = ["@com.google.protobuf_protobuf-java_2.6.1//file"],
  outs = ["@maven//:com.google.protobuf/protobuf-java-2.6.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.protobuf_protobuf-java_2.6.1",
  jars = ["@maven//:com.google.protobuf/protobuf-java-2.6.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.protobuf:protobuf-java", "jvm_version=2.6.1"],
  visibility = ["//visibility:public"]
)
'''
    ctx.file("BUILD", build_content, executable = False)

jvm_deps_rule = repository_rule(
    implementation = _jvm_deps_impl,
)
def jvm_deps():
  jvm_deps_rule(name = "maven")
