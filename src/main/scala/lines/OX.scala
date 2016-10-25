package bio4j.data.uniprot.lines

import bio4j.data.uniprot._

case class OX(val line: String) extends AnyVal {

  def taxonomyCrossReference: TaxonomyCrossReference =
    TaxonomyCrossReference(
      line
        .stripPrefix("NCBI_TaxID=")
        .stripSuffix(";")
    )
}
