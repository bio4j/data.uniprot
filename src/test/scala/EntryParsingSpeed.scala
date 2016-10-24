package bio4j.data.uniprot.test

import org.scalatest.FunSuite

import bio4j.data.uniprot._
import java.time.LocalDate

class EntryParsingSpeed extends FunSuite {

  ignore("split whole SwissProt into entry lines") {

    parsers.entries(
      io.Source.fromFile("/home/edu/Downloads/sprot/uniprot_sprot.dat").getLines
    )
    .foreach { e => () }
  }

  ignore("parse whole SwissProt") {

    parsers.entries(
      io.Source.fromFile("/home/edu/Downloads/sprot/uniprot_sprot.dat").getLines
    )
    .map(FlatFileEntry.from)
    .foreach { e => () }
  }
}
