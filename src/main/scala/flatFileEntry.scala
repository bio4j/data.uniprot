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
