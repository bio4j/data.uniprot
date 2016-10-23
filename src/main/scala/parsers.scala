package bio4j.data.uniprot

import java.time.LocalDate
import seqOps._

case object parsers {

  def entries(lines: Iterator[String]): Iterator[Array[String]] = new Iterator[Array[String]] {

    private val rest: BufferedIterator[String] = lines.buffered

    def hasNext: Boolean =
      rest.hasNext

    def next(): Array[String] =
      entry

    @annotation.tailrec
    private def entry_rec(acc: Array[String]): Array[String] =
      if (rest.hasNext) {
        if( rest.head.startsWith("//") ) {

          rest.next()
          acc
        }
        else entry_rec(acc :+ rest.next())
      }
      else acc

    private def entry: Array[String] = entry_rec(Array())
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
