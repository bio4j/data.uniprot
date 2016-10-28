package bio4j.data.uniprot.test

import org.scalatest.FunSuite
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._
import bio4j.test.ReleaseOnlyTest
import bio4j.data.uniprot._
import java.time.LocalDate

class EntryParsingSpeed extends FunSuite with TimeLimitedTests {

  def timeLimit = 100 seconds

  import testData.entries

  test("SwissProt all entry fields", ReleaseOnlyTest) {

    entries.foreach { e =>

      val id = e.identification;
      val ac = e.accessionNumbers;
      val dt = e.date
      val de = e.description
      val gn = e.geneNames
      val og = e.organelles
      val ox = e.taxonomyCrossReference
      val oh = e.organismHost
      val cc = e.comments
      val dr = e.databaseCrossReferences
      val pe = e.proteinExistence
      val kw = e.keywords
      val ft = e.features
      val sq = e.sequenceHeader
      val sd = e.sequence
    }
  }
}
