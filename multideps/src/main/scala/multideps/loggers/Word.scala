package multideps.loggers



sealed abstract class Word(paddingWidth: Int) {
  final def formatPadded(count: Long): String =
    format(count).padTo(paddingWidth, ' ')
  def format(count: Long): String
}
final case class SimpleWord(
    singular: String,
    plural: String,
    reasonableMaximumValue: Int
) extends Word(reasonableMaximumValue.toString.length) {
  def format(count: Long): String = {
    if (count == 1) s"$count $singular"
    else s"$count $plural"
  }
}

final case class PercentageWord(max: Long) extends Word("99.2%".length()) {
  def format(count: Long): String = {
    if (max == 0) ""
    else {
      val p = math.min(100, 100 * count.toDouble / max)
      f"$p%.1f%%"
    }
  }
}
object DownloadedBytesWord extends Word("100mb".length()) {
  def format(count: Long): String = BytesWord.format(count) + " downloaded"
}
object BytesWord extends Word("100mb".length()) {
  def format(count: Long): String = {
    if (count == 0L) ""
    else byteCount(count, si = false)
  }
  private def byteCount(bytes: Long, si: Boolean = false) = {
    val unit = if (si) 1000 else 1024
    if (bytes < unit)
      bytes + " B"
    else {
      val prefixes = if (si) "kMGTPE" else "KMGTPE"
      val exp = (math.log(bytes) / math.log(unit)).toInt min prefixes.length
      val pre = prefixes.charAt(exp - 1) + (if (si) "" else "i")
      val n = bytes / math.pow(unit, exp)
      if (exp < 2) f"${n}%.1f ${pre}B"
      else f"${n}%.2f ${pre}B" // Use more precision once we go over 1 GiB
    }
  }
}
object Word {
  def simple(
      singular: String,
      plural: String,
      reasonableMaximumValue: Int
  ): Word =
    SimpleWord(singular, plural, reasonableMaximumValue)
  def simple(word: String, reasonableMaximumValue: Int): Word =
    Word.simple(word, word + "s", reasonableMaximumValue)
}
