package multideps.loggers

import java.time.Clock
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class PrettyTimer(clock: Clock = Clock.systemDefaultZone()) {
  val start: LocalTime = LocalTime.now(clock)
  def elapsed(): Duration = Duration.between(start, LocalTime.now())
  def format(): String = PrettyTimer.formatDuration(elapsed())
  override def toString(): String = format()
}

object PrettyTimer {

  private def formatDuration(elapsed: Duration): String = {
    val sec = elapsed.getSeconds()
    val hr = TimeUnit.SECONDS.toHours(sec).toDouble
    val min = TimeUnit.SECONDS.toMinutes(sec).toDouble
    val n = elapsed.getNano()
    val ms = TimeUnit.NANOSECONDS.toMillis(n).toDouble / 1000
    val s = (sec % 60).toDouble + ms
    val value = List[(String, Double)](
      "hr" -> hr,
      "min" -> min,
      "s" -> s
    )
    value
      .collect {
        case (l, v) if v > 0 =>
          if (v - math.floor(v) > 0.1) f"$v%.1f$l"
          else s"${v.toInt}$l"
      }
      .mkString
      .padTo("10min10.4s".length(), ' ')
  }
}
