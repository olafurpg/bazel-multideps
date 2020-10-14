package multideps.loggers

import java.io.Writer

import moped.progressbars.ProgressBar
import moped.progressbars.ProgressRenderer
import moped.reporters.Terminals
import org.typelevel.paiges.Doc

class StaticProgressBar(
    renderer: ProgressRenderer,
    out: Writer,
    terminal: Terminals
) extends ProgressBar {
  private def write(d: Doc): Unit =
    out.write(d.render(terminal.screenWidth()))
  def start(): Unit = write(renderer.renderStart())
  def stop(): Unit = write(renderer.renderStop())
}
