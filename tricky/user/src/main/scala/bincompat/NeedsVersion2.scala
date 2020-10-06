package bincompat

object NeedsVersion2 extends App {
  CrossBuild.run()
  println(LibraryTrait.version2)
}
