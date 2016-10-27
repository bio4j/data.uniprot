
```scala
package bio4j.data.uniprot.test

import org.scalatest.FunSuite
import bio4j.test.ReleaseOnlyTest
import bio4j.data.uniprot._
import java.time.LocalDate

class EntryParsingSpeed extends FunSuite {

  // more or less the same as the raw read speed
  test("split whole SwissProt into entry lines", ReleaseOnlyTest) {

    flat.parsers.entries(testData.swissProtLines).foreach { e => () }
  }

  // more or less the same as the raw read speed; everything's lazy here
  test("parse whole SwissProt", ReleaseOnlyTest) {

    flat.parsers.entries(testData.swissProtLines).map(flat.Entry.from).foreach { e => () }
  }

  // ~26s
  test("parse whole SwissProt, access some data", ReleaseOnlyTest) {

    flat.parsers.entries(testData.swissProtLines).map(flat.Entry.from).foreach { e =>

      val z = e.accessionNumbers.primary
      val u = e.date.creation
      val v = e.identification.status

      e.description.recommendedName.foreach { n => if(n.full.isEmpty) println("empty full name!!") }
    }
  }

  // ~15s
  test  ("All SwissProt entries have a full name", ReleaseOnlyTest) {

    val noOfEntries = 551987

    val fullNameCount =
      flat.parsers.entries(testData.swissProtLines)
        .map(flat.Entry.from)
        .foldLeft(0){ (acc, e) =>

          acc + e.description.recommendedName.fold(0){_ => 1}
        }

    assert { fullNameCount == noOfEntries }
  }
}

```




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