package multideps.indexes

import scala.collection.JavaConverters._
import scala.collection.mutable

import multideps.resolvers.SimpleDependency

import org.scalameta.bazel_multideps.Build.QueryResult
import org.scalameta.bazel_multideps.Build.Target

class DependenciesIndex(query: QueryResult) {
  private val deps: mutable.LinkedHashMap[String, Set[TargetIndex]] =
    mutable.LinkedHashMap.empty
  private val targets = query
    .getTargetList()
    .asScala
    .filter(_.getType() == Target.Discriminator.RULE)
    .map(TargetIndex.fromQuery)
  private val byName: Map[String, TargetIndex] =
    targets.map(t => t.name -> t).toMap
  val byDependency: Map[SimpleDependency, TargetIndex] = targets
    .flatMap(t => t.dependency.map(d => d -> t))
    .toMap

  def dependencies(dependency: SimpleDependency): Set[TargetIndex] = {
    byDependency.get(dependency) match {
      case Some(target) => dependencies(target)
      case None => Set.empty
    }
  }
  def dependencies(target: String): Set[TargetIndex] = {
    dependencies(byName(target))
  }
  def dependencies(target: TargetIndex): Set[TargetIndex] = {
    deps.get(target.name) match {
      case Some(value) => value
      // NOTE(olafur): not stack safe
      case None => Set(target) ++ target.deps.flatMap(dependencies)
    }
  }
}
