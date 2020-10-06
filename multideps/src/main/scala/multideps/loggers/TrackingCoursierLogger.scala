package multideps.loggers

import java.util.concurrent.atomic.AtomicLong

import coursier.cache.CacheLogger

class TrackingCoursierLogger(
    val name: String,
    val isArtifactDownload: Boolean,
    val isIncludedUrl: String => Boolean
) {
  private val downloadedArtifactCounter = new AtomicLong(0)
  private val cachedArtifactCounter = new AtomicLong(0)
  private val downloadSizeCounter = new AtomicLong(0)
  private val maxDownloadCounter = new AtomicLong(0)
  val state = new ProgressBarState()
  def totalArtifactCount(): Long =
    downloadedArtifactCounter.get() + cachedArtifactCounter.get()
  def cachedArtifactsCount(): Long = cachedArtifactCounter.get()
  def downloadSize(): Long = downloadSizeCounter.get()
  def maxDownloadSize(): Long = maxDownloadCounter.get()
  val cacheLogger: CacheLogger = new CacheLogger {
    override def foundLocally(url: String): Unit = {
      state.tryStart()
      if (isIncludedUrl(url)) cachedArtifactCounter.incrementAndGet()
      if (isArtifactDownload) state.tryStop()
    }
    override def downloadedArtifact(url: String, success: Boolean): Unit = {
      if (isIncludedUrl(url)) downloadedArtifactCounter.incrementAndGet()
      if (isArtifactDownload) state.tryStop()
    }
    override def downloadProgress(url: String, downloaded: Long): Unit = {
      downloadSizeCounter.set(downloaded)
    }
    override def downloadingArtifact(url: String): Unit = {
      state.tryStart()
    }
    override def downloadLength(
        url: String,
        totalLength: Long,
        alreadyDownloaded: Long,
        watching: Boolean
    ): Unit = {
      maxDownloadCounter.set(totalLength)
      downloadSizeCounter.set(alreadyDownloaded)
    }
    override def init(sizeHint: Option[Int]): Unit = {
      state.tryStart()
    }
    override def stop(): Unit = {
      state.stop()
    }
  }
}
