package bio4j.data.uniprot.flat

import java.time.LocalDate
import bio4j.data.uniprot.seqOps._

case object parsers {

  def entries(lines: Iterator[String]): Iterator[Seq[String]] = new Iterator[Seq[String]] {

    private val rest: BufferedIterator[String] = lines.buffered

    def hasNext: Boolean =
      rest.hasNext

    def next(): Seq[String] =
      entry

    @annotation.tailrec
    private def entry_rec(acc: Vector[String]): Vector[String] =
      if (rest.hasNext) {
        if( rest.head.startsWith("//") ) {

          rest.next()
          acc
        }
        else entry_rec(acc :+ rest.next())
      }
      else acc

    private def entry: Seq[String] = entry_rec(Vector())
  }

  // see http://stackoverflow.com/a/33521793/614394
  lazy val localDateFormatter =
    new java.time.format.DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern("dd-MMM-yyyy")
      .toFormatter(java.util.Locale.ENGLISH)

  def localDateFrom(rep: String): LocalDate =
    LocalDate.parse(rep, localDateFormatter)
}
