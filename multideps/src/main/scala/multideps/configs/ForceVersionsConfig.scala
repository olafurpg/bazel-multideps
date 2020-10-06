package multideps.configs

import scala.util.matching.Regex

import moped.internal.diagnostics.TypeMismatchDiagnostic
import moped.json.DecodingContext
import moped.json.DecodingResult
import moped.json.ErrorResult
import moped.json.JsonArray
import moped.json.JsonDecoder
import moped.json.JsonEncoder
import moped.json.JsonString
import moped.json.ValueResult
import moped.reporters.Diagnostic

final case class ForceVersionsConfig(
    overrides: Map[ModuleConfig, String] = Map.empty
)

object ForceVersionsConfig {
  val default: ModuleConfig = ModuleConfig()
  implicit val encoder: JsonEncoder[ForceVersionsConfig] =
    JsonEncoder
      .iterableJsonEncoder[JsonString, List]
      .contramap[ForceVersionsConfig](_.overrides.toList.map {
        case (m, v) => JsonString(m.repr + ":" + v)
      })
  private val OrgArtifact: Regex = "([^:]+):([^:]+):([^:]+)".r
  implicit val decoder: JsonDecoder[ForceVersionsConfig] =
    new JsonDecoder[ForceVersionsConfig] {
      def decode(
          context: DecodingContext
      ): DecodingResult[ForceVersionsConfig] = {
        context.json match {
          case JsonArray(elements) =>
            val overrides = elements.map {
              case j @ JsonString(OrgArtifact(org, artifact, version)) =>
                ValueResult(
                  ModuleConfig(
                    JsonString(org)
                      .withPosition(j.position)
                      // NOTE(olafur): sob, avoid this cast upstream
                      .asInstanceOf[JsonString],
                    JsonString(artifact)
                      .withPosition(j.position)
                      .asInstanceOf[JsonString]
                  ) -> version
                )
              case _ =>
                ErrorResult(Diagnostic.typeMismatch("ORG:ARTIFACT", context))

            }
            DecodingResult
              .fromResults(overrides)
              .map(x => ForceVersionsConfig(x.toMap))
          case _ =>
            ErrorResult(new TypeMismatchDiagnostic("Object", context))
        }
      }
    }
}
