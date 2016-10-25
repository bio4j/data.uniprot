package bio4j.data.uniprot.lines

import bio4j.data.uniprot.{ DatabaseCrossReference, ResourceAbbreviation }
import bio4j.data.uniprot.seqOps._
/*
case class DatabaseCrossReference(
  val resource          : ResourceAbbreviation,
  val identifier        : String,
  val otherInformation  : Option[String],
  val isoformID         : Option[String]
)

  ```
  EMBL; U29082; AAA68403.1; -; Genomic_DNA.
  Allergome; 3541; Asc s 1.0101.
  ArachnoServer; AS000173; kappa-hexatoxin-Hv1b.
  Bgee; ENSMUSG00000032315; -.
  ```
*/
case class DR(val lines: Seq[String]) extends AnyVal {

  def databaseCrossReferences: Seq[DatabaseCrossReference] =
    lines map { line =>

      val fragments = line.splitSegments(_==';')
      val resourceAbbrv = fragments(0).trim
      val id            = fragments(1).trim

      DatabaseCrossReference(
        resource          = ResourceAbbreviation.fromString(resourceAbbrv),
        identifier        = id,
        // TODO other info?
        otherInformation  = None,
        isoformID         = None
      )
    }
}
