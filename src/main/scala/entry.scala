/*
This set of types serves as a generic model for UniProt entries. The main reference is the **[UniProt Knowledgebase user manual](http://web.expasy.org/docs/userman.html)**.

The method names match the "Content" description in the table at the end of the [General Structure section](http://web.expasy.org/docs/userman.html#entrystruc), with plurals signaling a `Seq` return type.
*/
package bio4j.data.uniprot

import java.time.LocalDate

// NOTE not sure about all these `Any`s being of any use
trait AnyEntry extends Any {

  def identification          : Identification
  def accessionNumbers        : AccessionNumber
  def date                    : Date
  def description             : Description
  def geneNames               : Seq[GeneName]
  def organismSpecies         : OrganismSpecies
  def organelle               : Option[Organelle]
  def organismClassification  : OrganismClassification
  def taxonomyCrossReference  : TaxonomyCrossReference
  def organismHost            : Seq[TaxonomyCrossReference]
  // skipping references; will consider doing them
  def comments                : Seq[Comment]
  def databaseCrossReferences : Seq[DatabaseCrossReference]
  def proteinExistence        : ProteinExistence
  def keywords                : Seq[Keyword]
  def features                : Seq[Feature]
  def sequenceHeader          : SequenceHeader
  def sequence                : Sequence
}

/* http://web.expasy.org/docs/userman.html#ID_line */
case class Identification(
  val entryName : String,
  val status    : Status,
  val length    : Int
)

sealed trait Status { lazy val asString = toString }
  case object Reviewed    extends Status
  case object Unreviewed  extends Status

/* http://web.expasy.org/docs/userman.html#AC_line */
case class AccessionNumber(
  val primary   : String,
  val secondary : Seq[String]
)

/* http://web.expasy.org/docs/userman.html#DT_line */
case class Date(
  val creation              : LocalDate,
  val sequenceLastModified  : VersionedDate,
  val entryLastModified     : VersionedDate
)

case class VersionedDate(
  val date          : LocalDate,
  val versionNumber : Int
)

/* http://web.expasy.org/docs/userman.html#DE_line */
case class Description(
  val recommendedName   : RecommendedName,
  val alternativeNames  : Seq[AlternativeName],
  val submittedNames    : Seq[SubmittedName]
)

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
case class GeneName(
  val name              : Option[Name],
  val orderedLocusNames : Seq[String],
  val ORFNames          : Seq[String]
)

case class Name(
  val official  : String,
  val synonyms  : Seq[String]
)

/* http://web.expasy.org/docs/userman.html#OS_line */
case class OrganismSpecies(val name: String) extends AnyVal

/* http://web.expasy.org/docs/userman.html#OG_line */
sealed trait Organelle              extends Any
  case object Hydrogenosome             extends Organelle
  case object Mitochondrion             extends Organelle
  case object Nucleomorph               extends Organelle
  case class Plasmid(val name: String)  extends Organelle
  sealed trait Plastid               extends Organelle
    case object Plastid                   extends Plastid
    case object Apicoplast                extends Plastid
    case object Chloroplast               extends Plastid
    case object OrganellarChromatophore   extends Plastid
    case object Cyanelle                  extends Plastid
    case object NonPhotosyntheticPlastid  extends Plastid


/* http://web.expasy.org/docs/userman.html#OC_line */
case class OrganismClassification(val lineageNames: Seq[String]) extends AnyVal

/* http://web.expasy.org/docs/userman.html#OX_line */
/* http://web.expasy.org/docs/userman.html#OH_line */
case class TaxonomyCrossReference(val taxonID: String) extends AnyVal

