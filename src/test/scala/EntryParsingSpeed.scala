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

  // ~25s, don't know why
  test("parse whole SwissProt") {

    parsers.entries(
      io.Source.fromFile("/home/edu/Downloads/sprot/uniprot_sprot.dat").getLines
    )
    .map(FlatFileEntry.from)
    .foreach { e => () }
  }
}
