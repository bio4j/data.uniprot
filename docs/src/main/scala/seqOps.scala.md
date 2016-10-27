
```scala
package bio4j.data.uniprot

case object seqOps {

  implicit class SequenceOps[A](val s: Seq[A]) extends AnyVal {
```


Splits `s` into segments according to `pred`: those intervals at which `pred` is true are dropped, and the rest are returned in the same order.


```scala
    def splitSegments(pred: A => Boolean): Seq[Seq[A]] =
      splitSegments_rec(s, Seq(), pred)

    @annotation.tailrec
    private def splitSegments_rec[X](xs: Seq[X], acc: Seq[Seq[X]], pred: X => Boolean): Seq[Seq[X]] = {

      val (segment, rest) = xs span { x => (!pred(x)) }

      val nextAcc = if(segment.isEmpty) acc else acc :+ segment

      if(rest.isEmpty)
        nextAcc
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
      splitSegments_rec(str, Seq(), pred)

    @annotation.tailrec
    private def splitSegments_rec(xs: String, acc: Seq[String], pred: Char => Boolean): Seq[String] = {

      val (segment, rest) = xs span { x => (!pred(x)) }

      val nextAcc = if(segment.isEmpty) acc else acc :+segment

      if(rest.isEmpty)
        nextAcc
      else
        splitSegments_rec(
          xs    = rest dropWhile { x => pred(x) },
          acc   = nextAcc,
          pred  = pred
        )
    }
  }
}

```




[test/scala/lines.scala]: ../../test/scala/lines.scala.md
[test/scala/testData.scala]: ../../test/scala/testData.scala.md
[test/scala/FlatFileEntry.scala]: ../../test/scala/FlatFileEntry.scala.md
[test/scala/EntryParsingSpeed.scala]: ../../test/scala/EntryParsingSpeed.scala.md
[test/scala/FileReadSpeed.scala]: ../../test/scala/FileReadSpeed.scala.md
[test/scala/SeqOps.scala]: ../../test/scala/SeqOps.scala.md
[main/scala/entry.scala]: entry.scala.md
[main/scala/flat/SequenceData.scala]: flat/SequenceData.scala.md
[main/scala/flat/KW.scala]: flat/KW.scala.md
[main/scala/flat/ID.scala]: flat/ID.scala.md
[main/scala/flat/RC.scala]: flat/RC.scala.md
[main/scala/flat/DT.scala]: flat/DT.scala.md
[main/scala/flat/Entry.scala]: flat/Entry.scala.md
[main/scala/flat/GN.scala]: flat/GN.scala.md
[main/scala/flat/parsers.scala]: flat/parsers.scala.md
[main/scala/flat/RG.scala]: flat/RG.scala.md
[main/scala/flat/DR.scala]: flat/DR.scala.md
[main/scala/flat/OG.scala]: flat/OG.scala.md
[main/scala/flat/RL.scala]: flat/RL.scala.md
[main/scala/flat/SQ.scala]: flat/SQ.scala.md
[main/scala/flat/PE.scala]: flat/PE.scala.md
[main/scala/flat/OS.scala]: flat/OS.scala.md
[main/scala/flat/CC.scala]: flat/CC.scala.md
[main/scala/flat/OX.scala]: flat/OX.scala.md
[main/scala/flat/OH.scala]: flat/OH.scala.md
[main/scala/flat/RN.scala]: flat/RN.scala.md
[main/scala/flat/DE.scala]: flat/DE.scala.md
[main/scala/flat/RA.scala]: flat/RA.scala.md
[main/scala/flat/RX.scala]: flat/RX.scala.md
[main/scala/flat/FT.scala]: flat/FT.scala.md
[main/scala/flat/AC.scala]: flat/AC.scala.md
[main/scala/flat/RP.scala]: flat/RP.scala.md
[main/scala/flat/lineTypes.scala]: flat/lineTypes.scala.md
[main/scala/flat/RT.scala]: flat/RT.scala.md
[main/scala/seqOps.scala]: seqOps.scala.md