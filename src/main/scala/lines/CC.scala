package bio4j.data.uniprot.lines

import bio4j.data.uniprot._

case class CC(val lines: Seq[String]) extends AnyVal {

  def comments: Seq[Comment] =
    commentBlocks flatMap commentFromBlock

  private def commentFromBlock(lines: Seq[String]): Seq[Comment] = {

    val (topic, _headContent) = lines.head.span(_!=':')

    val contents: Seq[String] = _headContent.stripPrefix(":") +: lines.tail

    topic match {

      case "ALLERGEN"                       => Seq( Allergen(contents.mkString(" ")) )
      case "ALTERNATIVE PRODUCTS"           => Seq( ??? )
      case "BIOPHYSICOCHEMICAL PROPERTIES"  => Seq( BiophysicochemicalProperties(contents.mkString(" ")) )
      case "BIOTECHNOLOGY"                  => Seq( Biotechnology(contents.mkString(" ")) )
      case "CATALYTIC ACTIVITY"             => Seq( CatalyticActivity(comments.mkString(" ")) )
      case "CAUTION"                        => Seq( Caution(contents.mkString(" ")) )
      case "COFACTOR"                       => Seq( Cofactor(contents.mkString(" ")) )
      case "WEB RESOURCE"                   => Seq( WebResource(contents.mkString(" ")) )
      case "DEVELOPMENTAL STAGE"            => Seq( DevelopmentalStage(contents.mkString(" ")) )
      case "DISEASE"                        => Seq( Disease(contents.mkString(" ")) )
      case "DISRUPTION PHENOTYPE"           => Seq( DisruptionPhenotype(contents.mkString(" ")) )
      case "DOMAIN"                         => Seq( Domain(contents.mkString(" ")) )
      case "ENZYME REGULATION"              => Seq( EnzymeRegulation(contents.mkString(" ")) )
      case "FUNCTION"                       => Seq( Function(contents.mkString(" ")) )
      case "INDUCTION"                      => Seq( Induction(contents.mkString(" ")) )
      case "INTERACTION"                    => Seq( Interaction(contents.mkString(" ")) )
      case "MASS SPECTROMETRY"              => Seq( MassSpectrometry(contents.mkString(" ")) )
      case "MISCELLANEOUS"                  => Seq( Miscellaneous(contents.mkString(" ")) )
      case "PATHWAY"                        => Seq( Pathway(contents.mkString(" ")) )
      case "PHARMACEUTICAL"                 => Seq( Pharmaceutical(contents.mkString(" ")) )
      case "POLYMORPHISM"                   => Seq( Polymorphism(contents.mkString(" ")) )
      case "PTM"                            => Seq( PTM(contents.mkString(" ")) )
      case "RNA EDITING"                    => Seq( RNAEditing(contents.mkString(" ")) )
      case "SEQUENCE CAUTION"               => Seq( SequenceCaution(contents.mkString(" ")) )
      case "SIMILARITY"                     => Seq( Similarity(contents.mkString(" ")) )
      case "SUBCELLULAR LOCATION"           => Seq( SubcellularLocation(contents.mkString(" ")) )
      case "SUBUNIT"                        => Seq( Subunit(contents.mkString(" ")) )
      case "TISSUE SPECIFICITY"             => Seq( TissueSpecificity(contents.mkString(" ")) )
      case "TOXIC DOSE"                     => Seq( ToxicDose(contents.mkString(" ")) )
      case "WEB RESOURCE"                   => Seq( WebResource(contents.mkString(" ")) )
      case _ => ???
    }
  }

  private def commentBlocks: Seq[Seq[String]] =
    commentBlocks_rec(lines, Seq())

  @annotation.tailrec
  private def commentBlocks_rec(commentLines: Seq[String], acc: Seq[Seq[String]]): Seq[Seq[String]] = {

    val (thisCommentDefLines, rest) = commentLines span { line =>  line startsWith "-!- " }

    val (thisCommentOtherLines, restOfComments) = rest span { _ startsWith "    " }

    val nextAcc: Seq[Seq[String]] =
      acc :+ (thisCommentDefLines.head.stripPrefix("-!-").trim +: thisCommentOtherLines.map(_.trim))

    if(restOfComments.isEmpty)
      nextAcc
    else
      commentBlocks_rec(
        commentLines = restOfComments,
        acc = nextAcc
      )
  }
}
