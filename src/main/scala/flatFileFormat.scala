package bio4j.data.uniprot

import java.time.LocalDate
import seqOps._

/*
  ## Line classes

  These classes correspond to each line type, with their value being the corresponding lines in an entry, stripped of their prefix and space.

  All parsing happens here in each of these classes, so that they are easily testable independently.
*/
case class GN(val value: Seq[String])
case class OS(val value: Seq[String])
case class OG(val value: Seq[String])
case class OX(val value: Seq[String])
case class RN(val value: Seq[String])
case class RP(val value: Seq[String])
case class RC(val value: Seq[String])
case class RX(val value: Seq[String])
case class RG(val value: Seq[String])
case class RA(val value: Seq[String])
case class RT(val value: Seq[String])
case class RL(val value: Seq[String])
case class OH(val value: Seq[String])
case class CC(val value: Seq[String])
case class DR(val value: Seq[String])
case class PE(val value: Seq[String])
case class KW(val value: Seq[String])
case class FT(val value: Seq[String])
case class SQ(val value: Seq[String])



sealed trait LineType { lazy val asString: String = toString }
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

case object LineType {

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
      case _            => None
    }
}

case class Line(
  val lineType: LineType,
  val content: String
)
{

  def ofType(some: LineType): Option[Line] =
    if(lineType == some) Some(this) else None

  def isOfType(some: LineType): Boolean =
    lineType == some
}

case object Line {

  def from(line: String): Option[Line] = {

    val lineTypeRep = line take 2

    (LineType fromString lineTypeRep) map { lineType =>

      val lineContent = line drop 5

      Line(lineType, lineContent)
    }
  }

  def isOfType(lt: LineType)(line: String): Boolean =
    (line take 2) == lt.asString

  def isReferenceLine(line: String): Boolean =
    isOfType(RN)(line) ||
    isOfType(RP)(line) ||
    isOfType(RC)(line) ||
    isOfType(RX)(line) ||
    isOfType(RG)(line) ||
    isOfType(RA)(line) ||
    isOfType(RT)(line) ||
    isOfType(RL)(line)


  def contentOf(line: String): String =
    line drop 5

  import seqOps._

  implicit class LineOps(val line: Line) extends AnyVal {

    def splitAtSemicolon: Seq[String] =
      line.content.splitSegments(_ == ';').map(_.trim)
  }

  implicit class LinesOps(val lines: Seq[Line]) extends AnyVal {

    def join: Option[Line] = {

      val newContent = lines.map(_.content).mkString("")

      if(lines.isEmpty) None else Some(Line(lines.head.lineType, newContent))
    }
  }
}
