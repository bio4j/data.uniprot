package bio4j.data.uniprot.test

import org.scalatest.FunSuite
import bio4j.data.uniprot.seqOps._

class SeqOps extends FunSuite {

  test("splitSegments") {

    val z = Seq(0,1,2,3,4,5,6,7,8,9,10,11,12,13)

    val pred = { x: Int => x % 3 == 0 }

    assert { (z splitSegments pred) == Seq(Seq(1,2), Seq(4,5), Seq(7,8), Seq(10,11), Seq(13) ) }
  }

}
