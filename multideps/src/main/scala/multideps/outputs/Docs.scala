package multideps.outputs

import org.typelevel.paiges.Doc

object Docs {
  def attr(name: String, value: Doc): Doc =
    Doc.text(name) + Doc.text(" = ") + value
  def literal(value: String): Doc = quote + Doc.text(value) + quote
  def array(values: String*): Doc =
    apply(openBracket, closeBracket, values.map(literal))
  def apply(open: Doc, close: Doc, values: Iterable[Doc]): Doc =
    Doc
      .intercalate(Doc.comma + Doc.lineOrSpace, values)
      .tightBracketBy(open, close)
  val blankLine: Doc = Doc.line + Doc.line
  val quote: Doc = Doc.char('"')
  val open: Doc = Doc.char('(')
  val close: Doc = Doc.char(')')
  val openBracket: Doc = Doc.char('[')
  val closeBracket: Doc = Doc.char(']')
  object emoji {
    val success: Doc = colors.green + Doc.text("✔ ") + colors.reset
    val error: Doc = Doc.text("❗")
  }
  object colors {
    val green: Doc = Doc.zeroWidth(Console.GREEN)
    val reset: Doc = Doc.zeroWidth(Console.RESET)
  }
}
