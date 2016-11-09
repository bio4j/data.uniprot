
```scala
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
```


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


```scala
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
```


The input here is assumed to be

```
Name=1; Synonyms=Non-muscle isozyme;
IsoId=Q15746-1; Sequence=Displayed;
Name=2;
IsoId=Q15746-2; Sequence=VSP_004791;
Name=3A;
IsoId=Q15746-3; Sequence=VSP_004792, VSP_004794;
```


```scala
  private def isoformFromBlock(isoLines: Seq[String]): Isoform =
    Isoform(
      name    = isoLines.head.stripPrefix("Name=").takeWhile(_!=';'),
      id      = isoLines.tail.head.stripPrefix("IsoId=").takeWhile(_!=';'),
      isEntry = isoLines.tail.head.containsSlice("Sequence=Displayed")
    )
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