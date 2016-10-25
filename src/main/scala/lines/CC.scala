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
      case "CAUTION"                        => Seq( Caution(contents.mkString(" ")) )
      case "COFACTOR"                       => Seq( Cofactor(contents.mkString(" ")) )
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
