package multideps.resolvers

import scala.util.hashing.MurmurHash3

case class SimpleDependency(
    module: SimpleModule,
    version: String
) {
  def repr: String = s"${module.repr}:$version"
  override lazy val hashCode: Int = MurmurHash3.productHash(this)
}
