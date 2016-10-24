package bio4j.data.uniprot

import java.time.LocalDate
import lines._

case class FlatFileEntry(
  val id: lines.ID,
  val ac: lines.AC,
  val dt: lines.DT, // 3 lines
  val de: lines.DE, // ?
  val GN_lines        : Seq[String], // ?
  val OS_lines        : Seq[String], //
  val OG_lines        : Seq[String],
  val OX_lines        : Seq[String],
  val OH_lines        : Seq[String],
  val CC_lines        : Seq[String],
  val DR_lines        : Seq[String],
  val PE_lines        : Seq[String],
  val KW_lines        : Seq[String],
  val FT_lines        : Seq[String],
  val SQ_lines        : Seq[String],
  val sequence_lines  : Seq[String]
)
extends AnyEntry {

  lazy val identification: Identification =
    Identification(
      entryName = id.id.mkString,
      status    = id.status,
      length    = id.length
    )

  lazy val accessionNumbers: AccessionNumber = {

    lazy val accesions =
      ac.accesions

    AccessionNumber(
      primary   = accesions.head,
      secondary = accesions.tail
    )
  }

  lazy val date: Date =
    Date(
      creation              = dt.creation,
      sequenceLastModified  = dt.sequenceLastModified,
      entryLastModified     = dt.entryLastModified
    )

  lazy val description: Description =
    Description(
      recommendedName   = de.recommendedName,
      alternativeNames  = de.alternativeNames,
      submittedNames    = de.submittedNames
    )

  lazy val geneNames: Seq[GeneName] = ???

  lazy val organismSpecies: OrganismSpecies = ???

  lazy val organelle: Option[Organelle] = ???

  lazy val organismClassification: OrganismClassification = ???

  lazy val taxonomyCrossReference: TaxonomyCrossReference = ???

  lazy val organismHost: Seq[TaxonomyCrossReference] = ???

  lazy val comments: Seq[Comment] = ???

  lazy val databaseCrossReferences: Seq[DatabaseCrossReference] = ???

  lazy val proteinExistence: ProteinExistence = ???

  lazy val keywords: Seq[Keyword] = ???

  lazy val features: Seq[Feature] = ???

  lazy val sequenceHeader: SequenceHeader = ???

  lazy val sequence: Sequence = ???
}

case object FlatFileEntry {

  import Line._

  // super ugly, but I don't see any simpler way
  def from(lns: Seq[String]): FlatFileEntry = {

    val allLines = lns.toArray

    val (id_lines, rest0) = allLines.span(isOfType(ID))
    val (ac_lines, rest1) = rest0.span(isOfType(AC))
    val (dt_lines, rest2) = rest1.span(isOfType(DT))
    val (de_lines, rest3) = rest2.span(isOfType(DE))
    val (gn_lines, rest4) = rest3.span(isOfType(GN))
    val (os_lines, rest5) = rest4.span(isOfType(OS))
    val (og_lines, rest6) = rest5.span(isOfType(OG))
    val (ox_lines, rest7) = rest6.span(isOfType(OX))
    val (oh_lines, rest8) = rest7.span(isOfType(OH))
    // drop ref lines
    val (cc_lines, rest9) = rest8.dropWhile(isReferenceLine).span(isOfType(CC))
    val (dr_lines, rest10) = rest9.span(isOfType(DR))
    val (pe_lines, rest11) = rest10.span(isOfType(PE))
    val (kw_lines, rest12) = rest11.span(isOfType(KW))
    val (ft_lines, rest13) = rest12.span(isOfType(FT))
    val (sq_lines, rest14) = rest13.span(isOfType(SQ))
    val seqLines = rest14

    FlatFileEntry(
      id  = lines.ID( contentOf(id_lines.head) ),
      ac  = lines.AC( ac_lines.map(contentOf(_)) ),
      dt  = lines.DT( dt_lines.map(contentOf(_)) ),
      de  = lines.DE( de_lines.map(contentOf(_)) ),
      GN_lines = gn_lines.map(contentOf(_)),
      OS_lines = os_lines.map(contentOf(_)),
      OG_lines = og_lines.map(contentOf(_)),
      OX_lines = ox_lines.map(contentOf(_)),
      OH_lines = oh_lines.map(contentOf(_)),
      CC_lines = cc_lines.map(contentOf(_)),
      DR_lines = dr_lines.map(contentOf(_)),
      PE_lines = pe_lines.map(contentOf(_)),
      KW_lines = kw_lines.map(contentOf(_)),
      FT_lines = ft_lines.map(contentOf(_)),
      SQ_lines = sq_lines.map(contentOf(_)),
      sequence_lines = seqLines.map(l => contentOf(l))
    )
  }
}
