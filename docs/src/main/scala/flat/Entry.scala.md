
```scala
package bio4j.data.uniprot.flat

import bio4j.data.uniprot._

import java.time.LocalDate

case class Entry(
  val allLines: Seq[String]
)
extends AnyEntry {

  private lazy val id: ID =
    ID(linesOfType(LineType.ID).head)

  @inline
  final def identification: Identification =
    id.identification

  private lazy val ac: AC =
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

  private lazy val dt: DT =
    DT(linesOfType(LineType.DT))

  @inline
  final def date: Date =
    dt.date

  private lazy val de: DE =
    DE(linesOfType(LineType.DE))

  @inline
  final def description: Description =
    de.description

  private lazy val gn: GN =
    GN(linesOfType(LineType.GN))

  @inline
  final def geneNames: Seq[GeneName] =
    gn.geneNames

  @inline
  final def organismSpecies: OrganismSpecies =
    ???

  private lazy val og: OG =
    OG(linesOfType(LineType.GN))

  lazy val organelles: Seq[Organelle] =
    og.organelles

  @inline
  final def organismClassification: OrganismClassification =
    ???

  private lazy val ox: OX =
    OX(linesOfType(LineType.OX).head)

  @inline
  final def taxonomyCrossReference: TaxonomyCrossReference =
    ox.taxonomyCrossReference

  private lazy val oh: OH =
    OH(linesOfType(LineType.OH))

  @inline
  final def organismHost: Seq[TaxonomyCrossReference] =
    oh.taxonomyCrossReferences

  private lazy val cc: CC =
    CC(linesOfType(LineType.CC))

  @inline
  final def comments: Seq[Comment] =
    cc.comments

  private lazy val dr: DR =
    DR(linesOfType(LineType.DR))

  @inline
  final def databaseCrossReferences: Seq[DatabaseCrossReference] =
    dr.databaseCrossReferences

  private lazy val pe: PE =
    PE(linesOfType(LineType.PE).head)

  @inline
  final def proteinExistence: ProteinExistence =
    pe.proteinExistence

  private lazy val kw: KW =
    KW(linesOfType(LineType.KW))

  @inline
  final def keywords: Seq[Keyword] =
    kw.keywords

  private lazy val ft: FT =
    FT(linesOfType(LineType.FT))

  @inline
  final def features: Seq[Feature] =
    ft.features

  private lazy val sq: SQ =
    SQ(linesOfType(LineType.SQ).head)

  @inline
  final def sequenceHeader: SequenceHeader =
    sq.sequenceHeader

  private lazy val sd: SequenceData =
    SequenceData(linesOfType(LineType.`  `))

  @inline
  final def sequence: Sequence =
    sd.sequence



  private def linesOfType(lt: LineType) =
    (allLines filter Line.isOfType(lt)).map(Line.contentOf)
}

case object Entry {

  def from(lns: Seq[String]): Entry =
    Entry(lns)
}

```




[test/scala/lines.scala]: ../../../test/scala/lines.scala.md
[test/scala/testData.scala]: ../../../test/scala/testData.scala.md
[test/scala/FlatFileEntry.scala]: ../../../test/scala/FlatFileEntry.scala.md
[test/scala/EntryParsingSpeed.scala]: ../../../test/scala/EntryParsingSpeed.scala.md
[test/scala/FileReadSpeed.scala]: ../../../test/scala/FileReadSpeed.scala.md
[test/scala/SeqOps.scala]: ../../../test/scala/SeqOps.scala.md
[main/scala/entry.scala]: ../entry.scala.md
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