/* http://web.expasy.org/docs/userman.html#CC_line */
sealed trait Comment               extends Any
  case class Allergen(val text: String) extends AnyVal with Comment
  case class Isoform(
    val name    : String,
    val id      : String,
    // val event   : String, // NOTE needed?
    val isEntry : Boolean
  ) extends Comment
  /* All this classes contain more information than a simple text; the format, however, is loosely defined. */
  case class BiophysicochemicalProperties(val text: String)   extends AnyVal with Comment
  case class Biotechnology(val text: String)                  extends AnyVal with Comment
  case class CatalyticActivity(val text: String)              extends AnyVal with Comment
  case class Caution(val text: String)                        extends AnyVal with Comment
  case class Cofactor(val text: String)                       extends AnyVal with Comment
  case class DevelopmentalStage(val text: String)             extends AnyVal with Comment
  case class Disease(val text: String)                        extends AnyVal with Comment
  case class DisruptionPhenotype(val text: String)            extends AnyVal with Comment
  case class Domain(val text: String)                         extends AnyVal with Comment
  case class EnzymeRegulation(val text: String)               extends AnyVal with Comment
  case class Function(val text: String)                       extends AnyVal with Comment
  case class Induction(val text: String)                      extends AnyVal with Comment
  case class Interaction(val text: String)                    extends AnyVal with Comment
  case class MassSpectrometry(val text: String)               extends AnyVal with Comment
  case class Miscellaneous(val text: String)                  extends AnyVal with Comment
  case class Pathway(val text: String)                        extends AnyVal with Comment
  case class Pharmaceutical(val text: String)                 extends AnyVal with Comment
  case class Polymorphism(val text: String)                   extends AnyVal with Comment
  case class PTM(val text: String)                            extends AnyVal with Comment
  case class RNAEditing(val text: String)                     extends AnyVal with Comment
  case class SequenceCaution(val text: String)                extends AnyVal with Comment
  case class Similarity(val text: String)                     extends AnyVal with Comment
  case class SubcellularLocation(val text: String)            extends AnyVal with Comment
  case class Subunit(val text: String)                        extends AnyVal with Comment
  case class TissueSpecificity(val text: String)              extends AnyVal with Comment
  case class ToxicDose(val text: String)                      extends AnyVal with Comment
  case class WebResource(val text: String)                    extends AnyVal with Comment


/* http://web.expasy.org/docs/userman.html#DR_line */
case class DatabaseCrossReference(
  val resource          : ResourceAbbreviation,
  val identifier        : String,
  val otherInformation  : Option[String],
  val isoformID         : Option[String]
)

/* All these resource abbreviations have an `asString` field which matches their String representation in all UniProt data. */
sealed trait ResourceAbbreviation {

