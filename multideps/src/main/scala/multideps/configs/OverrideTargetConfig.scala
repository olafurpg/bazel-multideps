package multideps.configs

import moped.json.JsonCodec
final case class OverrideTargetConfig(
    from: String = "",
    to: String = ""
)

object OverrideTargetConfig {
  val default: OverrideTargetConfig = OverrideTargetConfig()
  implicit val codec: JsonCodec[OverrideTargetConfig] =
    moped.macros.deriveCodec(default)
}
