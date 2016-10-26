package bio4j.data.uniprot.flat

import bio4j.data.uniprot.Keyword
import bio4j.data.uniprot.seqOps._

case class KW(val lines: Seq[String]) extends AnyVal {

  def keywords: Seq[Keyword] =
    lines.mkString("").splitSegments(_==';').map { kw => Keyword(kw.trim) }
}