  lazy val asString: String = toString
}
  case object EMBL                  extends ResourceAbbreviation { val description: String = "Nucleotide sequence database of EMBL/EBI (see 3.25)" }
  case object Allergome             extends ResourceAbbreviation { val description: String = "Allergome; a platform for allergen knowledge" }
  case object ArachnoServer         extends ResourceAbbreviation { val description: String = "ArachnoServer: Spider toxin database" }
  case object Bgee                  extends ResourceAbbreviation { val description: String = "Bgee dataBase for Gene Expression Evolution" }
  case object BindingDB             extends ResourceAbbreviation { val description: String = "The Binding Database" }
  case object BioCyc                extends ResourceAbbreviation { val description: String = "Collection of Pathway/Genome Databases" }
  case object BioGrid               extends ResourceAbbreviation { val description: String = "BioGrid, The Biological General Repository for Interaction Datasets" }
  case object BioMuta               extends ResourceAbbreviation { val description: String = "BioMuta curated single-nucleotide variation and disease association database" }
  case object BRENDA                extends ResourceAbbreviation { val description: String = "BRENDA Comprehensive Enzyme Information System" }
  case object CAZy                  extends ResourceAbbreviation { val description: String = "Carbohydrate-Active enZymes" }
  case object CCDS                  extends ResourceAbbreviation { val description: String = "The Consensus CDS (CCDS) project" }
  case object CDD                   extends ResourceAbbreviation { val description: String = "Conserved Domains Database" }
  case object ChEMBL                extends ResourceAbbreviation { val description: String = "A database of bioactive drug-like small molecules." }
  case object ChiTaRS               extends ResourceAbbreviation { val description: String = "A database of human, mouse and fruit fly chimeric transcripts and RNA-sequencing data" }
  case object CGD                   extends ResourceAbbreviation { val description: String = "Candida genome database" }
  case object CleanEx               extends ResourceAbbreviation { val description: String = "Public gene expression data via unique approved gene symbols" }
  case object `COMPLUYEAST-2DPAGE`  extends ResourceAbbreviation { val description: String = "2-D database at Universidad Complutense de Madrid" }
  case object CollecTF              extends ResourceAbbreviation { val description: String = "CollecTF database of bacterial transcription factor binding sites" }
  case object ConoServer            extends ResourceAbbreviation { val description: String = "ConoServer: ConeCone snail toxin database" }
  case object CTD                   extends ResourceAbbreviation { val description: String = "Comparative Toxicogenomics Database" }
  case object dictyBase             extends ResourceAbbreviation { val description: String = "Dictyostelium discoideum online informatics resource" }
  case object DIP                   extends ResourceAbbreviation { val description: String = "Database of interacting proteins" }
  case object DMDM                  extends ResourceAbbreviation { val description: String = "Domain mapping of disease mutations" }
  case object DNASU                 extends ResourceAbbreviation { val description: String = "The DNASU plasmid repository" }
  case object `DOSAC-COBS-2DPAGE`   extends ResourceAbbreviation { val description: String = "2D-PAGE database from the dipartimento oncologico di III Livello" }
  case object DisProt               extends ResourceAbbreviation { val description: String = "Database of protein disorders" }
  case object DrugBank              extends ResourceAbbreviation { val description: String = "The DrugBank database (DrugBank)" }
  case object EchoBASE              extends ResourceAbbreviation { val description: String = "The integrated post-genomic database for E. coli (EchoBASE)" }
  case object EcoGene               extends ResourceAbbreviation { val description: String = "Escherichia coli K12 genome database (EcoGene)" }
  case object eggNOG                extends ResourceAbbreviation { val description: String = "evolutionary genealogy of genes: Non-supervised Orthologous Groups" }
  case object Ensembl               extends ResourceAbbreviation { val description: String = "Database of automatically annotated sequences of large genomes (Ensembl database)" }
  case object EnsemblBacteria       extends ResourceAbbreviation { val description: String = "This databases is part of Ensembl Genomes, which has been created to complement the existing Ensembl site, the focus of which are vertebrate genomes." }
  case object EnsemblFungi          extends ResourceAbbreviation { val description: String = "This databases is part of Ensembl Genomes, which has been created to complement the existing Ensembl site, the focus of which are vertebrate genomes." }
  case object EnsemblMetazoa        extends ResourceAbbreviation { val description: String = "This databases is part of Ensembl Genomes, which has been created to complement the existing Ensembl site, the focus of which are vertebrate genomes." }
  case object EnsemblPlants         extends ResourceAbbreviation { val description: String = "This databases is part of Ensembl Genomes, which has been created to complement the existing Ensembl site, the focus of which are vertebrate genomes." }
  case object EnsemblProtists       extends ResourceAbbreviation { val description: String = "This databases is part of Ensembl Genomes, which has been created to complement the existing Ensembl site, the focus of which are vertebrate genomes." }
  case object EPD                   extends ResourceAbbreviation { val description: String = "The Encyclopedia of Proteome Dynamics is a resource that contains data from multiple, large-scale proteomics experiments aimed at characterising proteome dynamics in both human cells and model organisms." }
  case object ESTHER                extends ResourceAbbreviation { val description: String = "The server ESTHER (ESTerases and alpha/beta-Hydrolase Enzymes and Relatives) is dedicated to the analysis of proteins or protein domains belonging to the superfamily of alpha/beta-hydrolases, exemplified by the cholinesterases." }
  case object euHCVdb               extends ResourceAbbreviation { val description: String = "The European Hepatitis C Virus database" }
  case object EuPathDB              extends ResourceAbbreviation { val description: String = "Eukaryotic Pathogen Database Resources" }
  case object EvolutionaryTrace     extends ResourceAbbreviation { val description: String = "The Evolutionary Trace ranks amino acid residues in a protein sequence by their relative evolutionary importance." }
  case object ExpressionAtlas       extends ResourceAbbreviation { val description: String = "Information on gene expression patterns under different biological conditions." }
  case object FlyBase               extends ResourceAbbreviation { val description: String = "Drosophila genome database (FlyBase)" }
  case object Gene3D                extends ResourceAbbreviation { val description: String = "Database of structural assignments for genes (Gene3D)" }
  case object GeneCards             extends ResourceAbbreviation { val description: String = "GeneCards: human genes, protein and diseases" }
  case object GeneDB                extends ResourceAbbreviation { val description: String = "GeneDB pathogen genome database from Sanger Institute" }
  case object GeneID                extends ResourceAbbreviation { val description: String = "Database of genes from NCBI RefSeq genomes" }
  case object GeneReviews           extends ResourceAbbreviation { val description: String = "GeneReviews, a resource of expert-authored, peer-reviewed disease descriptions." }
  case object GeneWiki              extends ResourceAbbreviation { val description: String = "GeneWiki, an initiative that aims to create seed articles for every notable human gene." }
  case object GenomeRNAi            extends ResourceAbbreviation { val description: String = "Database of phenotypes from RNA interference screens in Drosophila and Homo sapiens." }
  case object GeneTree              extends ResourceAbbreviation { val description: String = "The phylogenetic gene trees that are available at http://www.ensembl.org/ and http://ensemblgenomes.org/." }
  case object Genevisible           extends ResourceAbbreviation { val description: String = "Genevisible is a free resource to explore public expression data for a gene of interest and find the top five tissues, cell lines, cancers or perturbations in which it has the highest expression or response." }
  case object GO                    extends ResourceAbbreviation { val description: String = "Gene Ontology (GO) database" }
  case object Gramene               extends ResourceAbbreviation { val description: String = "Comparative mapping resource for grains (Gramene)" }
  case object GuidetoPHARMACOLOGY   extends ResourceAbbreviation { val description: String = "An expert-driven guide to pharmacological targets and the substances that act on them" }
  case object HGNC                  extends ResourceAbbreviation { val description: String = "Human gene nomenclature database (HGNC)" }
  case object `H-InvDB`             extends ResourceAbbreviation { val description: String = "Human gene database H-Invitational Database (H-InvDB)" }
  case object HAMAP                 extends ResourceAbbreviation { val description: String = "Database of microbial protein families (HAMAP)" }
  case object HOGENOM               extends ResourceAbbreviation { val description: String = "Homologous genes from fully sequenced organisms database" }
  case object HOVERGEN              extends ResourceAbbreviation { val description: String = "Homologous vertebrate genes database" }
  case object HPA                   extends ResourceAbbreviation { val description: String = "Human Protein Atlas" }
  case object InParanoid            extends ResourceAbbreviation { val description: String = "InParanoid: Eukaryotic Ortholog Groups" }
  case object IntAct                extends ResourceAbbreviation { val description: String = "Protein interaction database and analysis system (IntAct)" }
  case object InterPro              extends ResourceAbbreviation { val description: String = "Integrated resource of protein families, domains and functional sites (InterPro)" }
  case object IPI                   extends ResourceAbbreviation { val description: String = "International Protein Index" }
  case object iPTMnet               extends ResourceAbbreviation { val description: String = "iPTMnet integrated resource for PTMs in systems biology context" }
  case object KEGG                  extends ResourceAbbreviation { val description: String = "Kyoto encyclopedia of genes and genomes" }
  case object KO                    extends ResourceAbbreviation { val description: String = "KEGG Orthology" }
  case object LegioList             extends ResourceAbbreviation { val description: String = "Legionella pneumophila (strains Paris and Lens) genome database" }
  case object Leproma               extends ResourceAbbreviation { val description: String = "Mycobacterium leprae genome database (Leproma)" }
  case object MaizeGDB              extends ResourceAbbreviation { val description: String = "Maize Genetics/Genomics Database (MaizeGDB)" }
  case object MalaCards             extends ResourceAbbreviation { val description: String = "MalaCards human disease database" }
  case object MaxQB                 extends ResourceAbbreviation { val description: String = "MaxQB - The MaxQuant DataBase" }
  case object MEROPS                extends ResourceAbbreviation { val description: String = "Peptidase database (MEROPS)" }
  case object MGI                   extends ResourceAbbreviation { val description: String = "Mouse Genome Informatics Database (MGI)" }
  case object MIM                   extends ResourceAbbreviation { val description: String = "Mendelian Inheritance in Man Database (MIM)" }
  case object MINT                  extends ResourceAbbreviation { val description: String = "Molecular INTeraction database" }
  case object mycoCLAP              extends ResourceAbbreviation { val description: String = "mycoCLAP" }
  case object neXtProt              extends ResourceAbbreviation { val description: String = "neXtProt, the human protein knowledge platform" }
  case object OGP                   extends ResourceAbbreviation { val description: String = "Oxford GlycoProteomics 2-DE database (OGP)" }
  case object OMA                   extends ResourceAbbreviation { val description: String = "Identification of Orthologs from Complete Genome Data" }
  case object Orphanet              extends ResourceAbbreviation { val description: String = "Orphanet; a database dedicated to information on rare diseases and orphan drugs" }
  case object OrthoDB               extends ResourceAbbreviation { val description: String = "Database of Orthologous Groups" }
  case object PANTHER               extends ResourceAbbreviation { val description: String = "Protein ANalysis THrough Evolutionary Relationships (PANTHER) Classification System" }
  case object PATRIC                extends ResourceAbbreviation { val description: String = "Pathosystems Resource Integration Center" }
  case object PaxDb                 extends ResourceAbbreviation { val description: String = "A comprehensive absolute protein abundance database" }
  case object PDB                   extends ResourceAbbreviation { val description: String = "3D-macromolecular structure Protein Data Bank (PDB)" }
  case object PDBsum                extends ResourceAbbreviation { val description: String = "PDB sum" }
  case object PeptideAtlas          extends ResourceAbbreviation { val description: String = "PeptideAtlas" }
  case object PeroxiBase            extends ResourceAbbreviation { val description: String = "Peroxidase superfamilies database" }
  case object Pfam                  extends ResourceAbbreviation { val description: String = "Pfam protein domain database" }
  case object PharmGKB              extends ResourceAbbreviation { val description: String = "The Pharmacogenetics and Pharmacogenomics Knowledge Base" }
  case object PhosphoSite           extends ResourceAbbreviation { val description: String = "Phosphorylation site database" }
  case object PhylomeDB             extends ResourceAbbreviation { val description: String = "Database for complete collections of gene phylogenies" }
  case object PIR                   extends ResourceAbbreviation { val description: String = "Protein sequence database of the Protein Information Resource (PIR)" }
  case object PIRSF                 extends ResourceAbbreviation { val description: String = "Protein classification system of PIR (PIRSF)" }
  case object `PMAP-CutDB`          extends ResourceAbbreviation { val description: String = "CutDB - Proteolytic event database" }
  case object PomBase               extends ResourceAbbreviation { val description: String = "Schizosaccharomyces pombe database" }
  case object PRIDE                 extends ResourceAbbreviation { val description: String = "PRoteomics IDEntifications database" }
  case object PRINTS                extends ResourceAbbreviation { val description: String = "Protein Fingerprint database (PRINTS)" }
  case object ProDom                extends ResourceAbbreviation { val description: String = "ProDom protein domain database" }
  case object PRO                   extends ResourceAbbreviation { val description: String = "PRO provides an ontological representation of protein-related entities." }
  case object ProMEX                extends ResourceAbbreviation { val description: String = "Protein Mass spectra EXtraction database" }
  case object PROSITE               extends ResourceAbbreviation { val description: String = "PROSITE protein domain and family database (see 3.25.126)" }
  case object ProteinModelPortal    extends ResourceAbbreviation { val description: String = "Protein Model Portal, a module of the Protein Structure Initiative Knowledgebase (PSI KB) to unify the model data from the different sites." }
  case object PseudoCAP             extends ResourceAbbreviation { val description: String = "Pseudomonas aeruginosa Community Annotation Project" }
  case object Reactome              extends ResourceAbbreviation { val description: String = "Curated resource of core pathways and reactions in human biology (Reactome)" }
  case object REBASE                extends ResourceAbbreviation { val description: String = "Restriction enzymes and methylases database (REBASE)" }
  case object RefSeq                extends ResourceAbbreviation { val description: String = "NCBI reference sequences" }
  case object `REPRODUCTION-2DPAGE` extends ResourceAbbreviation { val description: String = "2D-PAGE database from the Lab of Reproductive Medicine at the Nanjing Medical University" }
  case object RGD                   extends ResourceAbbreviation { val description: String = "Rat Genome Database (RGD)" }
  case object `SABIO-RK`            extends ResourceAbbreviation { val description: String = "Biochemical Reaction Kinetics Database" }
  case object SGD                   extends ResourceAbbreviation { val description: String = "Saccharomyces Genome Database (SGD)" }
  case object SignaLink             extends ResourceAbbreviation { val description: String = "A signaling pathway resource with multi-layered regulatory networks" }
  case object SIGNOR                extends ResourceAbbreviation { val description: String = "A signaling network open resource (SIGNOR)" }
  case object SMART                 extends ResourceAbbreviation { val description: String = "Simple Modular Architecture Research Tool (SMART)" }
  case object SMR                   extends ResourceAbbreviation { val description: String = "The SWISS-MODEL Repository (SMR)" }
  case object STRING                extends ResourceAbbreviation { val description: String = "STRING: functional protein association networks" }
  case object SUPFAM                extends ResourceAbbreviation { val description: String = "Superfamily database of structural and functional annotation" }
  case object SWISS                 extends ResourceAbbreviation { val description: String = "-2DPAGE 	2D-PAGE database from the Geneva University Hospital (SWISS-2DPAGE)" }
  case object SwissLipids           extends ResourceAbbreviation { val description: String = "SwissLipids knowledge resource for lipid biology" }
  case object SwissPalm             extends ResourceAbbreviation { val description: String = "SwissPalm database of S-palmitoylation events" }
  case object TAIR                  extends ResourceAbbreviation { val description: String = "The Arabidopsis Information Resource (TAIR)" }
  case object TCDB                  extends ResourceAbbreviation { val description: String = "Transport Classification Database" }
  case object TIGRFAMs              extends ResourceAbbreviation { val description: String = "TIGR protein family database (TIGRFAMs)" }
  case object TopDownProteomics     extends ResourceAbbreviation { val description: String = "TopDownProteomics is a resource from the Consortium for Top Down Proteomics that hosts top down proteomics data presenting validated proteoforms to the scientific community." }
  case object TreeFam               extends ResourceAbbreviation { val description: String = "TreeFam database of animal gene trees" }
  case object TubercuList           extends ResourceAbbreviation { val description: String = "Mycobacterium tuberculosis H37Rv genome database (TubercuList)" }
  case object `UCD-2DPAGE`          extends ResourceAbbreviation { val description: String = "UCD-2DPAGE: University College Dublin 2-DE Proteome Database." }
  case object UniGene               extends ResourceAbbreviation { val description: String = "UniGene database" }
  case object UCSC                  extends ResourceAbbreviation { val description: String = "UCSC genome browser" }
  case object UniCarbKB             extends ResourceAbbreviation { val description: String = "UniCarbKB, a new collaborative approach to glycomics." }
  case object UniPathway            extends ResourceAbbreviation { val description: String = "UniPathway: a resource for the exploration and annotation of metabolic pathways" }
  case object VectorBase            extends ResourceAbbreviation { val description: String = "Bioinformatics resource for invertebrate vectors of human pathogens" }
  case object `World-2DPAGE`        extends ResourceAbbreviation { val description: String = "The World-2DPAGE database" }
  case object WormBase              extends ResourceAbbreviation { val description: String = "A multi-species resource for nematode biology and genomics (WormBase)" }
  case object WBParaSite            extends ResourceAbbreviation { val description: String = "WormBase ParaSite (WBParaSite) resource for parasitic worms (helminths)" }
  case object Xenbase               extends ResourceAbbreviation { val description: String = "Xenopus laevis and tropicalis biology and genome database" }
  case object ZFIN                  extends ResourceAbbreviation { val description: String = "Zebrafish Information Network genome database (ZFIN)" }


