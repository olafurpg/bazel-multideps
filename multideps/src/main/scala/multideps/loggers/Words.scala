package multideps.loggers

object Words {
  val cached: Word =
    Word.simple("cached", "cached", 12)
  val worker: Word =
    Word.simple("worker", 12)
  val remaining: Word =
    Word.simple("dependency remaining", "dependencies remaining", 10000)
  val dependencies: Word =
    Word.simple("dependency", "dependencies", 1000)
  val done: Word =
    Word.simple("done", "done", 1000)
  val transitiveDendencies: Word =
    Word.simple("transitive dependency", "transitive dependencies", 10000)
  val bytes: Word = BytesWord
  val downloadedBytes: Word = DownloadedBytesWord
  val jarFiles: Word = Word.simple("jar file", 1000)
  def percentage(max: Long): Word = PercentageWord(max)
}
