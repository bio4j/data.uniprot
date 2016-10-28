package bio4j.data.uniprot.flat

import bio4j.data.uniprot.Sequence
import bio4j.data.uniprot.seqOps._

case class SequenceData(val lines: Seq[String]) extends AnyVal {

  @inline
  final def sequence: Sequence =
    Sequence( lines.mkString("").filter(_ != ' ') )
}
