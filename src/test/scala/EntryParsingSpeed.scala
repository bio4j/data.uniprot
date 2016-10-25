package bio4j.data.uniprot.test

import org.scalatest.FunSuite

import bio4j.data.uniprot._
import java.time.LocalDate

class EntryParsingSpeed extends FunSuite {

  // ~10s, raw read speed is ~8s
  ignore("split whole SwissProt into entry lines") {

    parsers.entries(
      io.Source.fromFile("/home/edu/Downloads/sprot/uniprot_sprot.dat").getLines
    )
    .foreach { e => () }
  }

  // ~11s with everything lazy
  ignore("parse whole SwissProt") {

    parsers.entries(
      io.Source.fromFile("/home/edu/Downloads/sprot/uniprot_sprot.dat").getLines
    )
    .map(FlatFileEntry.from)
    .foreach { e => () }
  }

  // ~26s
  ignore("parse whole SwissProt, access some data") {

    parsers.entries(
      io.Source.fromFile("/home/edu/Downloads/sprot/uniprot_sprot.dat").getLines
    )
    .map(FlatFileEntry.from)
    .foreach { e =>

      val z = e.accessionNumbers.primary
      val u = e.date.creation
      val v = e.identification.status

      e.description.recommendedName.foreach { n => if(n.full.isEmpty) println("empty full name!!") }
    }
  }

  // ~15s
  test("All SwissProt entries have a full name") {

    val noOfEntries = 551987

    val fullNameCount =
      parsers.entries(
        io.Source.fromFile("/home/edu/Downloads/sprot/uniprot_sprot.dat").getLines
      )
      .map(FlatFileEntry.from)
      .foldLeft(0){ (acc, e) =>

        acc + e.description.recommendedName.fold(0){_ => 1}
      }

    assert { fullNameCount == noOfEntries }
  }
}
