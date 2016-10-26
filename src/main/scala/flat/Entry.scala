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
