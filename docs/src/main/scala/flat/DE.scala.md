
```scala
package bio4j.data.uniprot.flat

import bio4j.data.uniprot._, seqOps._
```


```
DE   RecName: Full=Annexin A5;
DE            Short=Annexin-5;
DE   AltName: Full=Annexin V;
DE   AltName: Full=Lipocortin V;
DE   AltName: Full=Endonexin II;
DE   AltName: Full=Calphobindin I;
DE   AltName: Full=CBP-I;
DE   AltName: Full=Placental anticoagulant protein I;
DE            Short=PAP-I;
DE   AltName: Full=PP4;
DE   AltName: Full=Thromboplastin inhibitor;
DE   AltName: Full=Vascular anticoagulant-alpha;
DE            Short=VAC-alpha;
DE   AltName: Full=Anchorin CII;
```

another:

```
DE   RecName: Full=Granulocyte colony-stimulating factor;
DE            Short=G-CSF;
DE   AltName: Full=Pluripoietin;
DE   AltName: Full=Filgrastim;
DE   AltName: Full=Lenograstim;
DE   Flags: Precursor;
```


```scala
case class DE(val value: Seq[String]) extends AnyVal {

  final def description: Description =
    Description(
      recommendedName,
      alternativeNames,
      submittedNames
    )

  private def recommendedName: Option[RecommendedName] = {

    val rl = recommendedNameLines

    if(rl.isEmpty) None else Some(
      RecommendedName(
        full  = nameValue(rl.head), // know is the first one
        short = rl.tail.filter(_.startsWith(DE.shortPrefix)).map(nameValue(_)),
        ec    = rl.tail.filter(_.startsWith(DE.ECPrefix)).map(nameValue(_))
      )
    )
  }

  private def alternativeNames: Seq[AlternativeName] = {

    val rl = alternativeNamesLines

    rl map { ls =>

      AlternativeName(
        full  = if(ls.head startsWith DE.fullPrefix) Some(nameValue(ls.head)) else None,
        short = ls.filter(_.startsWith(DE.shortPrefix)).map(nameValue(_)),
        ec    = ls.filter(_.startsWith(DE.ECPrefix)).map(nameValue(_))
      )
    }
  }

  private def submittedNames: Seq[SubmittedName] = {

    val snl = submittedNamesLines

    val op = if(snl.isEmpty) None else Some(
      SubmittedName(
        full  = nameValue(snl.head), // know is the first one
        ec    = snl.tail.filter(_.startsWith(DE.ECPrefix)).map(nameValue(_))
      )
    )

    op.toSeq//.toArray
  }

  private def alternativeNamesLines: Seq[Seq[String]] =
    alternativeNamesLines_rec(Seq[Seq[String]](), value)

  @annotation.tailrec
  private def alternativeNamesLines_rec(acc: Seq[Seq[String]], ls: Seq[String]): Seq[Seq[String]] = {

    // find first alt name
    val (useless, rest) = ls.span(l => !(l startsWith DE.alternativeNamePrefix))

    if(rest.isEmpty)
      acc
    else {

      val (otherAltNames, newRest) = rest.tail.span(_.startsWith(DE.emptyPrefix))

      val altNameBlock =
        rest.head.stripPrefix(DE.alternativeNamePrefix).trim +: otherAltNames.map(_.stripPrefix(DE.emptyPrefix).trim)

      alternativeNamesLines_rec(acc :+ altNameBlock, newRest)
    }
  }

  private def submittedNamesLines: Seq[String] = {

    val (submittedNames, rest) =
      value.span(_.startsWith(DE.submittedNamePrefix))

    if( submittedNames.nonEmpty )
      submittedNames.map(_.stripPrefix(DE.submittedNamePrefix).trim) ++
        rest.takeWhile(_.startsWith(DE.emptyPrefix)).map(_.trim)
    else
      Seq()
  }
```


this method returns the values, already trimmed:


```scala
  private def recommendedNameLines: Seq[String] = {

    // if there's a recommended line, is the first one
    val (recNameLine, rest) =
      value.span(_.startsWith(DE.recommendedNamePrefix))

    if( recNameLine.nonEmpty )
      recNameLine.map(_.stripPrefix(DE.recommendedNamePrefix).trim) ++
        rest.takeWhile(_.startsWith(DE.emptyPrefix)).map(_.trim)
    else
      Seq()
  }

  private def nameValue(str: String): String =
    str
      .dropWhile(_ != '=').drop(1)
      .stripSuffix(";")

}

case object DE {

  val prefixLength          = 9
  val recommendedNamePrefix = "RecName:"
  val alternativeNamePrefix = "AltName:"
  val submittedNamePrefix   = "SubName:"
  val emptyPrefix           = "    "
  val fullPrefix            = "Full="
  val ECPrefix              = "EC="
  val shortPrefix           = "Short"
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