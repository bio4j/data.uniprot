package bio4j.data.uniprot

case object seqOps {

  implicit class SequenceOps[A](val s: Seq[A]) extends AnyVal {

    def splitSegments(pred: A => Boolean): Seq[Seq[A]] =
      splitSegments_rec(s, Seq[Seq[A]](), pred)

    @annotation.tailrec
    private def splitSegments_rec[X](xs: Seq[X], acc: Seq[Seq[X]], pred: X => Boolean): Seq[Seq[X]] = {

      val (segment, rest) = xs span { x => !pred(x) }

      if(rest.isEmpty)
        acc
      else
        splitSegments_rec( rest dropWhile pred, acc :+ xs, pred )
    }

  }

  // String is not Seq[Char], so need to repeat all this
  implicit class StringOps(val str: String) extends AnyVal {

    def splitSegments(pred: Char => Boolean): Seq[String] =
      splitSegments_rec(str, Seq(), pred)

    @annotation.tailrec
    private def splitSegments_rec(xs: String, acc: Seq[String], pred: Char => Boolean): Seq[String] = {

      val (segment, rest) = xs span { x => !pred(x) }

      if(rest.isEmpty)
        acc
      else
        splitSegments_rec( rest dropWhile pred, acc :+ xs, pred )
    }
  }
}
