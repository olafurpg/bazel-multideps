package multideps.configs

import moped.json.JsonDecoder
import moped.json.JsonString
import moped.json.ValueResult

object MultidepsJsonDecoders {
  implicit val jsonStringDecoder: JsonDecoder[JsonString] =
    JsonDecoder.fromJson[JsonString]("String") {
      case j: JsonString => ValueResult(j)
    }
}
