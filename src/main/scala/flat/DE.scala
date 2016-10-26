package bio4j.data.uniprot.flat

import bio4j.data.uniprot._, seqOps._

/*
  ```
  DE   RecName: Full=Annexin A5;
  DE            Short=Annexin-5;
  DE   AltName: Full=Annexin V;
  DE   AltName: Full=Lipocortin V;
  DE   AltName: Full=Endonexin II;
  DE   AltName: Full=Calphobindin I;
  DE   AltName: Full=CBP-I;
  DE   AltName: Full=Placental anticoagulant protein I;
  DE            Short=PAP-I;
  DE   AltName: Full=PP4;
  DE   AltName: Full=Thromboplastin inhibitor;
  DE   AltName: Full=Vascular anticoagulant-alpha;
  DE            Short=VAC-alpha;
  DE   AltName: Full=Anchorin CII;
  ```

  another:

  ```
  DE   RecName: Full=Granulocyte colony-stimulating factor;
  DE            Short=G-CSF;
  DE   AltName: Full=Pluripoietin;
  DE   AltName: Full=Filgrastim;
  DE   AltName: Full=Lenograstim;
  DE   Flags: Precursor;
  ```
*/

case class DE(val value: Seq[String]) extends AnyVal {

  final def description: Description =
    Description(
      recommendedName,
      alternativeNames,
      submittedNames
    )

  private def recommendedName: Option[RecommendedName] = {

    val rl = recommendedNameLines

    if(rl.isEmpty) None else Some(
      RecommendedName(
        full  = nameValue(rl.head), // know is the first one
        short = rl.tail.filter(_.startsWith(DE.shortPrefix)).map(nameValue(_)),
        ec    = rl.tail.filter(_.startsWith(DE.ECPrefix)).map(nameValue(_))
      )
    )
  }

  private def alternativeNames: Seq[AlternativeName] = {

    val rl = alternativeNamesLines

    rl map { ls =>

      AlternativeName(
        full  = if(ls.head startsWith DE.fullPrefix) Some(nameValue(ls.head)) else None,
        short = ls.filter(_.startsWith(DE.shortPrefix)).map(nameValue(_)),
        ec    = ls.filter(_.startsWith(DE.ECPrefix)).map(nameValue(_))
      )
    }
  }

  private def submittedNames: Seq[SubmittedName] = {

    val snl = submittedNamesLines

    val op = if(snl.isEmpty) None else Some(
      SubmittedName(
        full  = nameValue(snl.head), // know is the first one
        ec    = snl.tail.filter(_.startsWith(DE.ECPrefix)).map(nameValue(_))
      )
    )

    op.toSeq//.toArray
  }

  private def alternativeNamesLines: Seq[Seq[String]] =
    alternativeNamesLines_rec(Seq[Seq[String]](), value)

  @annotation.tailrec
  private def alternativeNamesLines_rec(acc: Seq[Seq[String]], ls: Seq[String]): Seq[Seq[String]] = {

    // find first alt name
    val (useless, rest) = ls.span(l => !(l startsWith DE.alternativeNamePrefix))

    if(rest.isEmpty)
      acc
    else {

      val (otherAltNames, newRest) = rest.tail.span(_.startsWith(DE.emptyPrefix))

      val altNameBlock =
        rest.head.stripPrefix(DE.alternativeNamePrefix).trim +: otherAltNames.map(_.stripPrefix(DE.emptyPrefix).trim)

      alternativeNamesLines_rec(acc :+ altNameBlock, newRest)
    }
  }

  private def submittedNamesLines: Seq[String] = {

    val (submittedNames, rest) =
      value.span(_.startsWith(DE.submittedNamePrefix))

    if( submittedNames.nonEmpty )
      submittedNames.map(_.stripPrefix(DE.submittedNamePrefix).trim) ++
        rest.takeWhile(_.startsWith(DE.emptyPrefix)).map(_.trim)
    else
      Seq()
  }

  /*
    this method returns the values, already trimmed:
  */
  private def recommendedNameLines: Seq[String] = {

    // if there's a recommended line, is the first one
    val (recNameLine, rest) =
      value.span(_.startsWith(DE.recommendedNamePrefix))

    if( recNameLine.nonEmpty )
      recNameLine.map(_.stripPrefix(DE.recommendedNamePrefix).trim) ++
        rest.takeWhile(_.startsWith(DE.emptyPrefix)).map(_.trim)
    else
      Seq()
  }

  private def nameValue(str: String): String =
    str
      .dropWhile(_ != '=').drop(1)
      .stripSuffix(";")

}

case object DE {

  val prefixLength          = 9
  val recommendedNamePrefix = "RecName:"
  val alternativeNamePrefix = "AltName:"
  val submittedNamePrefix   = "SubName:"
  val emptyPrefix           = "    "
  val fullPrefix            = "Full="
  val ECPrefix              = "EC="
  val shortPrefix           = "Short"
}