/* http://web.expasy.org/docs/userman.html#PE_line */
sealed trait ProteinExistence
  case object EvidenceAtProteinLevel    extends ProteinExistence
  case object EvidenceAtTranscriptLevel extends ProteinExistence
  case object InferredFromHomology      extends ProteinExistence
  case object Predicted                 extends ProteinExistence
  case object Uncertain                 extends ProteinExistence

/* http://web.expasy.org/docs/userman.html#KW_line */
case class Keyword(
  val id          : String,
  val description : String
)

/* http://web.expasy.org/docs/userman.html#FT_line */
case class Feature(
  val key         : FeatureKey,
  val from        : String,
  val to          : String,
  val description : String
)

sealed trait FeatureKey { lazy val asString: String = toString }
  case object INIT_MET      extends FeatureKey
  case object SIGNAL        extends FeatureKey
  case object PROPEP        extends FeatureKey
  case object TRANSIT       extends FeatureKey
  case object CHAIN         extends FeatureKey
  case object PEPTIDE       extends FeatureKey
  case object TOPO_DOM      extends FeatureKey
  case object TRANSMEM      extends FeatureKey
  case object INTRAMEM      extends FeatureKey
  case object DOMAIN        extends FeatureKey
  case object REPEAT        extends FeatureKey
  case object CA_BIND       extends FeatureKey
  case object ZN_FING       extends FeatureKey
  case object DNA_BIND      extends FeatureKey
  case object NP_BIND       extends FeatureKey
  case object REGION        extends FeatureKey
  case object COILED        extends FeatureKey
  case object MOTIF         extends FeatureKey
  case object COMPBIAS      extends FeatureKey
  case object ACT_SITE      extends FeatureKey
  case object METAL         extends FeatureKey
  case object BINDING       extends FeatureKey
  case object SITE          extends FeatureKey
  case object NON_STD       extends FeatureKey
  case object MOD_RES       extends FeatureKey
  case object LIPID         extends FeatureKey
  case object CARBOHYD      extends FeatureKey
  case object DISULFID      extends FeatureKey
  case object CROSSLNK      extends FeatureKey
  case object VAR_SEQ       extends FeatureKey
  case object VARIANT       extends FeatureKey
  case object MUTAGEN       extends FeatureKey
  case object UNSURE        extends FeatureKey
  case object CONFLICT      extends FeatureKey
  case object NON_CONS      extends FeatureKey
  case object NON_TER       extends FeatureKey
  case object HELIX         extends FeatureKey
  case object STRAND        extends FeatureKey
  case object TURN          extends FeatureKey


/* http://web.expasy.org/docs/userman.html#SQ_line */
case class SequenceHeader(
  val length          : Int,
  val molecularWeight : Int,
  val crc64           : String
)
/* http://web.expasy.org/docs/userman.html#Seq_line */
case class Sequence(val value: String) extends AnyVal
