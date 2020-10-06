package multideps.indexes

import scala.collection.JavaConverters._
import scala.util.matching.Regex

import multideps.resolvers.SimpleDependency
import multideps.resolvers.SimpleModule

import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import org.scalameta.bazel_multideps.Build.Target

case class TargetIndex(
    name: String,
    deps: List[String],
    tags: List[String],
    dependency: Option[SimpleDependency]
)

object TargetIndex {
  private object JvmVersion {
    private val Version: Regex = "jvm_version=(.+)".r
    def unapply(s: String): Option[String] =
      s match {
        case Version(version) => Some(version)
        case _ => None
      }
  }
  private object JvmModule {
    private val OrgModule: Regex = "jvm_module=([^:]+):([^:]+)".r
    def unapply(s: String): Option[Module] =
      s match {
        case OrgModule(org, module) =>
          Some(Module(Organization(org), ModuleName(module), Map.empty))
        case _ => None
      }
  }
  def fromQuery(t: Target): TargetIndex = {
    def getStringListAttribute(key: String): List[String] =
      (for {
        attr <- t.getRule().getAttributeList().asScala.iterator
        if attr.getName() == key
        dep <- attr.getStringListValueList().asScala.iterator
      } yield dep).toList
    val deps = getStringListAttribute("deps")
    val tags = getStringListAttribute("tags")
    val dependency = tags match {
      case collection.Seq(JvmModule(module), JvmVersion(version)) =>
        Some(
          SimpleDependency(
            SimpleModule(module.organization.value, module.name.value),
            version
          )
        )
      case _ =>
        None
    }
    TargetIndex(
      name = t.getRule().getName(),
      deps = deps,
      tags = tags,
      dependency = dependency
    )
  }
}
