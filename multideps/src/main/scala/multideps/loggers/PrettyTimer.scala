package multideps.loggers

import java.time.Clock
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class PrettyTimer(clock: Clock = Clock.systemDefaultZone()) {
  val start: LocalTime = LocalTime.now(clock)
  def elapsed(): Duration = Duration.between(start, LocalTime.now())
  def format(): String = PrettyTimer.formatDuration(elapsed())
  def formatPadded(): String = format().padTo("10.4s".length(), ' ')
  override def toString(): String = format()
}

object PrettyTimer {

  private def formatDuration(elapsed: Duration): String = {
    val sec = elapsed.getSeconds()
    val hr = TimeUnit.SECONDS.toHours(sec)
    val min = TimeUnit.SECONDS.toMinutes(sec)
    val n = elapsed.getNano()
    val ms = TimeUnit.NANOSECONDS.toMillis(elapsed.getNano()).toDouble / 1000
    val s = (sec % 60).toDouble + ms
    new StringBuilder()
      .append(if (hr > 0) s"${hr}hr" else "")
      .append(if (min > 0) s"${min}hr" else "")
      .append(f"$s%.1fs")
      .toString()
  }
}
