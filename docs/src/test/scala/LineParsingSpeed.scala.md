
```scala
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

```




[test/scala/LineParsingSpeed.scala]: LineParsingSpeed.scala.md
[test/scala/lines.scala]: lines.scala.md
[test/scala/testData.scala]: testData.scala.md
[test/scala/FlatFileEntry.scala]: FlatFileEntry.scala.md
[test/scala/EntryParsingSpeed.scala]: EntryParsingSpeed.scala.md
[test/scala/FileReadSpeed.scala]: FileReadSpeed.scala.md
[test/scala/SeqOps.scala]: SeqOps.scala.md
[main/scala/entry.scala]: ../../main/scala/entry.scala.md
[main/scala/flat/SequenceData.scala]: ../../main/scala/flat/SequenceData.scala.md
[main/scala/flat/KW.scala]: ../../main/scala/flat/KW.scala.md
[main/scala/flat/ID.scala]: ../../main/scala/flat/ID.scala.md
[main/scala/flat/RC.scala]: ../../main/scala/flat/RC.scala.md
[main/scala/flat/DT.scala]: ../../main/scala/flat/DT.scala.md
[main/scala/flat/Entry.scala]: ../../main/scala/flat/Entry.scala.md
[main/scala/flat/GN.scala]: ../../main/scala/flat/GN.scala.md
[main/scala/flat/parsers.scala]: ../../main/scala/flat/parsers.scala.md
[main/scala/flat/RG.scala]: ../../main/scala/flat/RG.scala.md
[main/scala/flat/DR.scala]: ../../main/scala/flat/DR.scala.md
[main/scala/flat/OG.scala]: ../../main/scala/flat/OG.scala.md
[main/scala/flat/RL.scala]: ../../main/scala/flat/RL.scala.md
[main/scala/flat/SQ.scala]: ../../main/scala/flat/SQ.scala.md
[main/scala/flat/PE.scala]: ../../main/scala/flat/PE.scala.md
[main/scala/flat/OS.scala]: ../../main/scala/flat/OS.scala.md
[main/scala/flat/CC.scala]: ../../main/scala/flat/CC.scala.md
[main/scala/flat/OX.scala]: ../../main/scala/flat/OX.scala.md
[main/scala/flat/OH.scala]: ../../main/scala/flat/OH.scala.md
[main/scala/flat/RN.scala]: ../../main/scala/flat/RN.scala.md
[main/scala/flat/DE.scala]: ../../main/scala/flat/DE.scala.md
[main/scala/flat/RA.scala]: ../../main/scala/flat/RA.scala.md
[main/scala/flat/RX.scala]: ../../main/scala/flat/RX.scala.md
[main/scala/flat/FT.scala]: ../../main/scala/flat/FT.scala.md
[main/scala/flat/AC.scala]: ../../main/scala/flat/AC.scala.md
[main/scala/flat/RP.scala]: ../../main/scala/flat/RP.scala.md
[main/scala/flat/lineTypes.scala]: ../../main/scala/flat/lineTypes.scala.md
[main/scala/flat/RT.scala]: ../../main/scala/flat/RT.scala.md
[main/scala/seqOps.scala]: ../../main/scala/seqOps.scala.md