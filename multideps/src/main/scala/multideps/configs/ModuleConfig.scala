package multideps.configs

import multideps.configs.MultidepsJsonDecoders.jsonStringDecoder

import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import moped.json.JsonCodec
import moped.json.JsonString

final case class ModuleConfig(
    organization: JsonString = JsonString(""),
    name: JsonString = JsonString("")
) {
  def repr = coursierModule.repr
  def coursierModule: Module =
    Module(
      Organization(organization.value),
      ModuleName(name.value),
      Map.empty
    )
}

object ModuleConfig {
  def apply(organization: String, moduleName: String): ModuleConfig =
    ModuleConfig(JsonString(organization), JsonString(moduleName))
  val default: ModuleConfig = ModuleConfig()
  implicit val codec: JsonCodec[ModuleConfig] =
    moped.macros.deriveCodec(default)
}
