package multideps.configs

import scala.util.matching.Regex

import multideps.configs.MultidepsJsonDecoders.jsonStringDecoder

import coursier.core.Configuration
import coursier.core.Dependency
import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import coursier.core.Publication
import moped.json.DecodingContext
import moped.json.DecodingResult
import moped.json.JsonCodec
import moped.json.JsonElement
import moped.json.JsonObject
import moped.json.JsonString
import moped.json.ValueResult
import moped.macros.ClassShape

final case class DependencyConfig(
    organization: JsonString = JsonString(""),
    name: String = "",
    version: String = "",
    classifier: Option[String] = None,
    exclusions: Set[ModuleConfig] = Set.empty,
    crossVersions: List[CrossVersionsConfig] = Nil,
    forceVersions: ForceVersionsConfig = ForceVersionsConfig(),
    modules: List[String] = Nil,
    lang: LanguagesConfig = JavaLanguagesConfig,
    exports: List[String] = Nil,
    targets: List[String] = Nil,
    versionScheme: Option[String] = None,
    force: Boolean = false
) {
  def coursierModule(scalaVersion: VersionsConfig): Module = {
    val suffix = lang match {
      case JavaLanguagesConfig => ""
      case ScalaLanguagesConfig => "_" + scalaVersion.binaryVersion
      case ScalaCompilerLanguagesConfig => "_" + scalaVersion.default.value
    }
    Module(
      Organization(organization.value),
      ModuleName(name + suffix),
      Map.empty
    )
  }
  def allVersions: List[String] =
    version :: crossVersions.map(_.version.value)
  def getVersion(key: String): Option[String] =
    if (key == "default") Some(version)
    else crossVersions.find(_.name.value == key).map(_.version.value)
  def coursierDependencies(scalaVersion: VersionsConfig): List[Dependency] =
    allVersions.map(v =>
      Dependency(
        module = coursierModule(scalaVersion),
        version = v,
        configuration = classifier match {
          case Some(c) => Configuration(c)
          case None => Configuration.empty
        },
        exclusions = exclusions.map(e =>
          e.coursierModule.organization -> e.coursierModule.name
        ),
        publication = Publication.empty,
        optional = false,
        transitive = true
      )
    )

}

object DependencyConfig {
  private val Full: Regex = "(.+):::(.+):(.+)".r
  private val Half: Regex = "(.+)::(.+):(.+)".r
  private val Java: Regex = "(.+):(.+):(.+)".r
  private object FromJsonString {
    def unapply(s: JsonString): Option[DependencyConfig] = {
      def json(value: String) = JsonString(value).withPosition(s.position)
      s.value match {
        case Full(org, artifact, version) =>
          Some(
            DependencyConfig(
              json(org),
              artifact,
              version = version,
              lang = ScalaCompilerLanguagesConfig
            )
          )
        case Half(org, artifact, version) =>
          Some(
            DependencyConfig(
              json(org),
              artifact,
              version = version,
              lang = ScalaLanguagesConfig
            )
          )
        case Java(org, artifact, version) =>
          Some(
            DependencyConfig(
              json(org),
              artifact,
              version = version
            )
          )
        case _ => None

      }
    }
  }
  val default: DependencyConfig = DependencyConfig()
  def automaticCodec(d: DependencyConfig): JsonCodec[DependencyConfig] =
    moped.macros.deriveCodec(d)
  val automaticCodec: JsonCodec[DependencyConfig] =
    automaticCodec(default)
  implicit val codec: JsonCodec[DependencyConfig] =
    new JsonCodec[DependencyConfig] {
      def decode(context: DecodingContext): DecodingResult[DependencyConfig] = {
        context.json match {
          case FromJsonString(dep) => ValueResult(dep)
          case obj: JsonObject =>
            obj.value.get("dependency") match {
              case Some(FromJsonString(dep)) =>
                val newJson = JsonObject(
                  obj.members.filterNot(_.key.value == "dependency")
                )
                automaticCodec(dep).decode(context.withJson(newJson))
              case _ =>
                automaticCodec.decode(context)
            }
          case _ =>
            automaticCodec.decode(context)
        }
      }
      def encode(value: DependencyConfig): JsonElement =
        automaticCodec.encode(value)
      def shape: ClassShape = automaticCodec.shape
    }

}
