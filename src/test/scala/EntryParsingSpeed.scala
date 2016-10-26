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
