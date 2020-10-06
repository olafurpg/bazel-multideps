package multideps.configs

import moped.json.JsonDecoder
import moped.json.JsonEncoder
import moped.json.JsonString
import moped.json.ValueResult

sealed abstract class LanguagesConfig(val value: String)
    extends Product
    with Serializable
case object JavaLanguagesConfig extends LanguagesConfig("java")
case object ScalaLanguagesConfig extends LanguagesConfig("scala")
case object ScalaCompilerLanguagesConfig
    extends LanguagesConfig("scala-compiler")

object LanguagesConfig {
  implicit val decoder: JsonDecoder[LanguagesConfig] =
    JsonDecoder.fromJson("java | scala | scala-compiler") {
      case JsonString("java") => ValueResult(JavaLanguagesConfig)
      case JsonString("scala") => ValueResult(ScalaLanguagesConfig)
      case JsonString("scala-compiler") =>
        ValueResult(ScalaCompilerLanguagesConfig)
    }
  implicit val encoder: JsonEncoder[LanguagesConfig] =
    JsonEncoder.stringJsonEncoder.contramap[LanguagesConfig](_.value)
}
