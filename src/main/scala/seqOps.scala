package bio4j.data.uniprot

case object seqOps {

  implicit class SequenceOps[A](val s: Seq[A]) extends AnyVal {

    /*
      Splits `s` into segments according to `pred`: those intervals at which `pred` is true are dropped, and the rest are returned in the same order.
    */
    def splitSegments(pred: A => Boolean): Seq[Seq[A]] =
      splitSegments_rec(s, new collection.mutable.UnrolledBuffer[Seq[A]](), pred)

    @annotation.tailrec
    private def splitSegments_rec[X](xs: Seq[X], acc: collection.mutable.UnrolledBuffer[Seq[X]], pred: X => Boolean): Seq[Seq[X]] = {

      val (segment, rest) = xs span { x => (!pred(x)) }

      val nextAcc = if(segment.isEmpty) acc else acc += segment

      if(rest.isEmpty)
        nextAcc.toVector
      else
        splitSegments_rec(
          xs    = rest dropWhile { x => pred(x) },
          acc   = nextAcc,
          pred  = pred
        )
    }
  }

  // String is not Seq[Char], so need to repeat all this
  implicit class StringOps(val str: String) extends AnyVal {

    def splitSegments(pred: Char => Boolean): Seq[String] =
      splitSegments_rec(str, collection.mutable.UnrolledBuffer(), pred)

    @annotation.tailrec
    private def splitSegments_rec(xs: String, acc: collection.mutable.Buffer[String], pred: Char => Boolean): Seq[String] = {

      val (segment, rest) = xs span { x => (!pred(x)) }

      val nextAcc = if(segment.isEmpty) acc else acc += segment

      if(rest.isEmpty)
        nextAcc.toVector
      else
        splitSegments_rec(
          xs    = rest dropWhile { x => pred(x) },
          acc   = nextAcc,
          pred  = pred
        )
    }
  }
}
