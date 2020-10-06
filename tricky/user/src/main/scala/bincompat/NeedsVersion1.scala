package bincompat

object NeedsVersion1 extends App {
  CrossBuild.run()
  println(LibraryTrait.version1)
}
