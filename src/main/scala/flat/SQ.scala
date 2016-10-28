package bio4j.data.uniprot.flat

import bio4j.data.uniprot.SequenceHeader
import bio4j.data.uniprot.seqOps._

case class SQ(val line: String) extends AnyVal {

  def sequenceHeader: SequenceHeader = {

    // val fragments = line.splitSegments(_==';')

    val (frg0, rest0) = line.stripPrefix("SEQUENCE").dropWhile(_==' ').span(_!=';')
    val (frg1, rest1) = rest0.stripPrefix(";").span(_!=';')
    val (frg2, rest2) = rest1.stripPrefix(";").span(_!=';')

    val l =
      frg0
        .takeWhile(_!=' ')
        .toInt

    val mw =
      frg1
        .dropWhile(_==' ')
        .takeWhile(_!=' ')
        .toInt

    val crc =
      frg2
        .dropWhile(_==' ')
        .takeWhile(_!=' ')

    SequenceHeader(
      length          = l,
      molecularWeight = mw,
      crc64           = crc
    )
  }
}
