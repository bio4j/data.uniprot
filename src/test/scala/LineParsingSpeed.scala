package bio4j.data.uniprot.test

import org.scalatest.FunSuite
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._
import bio4j.test.ReleaseOnlyTest
import bio4j.data.uniprot._
import java.time.LocalDate

class LinesParsingSpeed extends FunSuite with TimeLimitedTests {

  def timeLimit = 30 seconds

  import testData.entries

  test("SwissProt ID", ReleaseOnlyTest) { entries.foreach { e => val id = e.identification;         } }
  test("SwissProt AC", ReleaseOnlyTest) { entries.foreach { e => val ac = e.accessionNumbers;       } }
  test("SwissProt DT", ReleaseOnlyTest) { entries.foreach { e => val dt = e.date                    } }
  test("SwissProt DE", ReleaseOnlyTest) { entries.foreach { e => val de = e.description             } }
  test("SwissProt GN", ReleaseOnlyTest) { entries.foreach { e => val gn = e.geneNames               } }
  test("SwissProt OG", ReleaseOnlyTest) { entries.foreach { e => val og = e.organelles              } }
  test("SwissProt OX", ReleaseOnlyTest) { entries.foreach { e => val ox = e.taxonomyCrossReference  } }
  test("SwissProt OH", ReleaseOnlyTest) { entries.foreach { e => val oh = e.organismHost            } }
  test("SwissProt CC", ReleaseOnlyTest) { entries.foreach { e => val cc = e.comments                } }
  test("SwissProt DR", ReleaseOnlyTest) { entries.foreach { e => val dr = e.databaseCrossReferences } }
  test("SwissProt PE", ReleaseOnlyTest) { entries.foreach { e => val pe = e.proteinExistence        } }
  test("SwissProt KW", ReleaseOnlyTest) { entries.foreach { e => val kw = e.keywords                } }
  test("SwissProt FT", ReleaseOnlyTest) { entries.foreach { e => val ft = e.features                } }
  test("SwissProt SQ", ReleaseOnlyTest) { entries.foreach { e => val sq = e.sequenceHeader          } }
  test("SwissProt --", ReleaseOnlyTest) { entries.foreach { e => val x = e.sequence                 } }
}
