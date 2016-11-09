package bio4j.data.uniprot.test

import org.scalatest.FunSuite
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._
// import bio4j.test.ReleaseOnlyTest
import com.bio4j.data.uniprot._
import java.time.LocalDate
import ohnosequences.fastarious

class IsoformSequences extends FunSuite with TimeLimitedTests {

  def fastaLines =
    io.Source.fromFile("uniprot_sprot_varsplic.fasta").getLines

  def timeLimit = 2 seconds

  test("parse all isoform sequences") {

    fasta.isoformSequences.fromLines(fastaLines) foreach { isoSeq =>

      val id        = isoSeq.ID
      val sequence  = isoSeq.sequence
    }
  }
}
