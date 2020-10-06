package multideps.resolvers

import coursier.core.Dependency
import coursier.core.Publication
import coursier.util.Artifact

final case class ResolvedDependency(
    dependency: Dependency,
    publication: Publication,
    artifact: Artifact
)
