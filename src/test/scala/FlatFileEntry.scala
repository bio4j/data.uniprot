package bio4j.data.uniprot.test

import org.scalatest.FunSuite

import bio4j.data.uniprot._
import java.time.LocalDate

class FlatFileEntry extends FunSuite {

  test("can parse sample entry") {

    val e =
      FlatFileEntry from testData.entryLines

    // ID line
    assert { e.identification.entryName == "ZWILC_MOUSE" }
    assert { e.identification.status == Reviewed }
    assert { e.identification.length == 589 }
    // AC line
    assert { e.accessionNumbers.primary == "Q8R060" }
    assert { e.accessionNumbers.secondary == Seq("Q9D2E4", "Q9D761") }
    // DT line
    assert { e.date.creation == LocalDate.of(2008, 1, 15) }
    assert { e.date.sequenceLastModified == VersionedDate(LocalDate.of(2002, 6, 1), 1) }
    assert { e.date.entryLastModified == VersionedDate(LocalDate.of(2016, 9, 7), 95) }
    // DE line
    assert {
      e.description == Description(
        Some(RecommendedName("Protein zwilch homolog", Seq(), Seq())),
        Seq(),
        Seq()
      )
    }
  }

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

  ignore("All SwissProt entries have a full name") {

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
