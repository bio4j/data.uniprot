package bio4j.data.uniprot.test

import org.scalatest.FunSuite
import bio4j.test.ReleaseOnlyTest
import collection.JavaConverters._
import java.nio.file._

class FileReadSpeed extends FunSuite {

  // ~8s
  test("read whole SwissProt", ReleaseOnlyTest) {

    testData.swissProtLines.foreach { e => () }
  }
}
