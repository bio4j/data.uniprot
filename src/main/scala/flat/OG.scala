package bio4j.data.uniprot.flat

import bio4j.data.uniprot._, seqOps._

case class OG(val lines: Seq[String]) extends AnyVal {

  def organelles: Seq[Organelle] =
    OG organellesFromLines lines

}

case object OG {

  def organellesFromLines(reps: Seq[String]): Seq[Organelle] =
    if(reps.isEmpty) Seq() else
      reps.head match {

        case x if (x startsWith "Hydrogenosome") => Seq(Hydrogenosome)
        case x if (x startsWith "Mitochondrion") => Seq(Mitochondrion)
        case x if (x startsWith "Nucleomorph")   => Seq(Nucleomorph)
        case x if (x startsWith "Plastid")       => Seq(Plastid)
        case x if (x startsWith "Plastid;") =>
          (x stripPrefix "Plastid;").trim match {
            case x if (x startsWith "Apicoplast")                 => Seq(Apicoplast)
            case x if (x startsWith "Chloroplast")                => Seq(Chloroplast)
            case x if (x startsWith "Organellar chromatophore")   => Seq(OrganellarChromatophore)
            case x if (x startsWith "Cyanelle")                   => Seq(Cyanelle)
            case x if (x startsWith "Non-photosynthetic plastid") => Seq(NonPhotosyntheticPlastid)
          }
        case y if (y startsWith "Plasmid") =>
          reps
            .map(p => p.stripSuffix(" and"))
            .flatMap {
              _.splitSegments(_ == ',')
                .map{ frgmt => Plasmid(frgmt.trim.stripPrefix("Plasmid").trim.stripSuffix(".")) }
            }
      }
}
