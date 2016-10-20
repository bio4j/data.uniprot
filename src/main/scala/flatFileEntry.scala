package bio4j.data.uniprot

import java.time.LocalDate

case class FlatFileEntry(val lines: Seq[String]) extends AnyEntry {

  def accessionNumbers: AccessionNumber = ???
  def comments: Seq[Comment] = ???
  def databaseCrossReferences: Seq[DatabaseCrossReference] = ???
  def date: Date = ???
  def description: Description = ???
  def features: Seq[Feature] = ???
  def geneNames: Seq[GeneName] = ???
  def identification: Identification = ???
  def keywords: Seq[Keyword] = ???
  def organelle: Option[Organelle] = ???
  def organismClassification: OrganismClassification = ???
  def organismHost: Seq[TaxonomyCrossReference] = ???
  def organismSpecies: OrganismSpecies = ???
  def proteinExistence: ProteinExistence = ???
  def sequence: Sequence = ???
  def sequenceHeader: SequenceHeader = ???
  def taxonomyCrossReference: TaxonomyCrossReference = ???
}

case object ParseAccessionNumber {

  def apply(acLines: Seq[Line]): Option[AccessionNumber] =
    ???
}

case object ParseIdentification {

  def apply(idLine: Line): Option[Identification] =
    idLine.ofType(ID) flatMap { line =>

      val id =
        line.content.takeWhile(_ != ' ')

      val statusRep =
        line.content
          .drop(24)
          .takeWhile(_ != ';')

      val length =
        line.content
          .reverse
          .drop(4) // remove ".AA "
          .takeWhile(_ != ' ')
          .reverse

      // TODO parse status, map on option for nonempty stuff etc
      ???
    }
}

case object ParseAccession {

  def apply(acLines: Seq[Line]): Option[AccessionNumber] = {

    val validLines: Seq[Line] =
      acLines.filter(_.isOfType(AC))

    val allIDs =
      validLines
        .map(_.content).mkString("")  // join all lines
        .split(';').map(_.trim)       // split and trim values

    val primaryID = allIDs.headOption

    primaryID map { id =>
      AccessionNumber(
        primary   = id,
        secondary = allIDs.tail
      )
    }
  }
}


sealed trait LineType { lazy val asString: String = toString }
  case object ID extends LineType
  case object AC extends LineType
  case object DT extends LineType
  case object DE extends LineType
  case object GN extends LineType
  case object OS extends LineType
  case object OG extends LineType
  case object OX extends LineType
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
) {

  def ofType(some: LineType): Option[Line] =
    if(lineType == some) Some(this) else None

  def isOfType(some: LineType): Boolean =
    lineType == some
}

case object ParseLine {

  def apply(line: String): Option[Line] = {

    val lineTypeRep = line take 2

    (LineType fromString lineTypeRep) map { lineType =>

      val lineContent = line drop 5

      Line(lineType, lineContent)
    }
  }
}
