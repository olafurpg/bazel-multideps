package bincompat

object LibraryTrait {
  def defaultValues(): Unit = println("Hello version 1!")
  def version1 = "this is version 1"
}
