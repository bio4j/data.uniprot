package bio4j.data.uniprot

import java.time.LocalDate
import seqOps._

/*
  ## Line classes

  These classes correspond to each line type, with their value being the corresponding lines in an entry, stripped of their prefix and space.

  All parsing happens here in each of these classes, so that they are easily testable independently.
*/
case class ID(val value: String) extends AnyVal {

  def id: String =
    value takeWhile { _ != ' ' }

  def status: Status = {

    val statusStr =
      value
        .drop(24) // magic number!
        .takeWhile(_ != ';')

    if(statusStr == Reviewed.asString) Reviewed else Unreviewed
  }

  def length: Int =
    value
      .trim
      .stripSuffix(" AA.")
      .reverse
      .takeWhile(_ != ' ')
      .reverse
      .toInt
}

case class AC(val lines: Seq[String]) extends AnyVal {

  private def joinedLines: String =
    lines.mkString("")

  def accesions: Seq[String] =
    joinedLines
      .splitSegments(_ == ';')
      .map(_.trim)
}

case class DT(val value: Seq[String]) {

  private lazy val dates: Seq[LocalDate] =
    value
      .map( l => parsers.localDateFrom( l takeWhile { _ != ',' } ) )

  private lazy val versions: Seq[Int] =
    value
      .drop(1)
      .map(line =>
        line
          .reverse
          .drop(1)
          .takeWhile(_ != ' ')
          .reverse
          .toInt
      )

  def creation: LocalDate =
    dates(0)

  def sequenceLastModified: VersionedDate =
    VersionedDate(dates(1), versions(0))

  def entryLastModified: VersionedDate =
    VersionedDate(dates(2), versions(1))
}

case class DE(val value: Seq[String])
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

case object parsers {

  def entries(lines: Iterator[String]) = new Iterator[Seq[String]] {

    private val rest: BufferedIterator[String] = lines.buffered

    def hasNext: Boolean =
      rest.hasNext

    def next(): Seq[String] =
      entry

    @annotation.tailrec
    private def entry_rec(acc: Array[String]): Array[String] =
      if (rest.hasNext) {
        if( rest.head.startsWith("//") ) {

          val drop = rest.next()
          acc
        }
        else entry_rec(acc :+ rest.next())
      }
      else acc

    private def entry: Seq[String] = entry_rec(Array())
  }

  // see http://stackoverflow.com/a/33521793/614394
  lazy val localDateFormatter =
    new java.time.format.DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern("dd-MMM-yyyy")
      .toFormatter(java.util.Locale.ENGLISH)

  def localDateFrom(rep: String): LocalDate =
    LocalDate.parse(rep, localDateFormatter)
}
