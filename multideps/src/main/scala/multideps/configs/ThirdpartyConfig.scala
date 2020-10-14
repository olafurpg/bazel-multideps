package multideps.configs

import multideps.diagnostics.MultidepsEnrichments.XtensionList
import multideps.loggers.ResolveProgressRenderer

import coursier.Resolve
import coursier.cache.FileCache
import coursier.core.Dependency
import coursier.core.Module
import coursier.params.ResolutionParams
import coursier.util.Task
import moped.json.DecodingContext
import moped.json.DecodingResult
import moped.json.ErrorResult
import moped.json.JsonCodec
import moped.json.ValueResult
import moped.parsers.ConfigurationParser
import moped.parsers.JsonParser
import moped.parsers.YamlParser
import moped.reporters.Diagnostic
import moped.reporters.Input

final case class ThirdpartyConfig(
    repositories: List[RepositoryConfig] = List(),
    overrideTargets: List[OverrideTargetConfig] = List(),
    dependencies: List[DependencyConfig] = List(),
    scala: VersionsConfig = VersionsConfig()
) {
  val depsByModule: Map[Module, List[DependencyConfig]] =
    dependencies.groupBy(_.coursierModule(scala))
  def coursierDeps: List[(DependencyConfig, Dependency)] =
    dependencies
      .flatMap(d => d.coursierDependencies(scala).map(cd => d -> cd))
      .distinctBy(_._2)
  def toResolve(
      dep: DependencyConfig,
      cache: FileCache[Task],
      progressBar: ResolveProgressRenderer,
      cdep: Dependency
  ): DecodingResult[Resolve[Task]] =
    decodeForceVersions(dep).map { forceVersions =>
      Resolve(cache.withLogger(progressBar.loggers.newCacheLogger(cdep)))
        .addDependencies(cdep)
        .withResolutionParams(
          ResolutionParams().addForceVersion(forceVersions: _*)
        )
        .addRepositories(
          repositories.flatMap(_.coursierRepository): _*
        )
    }
  private def decodeForceVersions(
      dep: DependencyConfig
  ): DecodingResult[List[(Module, String)]] =
    DecodingResult.fromResults {
      dep.forceVersions.overrides.flatMap {
        case (module, version) =>
          depsByModule.get(module.coursierModule) match {
            case None =>
              List(
                ErrorResult(
                  Diagnostic.error(
                    s"version '$version' not found",
                    module.name.position
                  )
                )
              )
            case Some(depsConfigs) =>
              depsConfigs.map { depsConfig =>
                depsConfig.getVersion(version) match {
                  case Some(forcedVersion) =>
                    ValueResult(
                      depsConfig.coursierModule(scala) -> forcedVersion
                    )
                  case None =>
                    // TODO: report "did you mean?"
                    ErrorResult(
                      Diagnostic.error(
                        s"version '$version' not found",
                        module.name.position
                      )
                    )
                }
              }
          }
      }
    }
}

object ThirdpartyConfig {
  def parseYaml(input: Input): DecodingResult[ThirdpartyConfig] = {
    parse(input, YamlParser)
  }
  def parseJson(input: Input): DecodingResult[ThirdpartyConfig] = {
    parse(input, JsonParser)
  }
  private def parse(
      input: Input,
      parser: ConfigurationParser
  ): DecodingResult[ThirdpartyConfig] = {
    parser
      .parse(input)
      .flatMap(json =>
        codec.decode(DecodingContext(json).withFatalUnknownFields(true))
      )
  }
  val default: ThirdpartyConfig = ThirdpartyConfig()
  implicit val codec: JsonCodec[ThirdpartyConfig] =
    moped.macros.deriveCodec(default)
}
