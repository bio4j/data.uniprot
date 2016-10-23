package bio4j.data.uniprot.lines

import bio4j.data.uniprot._, seqOps._

/*
  Example:

  ```
    ID   CYC_BOVIN               Reviewed;         104 AA.
    ID   GIA2_GIALA              Reviewed;         296 AA.
    ID   Q5JU06_HUMAN            Unreviewed;       268 AA.
  ```
*/
case class ID(val value: Array[Char]) extends AnyVal {

  // TODO change it to getting slices
  def id: Array[Char] =
    value takeWhile { _ != ' ' }

  def status: Status = {

    val statusStr: Array[Char] =
      value
        .drop(24) // magic number!
        .takeWhile(_ != ';')

    if(statusStr sameElements Reviewed.asString.toCharArray) Reviewed else Unreviewed
  }

  def length: Int =
    value
      // // .trim
      // .stripSuffix(" AA.")
      // .reverse
      // .takeWhile(_ != ' ')
      // .reverse
      // .toInt
      .reverse
      .drop(4)
      .takeWhile(_ != ' ')
      .reverse
      .mkString
      .toInt
}
