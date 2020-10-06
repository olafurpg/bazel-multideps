package multideps.loggers

import java.util.concurrent.ConcurrentLinkedQueue

import scala.collection.mutable

import multideps.diagnostics.MultidepsEnrichments.XtensionDependency

import coursier.cache.CacheLogger
import coursier.core.Dependency

class CoursierLoggers(
    isArtifactDownload: Boolean,
    isIncludedUrl: String => Boolean
) {
  private val loggers = new ConcurrentLinkedQueue[TrackingCoursierLogger]
  var totalRootDependencies = 0L
  var totalTransitiveDependencies = 0L
  var totalDownloadSize = 0L
  var totalCachedArtifacts = 0L
  var totalMaxDownloadSize = 0L
  def getActiveLoggers(): collection.Seq[TrackingCoursierLogger] = {
    val buf = mutable.ArrayBuffer.empty[TrackingCoursierLogger]
    loggers.removeIf { logger =>
      val isDone = logger.state.isAfterStop
      if (isDone) {
        totalRootDependencies += 1
        totalTransitiveDependencies += logger.totalArtifactCount
        totalMaxDownloadSize += logger.maxDownloadSize()
        totalDownloadSize += logger.downloadSize()
        totalCachedArtifacts += logger.cachedArtifactsCount
      } else if (logger.state.isActive) {
        buf += logger
      }
      isDone
    }
    buf
  }
  def newCacheLogger(dep: Dependency): CacheLogger = {
    val logger =
      new TrackingCoursierLogger(dep.repr, isArtifactDownload, isIncludedUrl)
    loggers.add(logger)
    logger.cacheLogger
  }
}
