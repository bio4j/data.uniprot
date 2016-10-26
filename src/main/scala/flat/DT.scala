package bio4j.data.uniprot.flat

import bio4j.data.uniprot._, seqOps._
import java.time.LocalDate

/*
  Example:

  ```
  DT   01-OCT-1996, integrated into UniProtKB/Swiss-Prot.
  DT   01-OCT-1996, sequence version 1.
  DT   07-FEB-2006, entry version 49.
  ```

  for TrEMBL

  ```
  DT   01-FEB-1999, integrated into UniProtKB/TrEMBL.
  DT   15-OCT-2000, sequence version 2.
  DT   15-DEC-2004, entry version 5.
  ```
*/
case class DT(val value: Seq[String]) {

  final def date: Date =
    Date(
      creation              = this.creation,
      sequenceLastModified  = this.sequenceLastModified,
      entryLastModified     = this.entryLastModified
    )

  private lazy val dates: Seq[LocalDate] =
    value
      .map( l => parsers.localDateFrom( l takeWhile { _ != ',' } ) )

  private lazy val versions: Seq[Int] =
    value
      .drop(1)
      .map(line =>
        line
          .reverse
          .drop(1)
          .takeWhile(_ != ' ')
          .reverse
          .toInt
      )

  def creation: LocalDate =
    dates(0)

  def sequenceLastModified: VersionedDate =
    VersionedDate(dates(1), versions(0))

  def entryLastModified: VersionedDate =
    VersionedDate(dates(2), versions(1))
}
