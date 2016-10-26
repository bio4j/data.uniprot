package bio4j.data.uniprot.test

import org.scalatest.FunSuite
import collection.JavaConverters._
import java.nio.file._

class FileReadSpeed extends FunSuite {

  //
  test("read whole SwissProt") {

    io.Source.fromFile("/home/edu/Downloads/sprot/uniprot_sprot.dat").getLines
      .foreach { e => () }
  }

  // just for comparison
  ignore("read whole SwissProt Java Stream") {

    Files.lines(Paths.get("/home/edu/Downloads/sprot/uniprot_sprot.dat"))
      .iterator()
      .asScala
      .foreach { e => () }
  }
}
