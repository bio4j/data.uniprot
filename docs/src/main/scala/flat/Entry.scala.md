
```scala
package bio4j.data.uniprot.flat

import bio4j.data.uniprot._

import java.time.LocalDate

case class Entry(
  val allLines: Seq[String]
)
extends AnyVal with AnyEntry {

  final def id: ID =
    ID(linesOfType(LineType.ID).head)

  @inline
  final def identification: Identification =
    id.identification

  final def ac: AC =
    AC(linesOfType(LineType.AC))

  @inline
  final def accessionNumbers: AccessionNumber = {

    val accesions =
      ac.accesions

    AccessionNumber(
      primary   = accesions.head,
      secondary = accesions.tail
    )
  }

  final def dt: DT =
    DT(linesOfType(LineType.DT))

  @inline
  final def date: Date =
    dt.date

  final def de: DE =
    DE(linesOfType(LineType.DE))

  @inline
  final def description: Description =
    de.description

  final def gn: GN =
    GN(linesOfType(LineType.GN))

  @inline
  final def geneNames: Seq[GeneName] =
    gn.geneNames

  @inline
  final def organismSpecies: OrganismSpecies =
    ???

  final def og: OG =
    OG(linesOfType(LineType.OG))

  @inline
  final def organelles: Seq[Organelle] =
    og.organelles

  @inline
  final def organismClassification: OrganismClassification =
    ???

  final def ox: OX =
    OX(linesOfType(LineType.OX).head)

  @inline
  final def taxonomyCrossReference: TaxonomyCrossReference =
    ox.taxonomyCrossReference

  final def oh: OH =
    OH(linesOfType(LineType.OH))

  @inline
  final def organismHost: Seq[TaxonomyCrossReference] =
    oh.taxonomyCrossReferences

  final def cc: CC =
    CC(linesOfType(LineType.CC))

  @inline
  final def comments: Seq[Comment] =
    cc.comments

  final def dr: DR =
    DR(linesOfType(LineType.DR).toList)

  @inline
  final def databaseCrossReferences: Seq[DatabaseCrossReference] =
    dr.databaseCrossReferences

  final def pe: PE =
    PE(linesOfType(LineType.PE).head)

  @inline
  final def proteinExistence: ProteinExistence =
    pe.proteinExistence

  final def kw: KW =
    KW(linesOfType(LineType.KW))

  @inline
  final def keywords: Seq[Keyword] =
    kw.keywords

  final def ft: FT =
    FT(linesOfType(LineType.FT))

  @inline
  final def features: Seq[Feature] =
    ft.features

  final def sq: SQ =
    SQ(linesOfType(LineType.SQ).head)

  @inline
  final def sequenceHeader: SequenceHeader =
    sq.sequenceHeader

  final def sd: SequenceData =
    SequenceData(linesOfType(LineType.`  `))

  @inline
  final def sequence: Sequence =
    sd.sequence

  @inline
  final def linesOfType(lt: LineType) =
    allLines
      .dropWhile(l => !(l startsWith lt.asString))
      .takeWhile(_ startsWith lt.asString)
      .map(_ drop 5)
    // allLines collect { case l if(l startsWith lt.toString) => l drop 5 }
}

case object Entry {

  @inline
  def from(lns: Seq[String]): Entry =
    Entry(lns)
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
[main/scala/flat/SequenceData.scala]: SequenceData.scala.md
[main/scala/flat/KW.scala]: KW.scala.md
[main/scala/flat/ID.scala]: ID.scala.md
[main/scala/flat/RC.scala]: RC.scala.md
[main/scala/flat/DT.scala]: DT.scala.md
[main/scala/flat/Entry.scala]: Entry.scala.md
[main/scala/flat/GN.scala]: GN.scala.md
[main/scala/flat/parsers.scala]: parsers.scala.md
[main/scala/flat/RG.scala]: RG.scala.md
[main/scala/flat/DR.scala]: DR.scala.md
[main/scala/flat/OG.scala]: OG.scala.md
[main/scala/flat/RL.scala]: RL.scala.md
[main/scala/flat/SQ.scala]: SQ.scala.md
[main/scala/flat/PE.scala]: PE.scala.md
[main/scala/flat/OS.scala]: OS.scala.md
[main/scala/flat/CC.scala]: CC.scala.md
[main/scala/flat/OX.scala]: OX.scala.md
[main/scala/flat/OH.scala]: OH.scala.md
[main/scala/flat/RN.scala]: RN.scala.md
[main/scala/flat/DE.scala]: DE.scala.md
[main/scala/flat/RA.scala]: RA.scala.md
[main/scala/flat/RX.scala]: RX.scala.md
[main/scala/flat/FT.scala]: FT.scala.md
[main/scala/flat/AC.scala]: AC.scala.md
[main/scala/flat/RP.scala]: RP.scala.md
[main/scala/flat/lineTypes.scala]: lineTypes.scala.md
[main/scala/flat/RT.scala]: RT.scala.md
[main/scala/seqOps.scala]: ../seqOps.scala.md
[main/scala/fasta/isoforms.scala]: ../fasta/isoforms.scala.md