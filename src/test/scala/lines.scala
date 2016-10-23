package bio4j.data.uniprot.test

import org.scalatest.FunSuite

import collection.JavaConverters._
import bio4j.data.uniprot._
import java.time.LocalDate

class Lines extends FunSuite {

  test("ID") {

    val line1 = lines.ID("CYC_BOVIN               Reviewed;         104 AA.".toCharArray)
    val line2 = lines.ID("GIA2_GIALA              Reviewed;         296 AA.".toCharArray)
    val line3 = lines.ID("Q5JU06_HUMAN            Unreviewed;       268 AA.".toCharArray)

    assert {
      line1.id      === "CYC_BOVIN".toCharArray &&
      line1.status  === Reviewed    &&
      line1.length  === 104
    }

    assert {
      line2.id      === "GIA2_GIALA".toCharArray  &&
      line2.status  === Reviewed      &&
      line2.length  === 296
    }

    assert {
      line3.id      === "Q5JU06_HUMAN".toCharArray  &&
      line3.status  === Unreviewed      &&
      line3.length  === 268
    }
  }

  test("AC") {

    val lines1 = lines.AC(
      Array(
        "Q16653; O00713; O00714; O00715; Q13054; Q13055; Q14855; Q92891;",
        "Q92892; Q92893; Q92894; Q92895; Q93053; Q96KU9; Q96KV0; Q96KV1;"
      )
    )

    val lines2 = lines.AC(
      Array("Q99605;")
    )

    assert {
      lines1.accesions === Seq("Q16653", "O00713", "O00714", "O00715", "Q13054", "Q13055", "Q14855", "Q92891", "Q92892", "Q92893", "Q92894", "Q92895", "Q93053", "Q96KU9", "Q96KV0", "Q96KV1")
    }
  }

  test("DT") {

    val lines1 = lines.DT(
      Array(
        "01-OCT-1996, integrated into UniProtKB/Swiss-Prot.",
        "01-OCT-1996, sequence version 1.",
        "07-FEB-2006, entry version 49."
      )
    )

    val lines2 = lines.DT(
      Array(
        "01-FEB-1999, integrated into UniProtKB/TrEMBL.",
        "15-OCT-2000, sequence version 2.",
        "15-DEC-2004, entry version 5."
      )
    )

    assert {
      lines1.creation === LocalDate.of(1996, 10, 1)                               &&
      lines1.sequenceLastModified === VersionedDate(LocalDate.of(1996, 10, 1), 1) &&
      lines1.entryLastModified === VersionedDate(LocalDate.of(2006, 2, 7), 49)
    }
  }

  test("DE") {

    val lines1 = lines.DE(
      Array(
        "RecName: Full=Annexin A5;",
        "         Short=Annexin-5;",
        "AltName: Full=Annexin V;",
        "AltName: Full=Placental anticoagulant protein I;",
        "         Short=PAP-I;",
        "AltName: Full=Vascular anticoagulant-alpha;",
        "         Short=VAC-alpha;",
        "         Short=VAC-anti;",
        "AltName: Full=Anchorin CII;",
        "         EC=1.43.12.1;"
      )
    )

    assert {

      lines1.recommendedName === Some(
        RecommendedName(
          full  = "Annexin A5",
          short = Seq("Annexin-5"),
          ec    = Seq()
        )
      )
    }

    assert {

      lines1.alternativeNames === Seq(
        AlternativeName(
          full = Some("Annexin V"),
          short = Seq(),
          ec    = Seq()
        ),
        AlternativeName(
          full = Some("Placental anticoagulant protein I"),
          short = Seq("PAP-I"),
          ec    = Seq()
        ),
        AlternativeName(
          full = Some("Vascular anticoagulant-alpha"),
          short = Seq("VAC-alpha", "VAC-anti"),
          ec    = Seq()
        ),
        AlternativeName(
          full = Some("Anchorin CII"),
          short = Seq(),
          ec    = Seq("1.43.12.1")
        )
      )
    }

  }
}
