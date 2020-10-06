package multideps.configs

import multideps.configs.MultidepsJsonDecoders.jsonStringDecoder

import moped.json.JsonCodec
import moped.json.JsonString

final case class CrossVersionsConfig(
    name: JsonString = JsonString(""),
    version: JsonString = JsonString(""),
    forceVersions: ForceVersionsConfig = ForceVersionsConfig()
)
object CrossVersionsConfig {
  val default: CrossVersionsConfig = CrossVersionsConfig()
  implicit val codec: JsonCodec[CrossVersionsConfig] =
    moped.macros.deriveCodec(default)
}
