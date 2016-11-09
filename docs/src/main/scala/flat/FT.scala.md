
```scala
package bio4j.data.uniprot.flat

import bio4j.data.uniprot._
```


The format is

```
CARBOHYD    251    251       N-linked (GlcNAc...).
```

We have

1. feature type: [0,8[
2. from: [10,16[
3. to: [18,24[
4. description: [31,-[ and possibly the next lines/s


```scala
case class FT(val lines: Seq[String]) extends AnyVal {

  def features: Seq[Feature] =
    featureBlocks map featureFrom

  private def featureBlocks: Seq[Seq[String]] =
    lines.foldLeft[Seq[Seq[String]]](Vector()){ (acc: Seq[Seq[String]], line: String) =>
      // extra lines for a feature
      if(line startsWith "    ") {
        acc.updated(acc.length -1, acc.last :+ line.trim)
      }
      else {
        acc :+ Vector(line)
      }
    }

  private def featureFrom(featureBlock: Seq[String]): Feature = {

    val firstLine = featureBlock.head

    Feature(
      key         = FeatureKey.fromString( firstLine.slice(0,8).trim ),
      from        = firstLine.slice(10,16).trim,
      to          = firstLine.slice(18,24).trim,
      description = (firstLine.drop(29).trim +: featureBlock.tail.map(_.trim)).mkString(" ")
    )
  }
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