
```scala
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

```




[test/scala/LineParsingSpeed.scala]: ../../../test/scala/LineParsingSpeed.scala.md
[test/scala/IsoformSequences.scala]: ../../../test/scala/IsoformSequences.scala.md
[test/scala/lines.scala]: ../../../test/scala/lines.scala.md
[test/scala/testData.scala]: ../../../test/scala/testData.scala.md
[test/scala/FlatFileEntry.scala]: ../../../test/scala/FlatFileEntry.scala.md
[test/scala/EntryParsingSpeed.scala]: ../../../test/scala/EntryParsingSpeed.scala.md
[test/scala/FileReadSpeed.scala]: ../../../test/scala/FileReadSpeed.scala.md
[test/scala/SeqOps.scala]: ../../../test/scala/SeqOps.scala.md
[main/scala/entry.scala]: ../entry.scala.md
[main/scala/isoformSequences.scala]: ../isoformSequences.scala.md
[main/scala/flat/SequenceData.scala]: ../flat/SequenceData.scala.md
[main/scala/flat/KW.scala]: ../flat/KW.scala.md
[main/scala/flat/ID.scala]: ../flat/ID.scala.md
[main/scala/flat/RC.scala]: ../flat/RC.scala.md
[main/scala/flat/DT.scala]: ../flat/DT.scala.md
[main/scala/flat/Entry.scala]: ../flat/Entry.scala.md
[main/scala/flat/GN.scala]: ../flat/GN.scala.md
[main/scala/flat/parsers.scala]: ../flat/parsers.scala.md
[main/scala/flat/RG.scala]: ../flat/RG.scala.md
[main/scala/flat/DR.scala]: ../flat/DR.scala.md
[main/scala/flat/OG.scala]: ../flat/OG.scala.md
[main/scala/flat/RL.scala]: ../flat/RL.scala.md
[main/scala/flat/SQ.scala]: ../flat/SQ.scala.md
[main/scala/flat/PE.scala]: ../flat/PE.scala.md
[main/scala/flat/OS.scala]: ../flat/OS.scala.md
[main/scala/flat/CC.scala]: ../flat/CC.scala.md
[main/scala/flat/OX.scala]: ../flat/OX.scala.md
[main/scala/flat/OH.scala]: ../flat/OH.scala.md
[main/scala/flat/RN.scala]: ../flat/RN.scala.md
[main/scala/flat/DE.scala]: ../flat/DE.scala.md
[main/scala/flat/RA.scala]: ../flat/RA.scala.md
[main/scala/flat/RX.scala]: ../flat/RX.scala.md
[main/scala/flat/FT.scala]: ../flat/FT.scala.md
[main/scala/flat/AC.scala]: ../flat/AC.scala.md
[main/scala/flat/RP.scala]: ../flat/RP.scala.md
[main/scala/flat/lineTypes.scala]: ../flat/lineTypes.scala.md
[main/scala/flat/RT.scala]: ../flat/RT.scala.md
[main/scala/seqOps.scala]: ../seqOps.scala.md
[main/scala/fasta/isoforms.scala]: isoforms.scala.md