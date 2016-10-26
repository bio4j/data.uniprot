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
case class ID(val value: String) extends AnyVal {

  final def identification: Identification =
    Identification(
      entryName = this.id,
      status    = this.status,
      length    = this.length
    )

  // TODO change it to getting slices
  def id: String =
    value takeWhile { _ != ' ' }

  def status: Status = {

    val statusStr: String =
      value
        .drop(24) // magic number!
        .takeWhile(_ != ';')

    if(statusStr sameElements Reviewed.asString.toCharArray) Reviewed else Unreviewed
  }

  def length: Int =
    value
      .reverse
      .drop(4)
      .takeWhile(_ != ' ')
      .reverse
      .toInt
}
