package bio4j.data.uniprot.lines

import bio4j.data.uniprot._, seqOps._

case class OG(val lines: Seq[String]) extends AnyVal {

  def organelles: Seq[Organelle] =
    OG organellesFromLines lines

}

case object OG {

  def organellesFromLines(reps: Seq[String]): Seq[Organelle] =
    reps.head match {

      case "Hydrogenosome." => Seq(Hydrogenosome)
      case "Mitochondrion." => Seq(Mitochondrion)
      case "Nucleomorph."   => Seq(Nucleomorph)
      case "Plastid."       => Seq(Plastid)
      case x if(x startsWith "Plastid;") =>
        (x stripPrefix "Plastid;").trim match {
          case "Apicoplast."                  => Seq(Apicoplast)
          case "Chloroplast."                 => Seq(Chloroplast)
          case "Organellar chromatophore."    => Seq(OrganellarChromatophore)
          case "Cyanelle."                    => Seq(Cyanelle)
          case "Non-photosynthetic plastid."  => Seq(NonPhotosyntheticPlastid)
        }
      case y if(y startsWith "Plasmid") =>
        reps
          .map(p => p.stripSuffix(" and"))
          .flatMap {
            _.splitSegments(_ == ',')
              .map{ frgmt => Plasmid(frgmt.trim.stripPrefix("Plasmid").trim.stripSuffix(".")) }
          }
    }

}
