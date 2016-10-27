
```scala
package bio4j.data.uniprot.flat

import java.time.LocalDate

sealed trait LineType { lazy val asString: String = toString }

case object LineType {

  case object ID extends LineType
  case object AC extends LineType
  case object DT extends LineType
  case object DE extends LineType
  case object GN extends LineType
  case object OS extends LineType
  case object OG extends LineType
  case object OX extends LineType
  // reference lines begin
  case object RN extends LineType
  case object RP extends LineType
  case object RC extends LineType
  case object RX extends LineType
  case object RG extends LineType
  case object RA extends LineType
  case object RT extends LineType
  case object RL extends LineType
  // reference lines end
  case object OH extends LineType
  case object CC extends LineType
  case object DR extends LineType
  case object PE extends LineType
  case object KW extends LineType
  case object FT extends LineType
  case object SQ extends LineType
  case object `  ` extends LineType

  def fromString(rep: String): Option[LineType] =
    rep match {
      case ID.asString  => Some(ID)
      case AC.asString  => Some(AC)
      case DT.asString  => Some(DT)
      case DE.asString  => Some(DE)
      case GN.asString  => Some(GN)
      case OS.asString  => Some(OS)
      case OG.asString  => Some(OG)
      case OX.asString  => Some(OX)
      case OH.asString  => Some(OH)
      case CC.asString  => Some(CC)
      case DR.asString  => Some(DR)
      case PE.asString  => Some(PE)
      case KW.asString  => Some(KW)
      case FT.asString  => Some(FT)
      case SQ.asString  => Some(SQ)
      case `  `.asString => Some(`  `)
    }
}

case object Line {

  def isOfType(lt: LineType): String => Boolean =
    line => (line take 2) == lt.asString

  @inline
  def contentOf(line: String): String =
    line drop 5
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