package bio4j.data.uniprot

/*
  This set of types serves as a generic model for UniProt entries. The main reference is the **[UniProt Knowledgebase user manual](http://web.expasy.org/docs/userman.html)**.

  The method names match the "Content" description in the table at the end of the [General Structure section](http://web.expasy.org/docs/userman.html#entrystruc), with plurals signaling a `Seq` return type.
*/
// NOTE not sure about all these `Any`s being of any use
trait AnyEntry extends Any {

  def identification: AnyIdentification

  def accessionNumbers: Seq[AnyIdentification]

  def date: AnyDate

  def description: AnyDescription

  // TODO review this
  def geneNames: Seq[AnyGeneName]

  def organismSpecies: AnyOrganismSpecies

  def organelle: Option[AnyOrganelle]

  def organismClassification: AnyOrganismClassification

  def taxonomyCrossReference: AnyTaxonomyCrossReference

  def organismHost: Option[AnyOrganismHost]

  // skipping references; consider doing them
  def comments: Seq[AnyComment]

  def databaseCrossReferences: Seq[AnyDatabaseCrossReference]

  def proteinExistence: AnyProteinExistence

  def keywords: Seq[AnyKeyword]

  def features: Seq[AnyFeature]

  def sequenceHeader: AnySequenceHeader

  def sequence: AnySequence
}

/* http://web.expasy.org/docs/userman.html#ID_line */
trait AnyIdentification         extends Any {

  def entryName: String

  def status: Status

  def length: Int
}

sealed trait Status {

  lazy val asString = toString
}
case object Reviewed    extends Status
case object Unreviewed  extends Status
case object Status {

  def fromString(value: String): Option[Status] =
    value match {
      case Reviewed.asString    => Some(Reviewed)
      case Unreviewed.asString  => Some(Unreviewed)
      case _                    => None
    }
}

/* http://web.expasy.org/docs/userman.html#AC_line */
trait AnyAccessionNumber        extends Any {

  def primary: String

  def secondary: Seq[String]
}

/* http://web.expasy.org/docs/userman.html#DT_line */
trait AnyDate                   extends Any {

  def creation              : java.time.LocalDate
  def sequenceLastModified  : VersionedDate
  def entryLastModified     : VersionedDate
}

case class VersionedDate(
  val date          : java.time.LocalDate,
  val versionNumber : Int
)

/* http://web.expasy.org/docs/userman.html#DE_line */
trait AnyDescription            extends Any {

  def recommendedName   : RecommendedName
  def alternativeNames  : Seq[AlternativeName]
  def submittedNames    : Seq[SubmittedName]
}

case class RecommendedName(
  val full  : String,
  val short : Seq[String],
  val ec    : Seq[String]
)

case class AlternativeName(
  val full  : Option[String],
  val short : Seq[String],
  val ec    : Seq[String]
)

case class SubmittedName(
  val full  : String,
  val ec    : Seq[String]
)

/* http://web.expasy.org/docs/userman.html#GN_line */
trait AnyGeneName               extends Any {

  def name              : Option[Name]
  def orderedLocusNames : Seq[String]
  def ORFNames          : Seq[String]
}

case class Name(
  val official  : String,
  val synonyms  : Seq[String]
)

/* http://web.expasy.org/docs/userman.html#OS_line */
trait AnyOrganismSpecies        extends Any
/* http://web.expasy.org/docs/userman.html#OG_line */
trait AnyOrganelle              extends Any
/* http://web.expasy.org/docs/userman.html#OC_line */
trait AnyOrganismClassification extends Any
/* http://web.expasy.org/docs/userman.html#OX_line */
trait AnyTaxonomyCrossReference extends Any
/* http://web.expasy.org/docs/userman.html#OH_line */
trait AnyOrganismHost           extends Any
/* http://web.expasy.org/docs/userman.html#CC_line */
trait AnyComment                extends Any
/* http://web.expasy.org/docs/userman.html#DR_line */
trait AnyDatabaseCrossReference extends Any
/* http://web.expasy.org/docs/userman.html#PE_line */
trait AnyProteinExistence       extends Any
/* http://web.expasy.org/docs/userman.html#KW_line */
trait AnyKeyword                extends Any
/* http://web.expasy.org/docs/userman.html#FT_line */
trait AnyFeature                extends Any
/* http://web.expasy.org/docs/userman.html#SQ_line */
trait AnySequenceHeader         extends Any
/* http://web.expasy.org/docs/userman.html#Seq_line */
trait AnySequence               extends Any
