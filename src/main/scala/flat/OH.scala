package bio4j.data.uniprot.flat

import bio4j.data.uniprot.TaxonomyCrossReference

/*
  ```
  OH   NCBI_TaxID=9481; Callithrix.
  OH   NCBI_TaxID=9536; Cercopithecus hamlyni (Owl-faced monkey) (Hamlyn's monkey).
  OH   NCBI_TaxID=9539; Macaca (macaques).
  OH   NCBI_TaxID=9598; Pan troglodytes (Chimpanzee).
  ```
*/
case class OH(val lines: Seq[String]) extends AnyVal {

  def taxonomyCrossReferences: Seq[TaxonomyCrossReference] =
    lines map { line =>
      TaxonomyCrossReference(
        line
          .stripPrefix("NCBI_TaxID=")
          .takeWhile(_ != ';')
      )
    }
}
