package bio4j.data.uniprot.flat

import bio4j.data.uniprot.{ DatabaseCrossReference, ResourceAbbreviation }
import bio4j.data.uniprot.seqOps._
/*
  Example:

  ```
  EMBL; U29082; AAA68403.1; -; Genomic_DNA.
  Allergome; 3541; Asc s 1.0101.
  ArachnoServer; AS000173; kappa-hexatoxin-Hv1b.
  Bgee; ENSMUSG00000032315; -.
  ```
*/
case class DR(val lines: Seq[String]) extends AnyVal {

  @inline
  def databaseCrossReferences: Seq[DatabaseCrossReference] =
    lines map { line =>

      val (firstFrag, rest1)  = line.span(_!=';')
      val (secondFrag, rest2) = rest1.stripPrefix(";").span(_!=';')

      DatabaseCrossReference(
        resource          = ResourceAbbreviation.fromString(firstFrag.trim),
        identifier        = secondFrag.trim
        // TODO other info?
        // otherInformation  = None,
        // isoformID         = None
      )
    }
}
