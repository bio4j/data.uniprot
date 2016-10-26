package bio4j.data.uniprot.test

import org.scalatest.FunSuite
import bio4j.test.ReleaseOnlyTest
import collection.JavaConverters._
import java.nio.file._

class FileReadSpeed extends FunSuite {

  // ~8s
  test("read whole SwissProt", ReleaseOnlyTest) {

    io.Source.fromFile("/home/edu/Downloads/sprot/uniprot_sprot.dat").getLines
      .foreach { e => () }
  }

  // just for comparison
  test("read whole SwissProt Java Stream", ReleaseOnlyTest) {

    Files.lines(Paths.get("/home/edu/Downloads/sprot/uniprot_sprot.dat"))
      .iterator()
      .asScala
      .foreach { e => () }
  }
}
