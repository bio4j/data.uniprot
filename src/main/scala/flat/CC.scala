package bio4j.data.uniprot.flat

import bio4j.data.uniprot._

case class CC(val lines: Seq[String])  {

  def comments: Seq[Comment] =
    commentBlocks(lines) flatMap commentFromBlock

  private def commentFromBlock(blockLines: Seq[String]): Seq[Comment] = {

    val (topic, _headContent) = blockLines.head.span(_!=':')

    val contents: Seq[String] = _headContent.stripPrefix(":").trim +: blockLines.tail

    topic match {

      case "ALLERGEN"                       => List( Allergen(contents.mkString(" ")) )
      case "ALTERNATIVE PRODUCTS"           => isoformBlocks(contents.tail) map isoformFromBlock
      case "BIOPHYSICOCHEMICAL PROPERTIES"  => List( BiophysicochemicalProperties(contents.mkString(" ")) )
      case "BIOTECHNOLOGY"                  => List( Biotechnology(contents.mkString(" ")) )
      case "CATALYTIC ACTIVITY"             => List( CatalyticActivity(contents.mkString(" ")) )
      case "CAUTION"                        => List( Caution(contents.mkString(" ")) )
      case "COFACTOR"                       => List( Cofactor(contents.mkString(" ")) )
      case "DEVELOPMENTAL STAGE"            => List( DevelopmentalStage(contents.mkString(" ")) )
      case "DISEASE"                        => List( Disease(contents.mkString(" ")) )
      case "DISRUPTION PHENOTYPE"           => List( DisruptionPhenotype(contents.mkString(" ")) )
      case "DOMAIN"                         => List( Domain(contents.mkString(" ")) )
      case "ENZYME REGULATION"              => List( EnzymeRegulation(contents.mkString(" ")) )
      case "FUNCTION"                       => List( Function(contents.mkString(" ")) )
      case "INDUCTION"                      => List( Induction(contents.mkString(" ")) )
      case "INTERACTION"                    => List( Interaction(contents.mkString(" ")) )
      case "MASS SPECTROMETRY"              => List( MassSpectrometry(contents.mkString(" ")) )
      case "MISCELLANEOUS"                  => List( Miscellaneous(contents.mkString(" ")) )
      case "PATHWAY"                        => List( Pathway(contents.mkString(" ")) )
      case "PHARMACEUTICAL"                 => List( Pharmaceutical(contents.mkString(" ")) )
      case "POLYMORPHISM"                   => List( Polymorphism(contents.mkString(" ")) )
      case "PTM"                            => List( PTM(contents.mkString(" ")) )
      case "RNA EDITING"                    => List( RNAEditing(contents.mkString(" ")) )
      case "SEQUENCE CAUTION"               => List( SequenceCaution(contents.mkString(" ")) )
      case "SIMILARITY"                     => List( Similarity(contents.mkString(" ")) )
      case "SUBCELLULAR LOCATION"           => List( SubcellularLocation(contents.mkString(" ")) )
      case "SUBUNIT"                        => List( Subunit(contents.mkString(" ")) )
      case "TISSUE SPECIFICITY"             => List( TissueSpecificity(contents.mkString(" ")) )
      case "TOXIC DOSE"                     => List( ToxicDose(contents.mkString(" ")) )
      case "WEB RESOURCE"                   => List( WebResource(contents.mkString(" ")) )
    }
  }

  private def commentBlocks(commentLines: Seq[String]): Seq[Seq[String]] =
    commentLines.foldLeft[collection.mutable.Buffer[Seq[String]]](new collection.mutable.UnrolledBuffer[Seq[String]]){ (acc: collection.mutable.Buffer[Seq[String]], line: String) =>
      // extra lines for a comment
      if(line startsWith "    ") {
        acc.updated(acc.length - 1, acc.last :+ line.trim)
      }
      else {
        acc += List(line.stripPrefix("-!-").trim)
      }
    }

  /*
    Isoforms sample

    ```
    -!- ALTERNATIVE PRODUCTS:
        Event=Alternative splicing, Alternative initiation; Named isoforms=8;
          Comment=Additional isoforms seem to exist;
        Name=1; Synonyms=Non-muscle isozyme;
          IsoId=Q15746-1; Sequence=Displayed;
        Name=2;
          IsoId=Q15746-2; Sequence=VSP_004791;
        Name=3A;
          IsoId=Q15746-3; Sequence=VSP_004792, VSP_004794;
        Name=3B;
          IsoId=Q15746-4; Sequence=VSP_004791, VSP_004792, VSP_004794;
        Name=4;
          IsoId=Q15746-5; Sequence=VSP_004792, VSP_004793;
    ```

    The input here has lines **already trimmed**.
  */
  private def isoformBlocks(altProdLines: Seq[String]): Seq[Seq[String]] =
    altProdLines
      .dropWhile(altProdLine => !altProdLine.startsWith("Name="))
      .foldLeft(new collection.mutable.UnrolledBuffer[Seq[String]]()){ (acc: collection.mutable.UnrolledBuffer[Seq[String]], line: String) =>
      // same iso
      if(!(line startsWith "Name="))
        acc.updated(acc.length - 1, acc.last :+ line.trim)
      else
        acc += List(line.trim)
    }

  /*
    The input here is assumed to be

    ```
    Name=1; Synonyms=Non-muscle isozyme;
    IsoId=Q15746-1; Sequence=Displayed;
    Name=2;
    IsoId=Q15746-2; Sequence=VSP_004791;
    Name=3A;
    IsoId=Q15746-3; Sequence=VSP_004792, VSP_004794;
    ```
  */
  private def isoformFromBlock(isoLines: Seq[String]): Isoform =
    Isoform(
      name    = isoLines.head.stripPrefix("Name=").takeWhile(_!=';'),
      id      = isoLines.tail.head.stripPrefix("IsoId=").takeWhile(_!=';'),
      isEntry = isoLines.tail.head.containsSlice("Sequence=Displayed")
    )
}
