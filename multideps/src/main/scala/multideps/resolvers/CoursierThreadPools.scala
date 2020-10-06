package multideps.resolvers

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

import scala.concurrent.ExecutionContext

class CoursierThreadPools {

  def close(): Unit = downloadPool.shutdownNow()

  // From https://github.com/johnynek/bazel-deps/blob/dbd90f155e45e0b2529999d0b74ea65b49e6fb07/src/scala/com/github/johnynek/bazel_deps/CoursierResolver.scala#L19
  val downloadPool: ExecutorService = Executors.newFixedThreadPool(
    12,
    new ThreadFactory {
      val defaultThreadFactory = Executors.defaultThreadFactory()
      def newThread(r: Runnable) = {
        val t = defaultThreadFactory.newThread(r)
        t.setDaemon(true)
        t
      }
    }
  )
  val ec: ExecutionContext = ExecutionContext.fromExecutorService(downloadPool)
}
