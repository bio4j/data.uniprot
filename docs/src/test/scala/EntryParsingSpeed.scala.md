
```scala
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