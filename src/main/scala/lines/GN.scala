package bio4j.data.uniprot.lines

import bio4j.data.uniprot._, seqOps._

/*
  ```
  GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;
  GN   and
  GN   Name=Jon99Ciii; Synonyms=SER2, SER5, Ser99Db; ORFNames=CG15519;
  ```
*/
case class GN(val lines: Seq[String]) extends AnyVal {

  final def geneNames: Seq[GeneName] =
    (geneNameLines map names).map { geneNames =>

      val (nameOpt, restOfNames)  = nameAndSynonyms(geneNames)
      val (olcNs, restOfNames2)   = orderedLocusNames(restOfNames)
      val orfNs                   = ORFNames(restOfNames2)

      GeneName(
        name              = nameOpt,
        orderedLocusNames = olcNs.toSeq,
        ORFNames          = orfNs.toSeq
      )
    }

  private def geneNameLines: Seq[Seq[String]] =
    lines splitSegments { _ startsWith GN.geneSeparatorPrefix }

  private def names(geneNameLs: Seq[String]): Seq[String] =
    geneNameLs flatMap { line => line.splitSegments(_==';').map(_.trim) }

  // here rest is the rest of names
  private def nameAndSynonyms(allNames: Seq[String]): (Option[Name], Seq[String]) =
    allNames
      .headOption.fold[(Option[Name], Seq[String])]((None, Seq())){ first: String =>

        val nameOpt: Option[String] =
          if(first startsWith GN.NamePrefix)
            Some(first stripPrefix GN.NamePrefix)
          else
            None

        val (synonymsStr, rest) =
          allNames.tail span { _ startsWith GN.SynonymsPrefix }

        val synonyms =
          synonymsStr flatMap { str =>
            (str stripPrefix GN.SynonymsPrefix).splitSegments(_ == ',').map(_.trim)
          }

        ( nameOpt map { name => Name(official = name, synonyms = synonyms) }, rest )
      }

  // returns (ordered locus names, rest)
  private def orderedLocusNames(restOfNames: Seq[String]): (Seq[String], Seq[String]) =
    restOfNames.headOption.fold((restOfNames,restOfNames)) { first =>
      if(first startsWith GN.OrderedLocusNamesPrefix)
        (first.stripPrefix(GN.OrderedLocusNamesPrefix).splitSegments(_==',').map(_.trim), restOfNames.tail)
      else
        (Seq(), restOfNames)
    }

  // returns orfnames
  private def ORFNames(restOfNames: Seq[String]): Seq[String] =
    restOfNames.headOption.fold(restOfNames) { first =>
      if(first startsWith GN.ORFNamesPrefix)
          first
            .stripPrefix(GN.ORFNamesPrefix)
            .splitSegments(_==',').map(_.trim)
      else
        restOfNames
    }
}

case object GN {

  val geneSeparatorPrefix       = "and"
  val NamePrefix                = "Name="
  val SynonymsPrefix            = "Synonyms="
  val OrderedLocusNamesPrefix   = "OrderedLocusNames="
  val ORFNamesPrefix            = "ORFNames="
}
