package bio4j.data.uniprot.test

import org.scalatest.FunSuite

import collection.JavaConverters._
import bio4j.data.uniprot._
import java.time.LocalDate
import java.nio.file._

class FileReadSpeed extends FunSuite {

  ignore("read whole SwissProt") {

    io.Source.fromFile("/home/edu/Downloads/sprot/uniprot_sprot.dat").getLines
      .foreach { e => () }
  }

  ignore("read whole SwissProt Java Stream") {

    Files.lines(Paths.get("/home/edu/Downloads/sprot/uniprot_sprot.dat"))
      .iterator()
      .asScala
      .foreach { e => () }
  }
}
