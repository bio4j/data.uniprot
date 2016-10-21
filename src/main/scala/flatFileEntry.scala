package bio4j.data.uniprot

import java.time.LocalDate

case class FlatFileEntry(
  val id: ID,
  val ac: AC,
  val dt: DT, // 3 lines
  val DE_lines        : Seq[Line], // ?
  val GN_lines        : Seq[Line], // ?
  val OS_lines        : Seq[Line], //
  val OG_lines        : Seq[Line],
  val OX_lines        : Seq[Line],
  val OH_lines        : Seq[Line],
  val CC_lines        : Seq[Line],
  val DR_lines        : Seq[Line],
  val PE_lines        : Seq[Line],
  val KW_lines        : Seq[Line],
  val FT_lines        : Seq[Line],
  val SQ_lines        : Seq[Line],
  val sequence_lines  : Seq[String]
)
extends AnyEntry {

  lazy val identification: Identification =
    Identification(
      entryName = id.id,
      status    = id.status,
      length    = id.length
    )

  lazy val accessionNumbers: AccessionNumber = {

    lazy val accesions =
      ac.accesions

    println { accesions }

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

  lazy val description: Description = {

    // if there's a recommended name, the content starts with 'RecName:'; otherwhise there's none
    // note that there's *always* a DE line, we just don't know what it is
    // both values here cannot be empty
    val (recNameLines, rest) = DE_lines.span(_.content.startsWith("RecName:"))
    val recNameLine = recNameLines.headOption

    val fullNameOpt =
      recNameLine map { l =>

        val z =
          l.content
            .trim
            .stripPrefix("RecName: Full=")
            .stripSuffix(";")

        // they sometimes have some funny '{ECO:0000255|HAMAP-Rule:MF_01588}' stuff at the end
        if(z.endsWith("}"))
          z.reverse.dropWhile(_ != '{').stripPrefix("{").reverse.trim
        else
          z
      }

    val (shortNames: Seq[String], ecNames: Seq[String]) = recNameLine.fold[(Seq[String], Seq[String])](( Seq(),Seq() ))(l => {

      val restRecNameLinesTrimmed: Seq[String] =
        rest
          .map(_.content)
          .takeWhile(_.startsWith("        "))
          .map(_.trim)

      (
        restRecNameLinesTrimmed.filter(_.trim.startsWith("Short="))
          .map(_.stripPrefix("Short=").stripSuffix(";"))
        ,
        restRecNameLinesTrimmed.filter(_.trim.startsWith("EC="))
          .map(_.stripPrefix("EC=").stripSuffix(";"))
      )
    }
    )

    val ret1 = fullNameOpt.map { fn =>
      RecommendedName(
        full  = fn,
        short = shortNames,
        ec    = ecNames
      )
    }

    Description(
      recommendedName = ret1,
      alternativeNames = Seq(),
      submittedNames = Seq()
    )
  }

  import seqOps._

  lazy val geneNames: Seq[GeneName] = {

    // we can safely join several lines into one String here for each gene
    def splitByGene(geneLines: Seq[String]): Seq[String] =
      (geneLines splitSegments { _ startsWith "and" }).map { _.mkString("") }

    def dropECOcrap(fragment: String): String =
      if(fragment.endsWith("}"))
      fragment.reverse.dropWhile(_ == ' ').reverse
      else
      fragment

    def splitGeneNameValues(line: String): Seq[String] =
      (line splitSegments { _ == ';' }).map(s => dropECOcrap(s.trim))

    val isName              : String => Boolean = _.startsWith("Name=")
    val isSynonyms          : String => Boolean = _.startsWith("Synonyms=")
    val isOrderedLocusNames : String => Boolean = _.startsWith("OrderedLocusNames=")
    val isORFNames          : String => Boolean = _.startsWith("ORFNames=")


    def name(fragment: String): String =
      dropECOcrap(fragment.stripPrefix("Name=")).trim

    def synonyms(fragment: String): Seq[String] =
      if(isSynonyms(fragment))
        fragment.stripPrefix("Synonyms=").splitSegments(_ == ',').map(s => dropECOcrap(s.trim))
      else
        Seq()

    def orderedLocusNames(fragment: String): Seq[String] =
      ???

    def orfNames(fragment: String): Seq[String] =
      ???

    splitByGene(GN_lines.map(_.content)) map { geneLine =>

      val values = splitGeneNameValues(geneLine)

      val nf = (values filter isName).headOption
      val n = nf map name

      val sf = (values filter isSynonyms).mkString("")
      val s = synonyms(sf)

      // TODO finish this
      val oln   = values filter isOrderedLocusNames
      val orfn  = values filter isORFNames

      GeneName(
        name  = n map { Name(_, s) },
        orderedLocusNames = oln,
        ORFNames          = orfn
       )
    }
  }

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
  def from(allLines: Seq[String]): FlatFileEntry = {

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
      id  = ID( contentOf(id_lines.head) ),
      ac  = AC( ac_lines.map(contentOf(_)) ),
      dt  = DT( dt_lines.map(contentOf(_)) ),
      DE_lines = de_lines.map(l => Line( DE, contentOf(l) )),
      GN_lines = gn_lines.map(l => Line( GN, contentOf(l) )),
      OS_lines = os_lines.map(l => Line( OS, contentOf(l) )),
      OG_lines = og_lines.map(l => Line( OG, contentOf(l) )),
      OX_lines = ox_lines.map(l => Line( OX, contentOf(l) )),
      OH_lines = oh_lines.map(l => Line( OH, contentOf(l) )),
      CC_lines = cc_lines.map(l => Line( CC, contentOf(l) )),
      DR_lines = dr_lines.map(l => Line( DR, contentOf(l) )),
      PE_lines = pe_lines.map(l => Line( PE, contentOf(l) )),
      KW_lines = kw_lines.map(l => Line( KW, contentOf(l) )),
      FT_lines = ft_lines.map(l => Line( FT, contentOf(l) )),
      SQ_lines = sq_lines.map(l => Line( SQ, contentOf(l) )),
      sequence_lines = seqLines.map(l => contentOf(l))
    )
  }
}
