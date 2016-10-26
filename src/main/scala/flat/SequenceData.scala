package bio4j.data.uniprot.flat

import bio4j.data.uniprot.Sequence
import bio4j.data.uniprot.seqOps._

case class SequenceData(val lines: Seq[String]) extends AnyVal {

  def sequence: Sequence =
    Sequence( (lines map lineToSequence).mkString("") )

  private def lineToSequence(line: String): String =
    line.splitSegments(_==' ').mkString("")
}
