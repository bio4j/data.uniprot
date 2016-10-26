package bio4j.data.uniprot

import java.time.LocalDate

case class FlatFileEntry(
  val allLines: Array[String]
)
extends AnyEntry {

  private lazy val id: lines.ID =
    lines.ID(linesOfType(ID).head)

  @inline
  final def identification: Identification =
    id.identification

  private lazy val ac: lines.AC =
    lines.AC(linesOfType(AC))

  @inline
  final def accessionNumbers: AccessionNumber = {

    val accesions =
      ac.accesions

    AccessionNumber(
      primary   = accesions.head,
      secondary = accesions.tail
    )
  }

  private lazy val dt: lines.DT =
    lines.DT(linesOfType(DT))

  @inline
  final def date: Date =
    dt.date

  private lazy val de: lines.DE =
    lines.DE(linesOfType(DE))

  @inline
  final def description: Description =
    de.description

  private lazy val gn: lines.GN =
    lines.GN(linesOfType(GN))

  @inline
  final def geneNames: Seq[GeneName] =
    gn.geneNames

  @inline
  final def organismSpecies: OrganismSpecies =
    ???

  private lazy val og: lines.OG =
    lines.OG(linesOfType(GN))

  lazy val organelles: Seq[Organelle] =
    og.organelles

  @inline
  final def organismClassification: OrganismClassification =
    ???

  private lazy val ox: lines.OX =
    lines.OX(linesOfType(OX).head)

  @inline
  final def taxonomyCrossReference: TaxonomyCrossReference =
    ox.taxonomyCrossReference

  private lazy val oh: lines.OH =
    lines.OH(linesOfType(OH))

  @inline
  final def organismHost: Seq[TaxonomyCrossReference] =
    oh.taxonomyCrossReferences

  private lazy val cc: lines.CC =
    lines.CC(linesOfType(CC))

  @inline
  final def comments: Seq[Comment] =
    cc.comments

  private lazy val dr: lines.DR =
    lines.DR(linesOfType(DR))

  @inline
  final def databaseCrossReferences: Seq[DatabaseCrossReference] =
    dr.databaseCrossReferences

  private lazy val pe: lines.PE =
    lines.PE(linesOfType(PE).head)

  @inline
  final def proteinExistence: ProteinExistence =
    pe.proteinExistence

  private lazy val kw: lines.KW =
    lines.KW(linesOfType(KW))

  @inline
  final def keywords: Seq[Keyword] =
    kw.keywords

  private lazy val ft: lines.FT =
    lines.FT(linesOfType(FT))

  @inline
  final def features: Seq[Feature] =
    ft.features

  private lazy val sq: lines.SQ =
    lines.SQ(linesOfType(SQ).head)

  @inline
  final def sequenceHeader: SequenceHeader =
    sq.sequenceHeader

  private lazy val sd: lines.SequenceData =
    lines.SequenceData(linesOfType(`  `))

  @inline
  final def sequence: Sequence =
    sd.sequence



  private def linesOfType(lt: LineType) =
    (allLines filter Line.isOfType(lt)).map(Line.contentOf)
}

case object FlatFileEntry {

  def from(lns: Seq[String]): FlatFileEntry =
    FlatFileEntry(lns.toArray)
}
