package multideps.resolvers

import scala.util.hashing.MurmurHash3

case class SimpleModule(
    org: String,
    name: String
) {
  def repr: String = s"$org:$name"
  override lazy val hashCode: Int = MurmurHash3.productHash(this)
}
