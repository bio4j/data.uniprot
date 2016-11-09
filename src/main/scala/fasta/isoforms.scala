package com.bio4j.data.uniprot.fasta

import com.bio4j.data.uniprot.AnyIsoformSequence
import ohnosequences.fastarious._, fasta._

case class Isoform(val fa: FASTA.Value) extends AnyVal with AnyIsoformSequence {

  def ID: String =
    // the format of the id is 'sp|${id}|otherstuff'
    fa.getV(fasta.header).id.stripPrefix("sp|").takeWhile(_ != '|')

  def sequence: String =
    fa.getV(fasta.sequence).value
}

case object isoformSequences {

  def fromLines(lines: Iterator[String]): Iterator[Isoform] =
    fasta.parseFastaDropErrors(lines) map Isoform
}
