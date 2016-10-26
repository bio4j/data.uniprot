package bio4j.data.uniprot.lines

import bio4j.data.uniprot.SequenceHeader
import bio4j.data.uniprot.seqOps._

case class SQ(val line: String) extends AnyVal {

  def sequenceHeader: SequenceHeader = {

    val fragments = line.splitSegments(_==';')

    val l =
      fragments(0)
        .stripPrefix("SEQUENCE")
        .dropWhile(_==' ')
        .takeWhile(_!=' ')
        .toInt

    val mw =
      fragments(1)
        .dropWhile(_==' ')
        .takeWhile(_!=' ')
        .toInt

    val crc =
      fragments(2)
        .dropWhile(_==' ')
        .takeWhile(_!=' ')

    SequenceHeader(
      length          = l,
      molecularWeight = mw,
      crc64           = crc
    )
  }
}
