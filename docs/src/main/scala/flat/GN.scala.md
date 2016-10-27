
```scala
package bio4j.data.uniprot.flat

import bio4j.data.uniprot._, seqOps._
```


```
GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
GN   and
GN   Name=Jon99Ciii; Synonyms=SER2, SER5, Ser99Db; ORFNames=CG15519;
```


```scala
case class GN(val lines: Seq[String]) extends AnyVal {

  final def geneNames: Seq[GeneName] =
    (geneNameLines map names).map { geneNames =>

      val (nameOpt, restOfNames)  = nameAndSynonyms(geneNames)
      val (olcNs, restOfNames2)   = orderedLocusNames(restOfNames)
      val orfNs                   = ORFNames(restOfNames2)

      GeneName(
        name              = nameOpt,
        orderedLocusNames = olcNs.toSeq,
        ORFNames          = orfNs.toSeq
      )
    }

  private def geneNameLines: Seq[Seq[String]] =
    lines splitSegments { _ startsWith GN.geneSeparatorPrefix }

  private def names(geneNameLs: Seq[String]): Seq[String] =
    geneNameLs flatMap { line => line.splitSegments(_==';').map(_.trim) }

  // here rest is the rest of names
  private def nameAndSynonyms(allNames: Seq[String]): (Option[Name], Seq[String]) =
    allNames
      .headOption.fold[(Option[Name], Seq[String])]((None, Seq())){ first: String =>

        val nameOpt: Option[String] =
          if(first startsWith GN.NamePrefix)
            Some(first stripPrefix GN.NamePrefix)
          else
            None

        val (synonymsStr, rest) =
          allNames.tail span { _ startsWith GN.SynonymsPrefix }

        val synonyms =
          synonymsStr flatMap { str =>
            (str stripPrefix GN.SynonymsPrefix).splitSegments(_ == ',').map(_.trim)
          }

        ( nameOpt map { name => Name(official = name, synonyms = synonyms) }, rest )
      }

  // returns (ordered locus names, rest)
  private def orderedLocusNames(restOfNames: Seq[String]): (Seq[String], Seq[String]) =
    restOfNames.headOption.fold((restOfNames,restOfNames)) { first =>
      if(first startsWith GN.OrderedLocusNamesPrefix)
        (first.stripPrefix(GN.OrderedLocusNamesPrefix).splitSegments(_==',').map(_.trim), restOfNames.tail)
      else
        (Seq(), restOfNames)
    }

  // returns orfnames
  private def ORFNames(restOfNames: Seq[String]): Seq[String] =
    restOfNames.headOption.fold(restOfNames) { first =>
      if(first startsWith GN.ORFNamesPrefix)
          first
            .stripPrefix(GN.ORFNamesPrefix)
            .splitSegments(_==',').map(_.trim)
      else
        restOfNames
    }
}

case object GN {

  val geneSeparatorPrefix       = "and"
  val NamePrefix                = "Name="
  val SynonymsPrefix            = "Synonyms="
  val OrderedLocusNamesPrefix   = "OrderedLocusNames="
  val ORFNamesPrefix            = "ORFNames="
}

```




[test/scala/lines.scala]: ../../../test/scala/lines.scala.md
[test/scala/testData.scala]: ../../../test/scala/testData.scala.md
[test/scala/FlatFileEntry.scala]: ../../../test/scala/FlatFileEntry.scala.md
[test/scala/EntryParsingSpeed.scala]: ../../../test/scala/EntryParsingSpeed.scala.md
[test/scala/FileReadSpeed.scala]: ../../../test/scala/FileReadSpeed.scala.md
[test/scala/SeqOps.scala]: ../../../test/scala/SeqOps.scala.md
[main/scala/entry.scala]: ../entry.scala.md
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