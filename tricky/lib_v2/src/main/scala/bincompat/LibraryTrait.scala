package bincompat

object LibraryTrait {
  def defaultValues(name: String = "version 2"): Unit = println(s"Hello $name!")
  def version2 = "this is version 2"
}
