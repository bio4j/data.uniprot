package bio4j.data.uniprot.lines

import bio4j.data.uniprot._

case class CC(val lines: Seq[String])  {

  def comments: Seq[Comment] =
    commentBlocks(lines) flatMap commentFromBlock

  private def commentFromBlock(blockLines: Seq[String]): Seq[Comment] = {

    val (topic, _headContent) = blockLines.head.span(_!=':')

    val contents: Seq[String] = _headContent.stripPrefix(":").trim +: blockLines.tail

    topic match {

      case "ALLERGEN"                       => Vector( Allergen(contents.mkString(" ")) )
      case "ALTERNATIVE PRODUCTS"           => Vector( ??? )
      case "BIOPHYSICOCHEMICAL PROPERTIES"  => Vector( BiophysicochemicalProperties(contents.mkString(" ")) )
      case "BIOTECHNOLOGY"                  => Vector( Biotechnology(contents.mkString(" ")) )
      case "CATALYTIC ACTIVITY"             => Vector( CatalyticActivity(contents.mkString(" ")) )
      case "CAUTION"                        => Vector( Caution(contents.mkString(" ")) )
      case "COFACTOR"                       => Vector( Cofactor(contents.mkString(" ")) )
      case "DEVELOPMENTAL STAGE"            => Vector( DevelopmentalStage(contents.mkString(" ")) )
      case "DISEASE"                        => Vector( Disease(contents.mkString(" ")) )
      case "DISRUPTION PHENOTYPE"           => Vector( DisruptionPhenotype(contents.mkString(" ")) )
      case "DOMAIN"                         => Vector( Domain(contents.mkString(" ")) )
      case "ENZYME REGULATION"              => Vector( EnzymeRegulation(contents.mkString(" ")) )
      case "FUNCTION"                       => Vector( Function(contents.mkString(" ")) )
      case "INDUCTION"                      => Vector( Induction(contents.mkString(" ")) )
      case "INTERACTION"                    => Vector( Interaction(contents.mkString(" ")) )
      case "MASS SPECTROMETRY"              => Vector( MassSpectrometry(contents.mkString(" ")) )
      case "MISCELLANEOUS"                  => Vector( Miscellaneous(contents.mkString(" ")) )
      case "PATHWAY"                        => Vector( Pathway(contents.mkString(" ")) )
      case "PHARMACEUTICAL"                 => Vector( Pharmaceutical(contents.mkString(" ")) )
      case "POLYMORPHISM"                   => Vector( Polymorphism(contents.mkString(" ")) )
      case "PTM"                            => Vector( PTM(contents.mkString(" ")) )
      case "RNA EDITING"                    => Vector( RNAEditing(contents.mkString(" ")) )
      case "SEQUENCE CAUTION"               => Vector( SequenceCaution(contents.mkString(" ")) )
      case "SIMILARITY"                     => Vector( Similarity(contents.mkString(" ")) )
      case "SUBCELLULAR LOCATION"           => Vector( SubcellularLocation(contents.mkString(" ")) )
      case "SUBUNIT"                        => Vector( Subunit(contents.mkString(" ")) )
      case "TISSUE SPECIFICITY"             => Vector( TissueSpecificity(contents.mkString(" ")) )
      case "TOXIC DOSE"                     => Vector( ToxicDose(contents.mkString(" ")) )
      case "WEB RESOURCE"                   => Vector( WebResource(contents.mkString(" ")) )
    }
  }

  private def commentBlocks(commentLines: Seq[String]): Seq[Seq[String]] =
    commentLines.foldLeft[Seq[Seq[String]]](Vector()){ (acc: Seq[Seq[String]], line: String) =>
      // extra lines for a comment
      if(line startsWith "    ") {
        acc.updated(acc.length -1, acc.last :+ line.trim)
      }
      else {
        acc :+ Vector(line.stripPrefix("-!-").trim)
      }
    }
}
