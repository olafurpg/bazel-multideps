package bincompat

object NeedsGuava29 extends App {
  List[Int]().foreach { n =>
    val g: com.google.common.graph.AbstractGraph[Int] = ???
    g.incidentEdgeOrder()
  }
  val hash = com.google.common.hash.Hashing
    .sha256()
    .hashString("Hello world", java.nio.charset.StandardCharsets.UTF_8)
  println(hash)
}
