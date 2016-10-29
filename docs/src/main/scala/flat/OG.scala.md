
```scala
package bio4j.data.uniprot.flat

import bio4j.data.uniprot._, seqOps._

case class OG(val lines: Seq[String]) extends AnyVal {

  def organelles: Seq[Organelle] =
    OG organellesFromLines lines

}

case object OG {

  def organellesFromLines(reps: Seq[String]): Seq[Organelle] =
    if(reps.isEmpty) Seq() else
      reps.head match {

        case x if (x startsWith "Hydrogenosome") => Seq(Hydrogenosome)
        case x if (x startsWith "Mitochondrion") => Seq(Mitochondrion)
        case x if (x startsWith "Nucleomorph")   => Seq(Nucleomorph)
        case x if (x startsWith "Plastid")       => Seq(Plastid)
        case x if (x startsWith "Plastid;") =>
          (x stripPrefix "Plastid;").trim match {
            case x if (x startsWith "Apicoplast")                 => Seq(Apicoplast)
            case x if (x startsWith "Chloroplast")                => Seq(Chloroplast)
            case x if (x startsWith "Organellar chromatophore")   => Seq(OrganellarChromatophore)
            case x if (x startsWith "Cyanelle")                   => Seq(Cyanelle)
            case x if (x startsWith "Non-photosynthetic plastid") => Seq(NonPhotosyntheticPlastid)
          }
        case y if (y startsWith "Plasmid") =>
          reps
            .map(p => p.stripSuffix(" and"))
            .flatMap {
              _.splitSegments(_ == ',')
                .map{ frgmt => Plasmid(frgmt.trim.stripPrefix("Plasmid").trim.stripSuffix(".")) }
            }
      }
}

```




[test/scala/LineParsingSpeed.scala]: ../../../test/scala/LineParsingSpeed.scala.md
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