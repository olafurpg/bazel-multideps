package multideps.loggers

import java.io.PrintStream
import java.io.PrintWriter
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

import coursier.cache.CacheLogger

final case class FancyResolveLogger(
    out: PrintStream,
    useAnsiOutput: Boolean,
    size: Int
) {
  self =>
  val lock = new Object
  private val p = new ProgressLogger[lock.type](
    s"Resolved",
    "dependencies",
    new PrintWriter(out)
  )
  private val isStarted = new AtomicBoolean(true)
  def start(): Unit = {}
  def stop(): Unit = {}
  private val locals = new AtomicInteger(0)
  private class Impl() extends CacheLogger {
    override def foundLocally(url: String): Unit = {
      locals.incrementAndGet()
    }
    override def downloadingArtifact(url: String): Unit = {
      p.processing(url, lock)
    }
    override def downloadedArtifact(url: String, success: Boolean): Unit = {
      // NOTE: ignore success parameter because the resolution can succeed if an artifact fails to download.
      p.processed(url, lock, errored = false)
    }
    override def stop(): Unit = {
      p.processedSet(lock)
      p.stop(keep = true)
    }
    override def init(sizeHint: Option[Int]): Unit = {}
  }

  def newLogger(): CacheLogger = ???
}
