package bio4j.data.uniprot.lines

import bio4j.data.uniprot.{ ProteinExistence, EvidenceAtProteinLevel, EvidenceAtTranscriptLevel, InferredFromHomology, Predicted, Uncertain }

case class PE(val line: String) extends AnyVal {

  def proteinExistence: ProteinExistence =
    (line take 1) match {
      case "1" => EvidenceAtProteinLevel
      case "2" => EvidenceAtTranscriptLevel
      case "3" => InferredFromHomology
      case "4" => Predicted
      case "5" => Uncertain
    }
}
