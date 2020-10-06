package multideps.outputs

import org.typelevel.paiges.Doc

final case class TargetOutput(
    kind: String,
    attributes: (String, Doc)*
) {
  def toDoc: Doc = {
    val arguments = Doc.intercalate(
      Doc.comma + Doc.line,
      attributes.map { case (key, value) => Docs.attr(key, value) }
    )
    Doc.text(kind) + arguments.bracketBy(Docs.open, Docs.close)
  }
}
