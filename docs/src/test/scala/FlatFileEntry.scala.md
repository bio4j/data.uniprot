
```scala
package bio4j.data.uniprot.test

import org.scalatest.FunSuite

import bio4j.data.uniprot._
import java.time.LocalDate

class FlatFileEntry extends FunSuite {

  test("can parse sample entry") {

    val e =
      flat.Entry from testData.entryLines

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
    // sequence line
    assert {
      e.sequence === Sequence(
        "MWSRMNRAAEEFYARLRQEFNEEKKGASKDPFIYEADVQVQLISKGQPSLLKTILNENDSVFLVEKVVLEKEETSQVEELQSEETAISDLSAGENIRPLALPVGRARQLIGLYTMAHNPNMTHLKIKQPVTALPPLWVRCDGSDPEGTCWLGAELITTNDIIAGVILYVLTCKADKNYSEDLENLKTSHKKRHHVSAVTARGFAQYELFKSDDLDDTVAPSQTTVTLDLSWSPVDEMLQTPPLSSTAALNIRVQSGESRGCLSHLHRELKFLLVLADGIRTGVTEWLEPLETKSALEFVQEFLNDLNKLDEFDDSTKKDKQKEAVNHDAAAVVRSMLLTVRGDLDFAEQLWCRMSSSVVSYQDLVKCFTLILQSLQRGDIQPWLHSGSNSLLSKLIHQSYHGAMDSVPLSGTTPLQMLLEIGLDKLKKDYISFFVSQELASLNHLEYFISPSVSTQEQVCRVQKLHHILEILVICMLFIKPQHELLFSLTQSCIKYYKQNPLDEQHIFQLPVRPAAVKNLYQSEKPQKWRVELSNSQKRVKTVWQLSDSSPVDHSSFHRPEFPELTLNGSLEERTAFVNMLTCSQVHFK"
      )
    }
  }

}

```




[test/scala/lines.scala]: lines.scala.md
[test/scala/testData.scala]: testData.scala.md
[test/scala/FlatFileEntry.scala]: FlatFileEntry.scala.md
[test/scala/EntryParsingSpeed.scala]: EntryParsingSpeed.scala.md
[test/scala/FileReadSpeed.scala]: FileReadSpeed.scala.md
[test/scala/SeqOps.scala]: SeqOps.scala.md
[main/scala/entry.scala]: ../../main/scala/entry.scala.md
[main/scala/flat/SequenceData.scala]: ../../main/scala/flat/SequenceData.scala.md
[main/scala/flat/KW.scala]: ../../main/scala/flat/KW.scala.md
[main/scala/flat/ID.scala]: ../../main/scala/flat/ID.scala.md
[main/scala/flat/RC.scala]: ../../main/scala/flat/RC.scala.md
[main/scala/flat/DT.scala]: ../../main/scala/flat/DT.scala.md
[main/scala/flat/Entry.scala]: ../../main/scala/flat/Entry.scala.md
[main/scala/flat/GN.scala]: ../../main/scala/flat/GN.scala.md
[main/scala/flat/parsers.scala]: ../../main/scala/flat/parsers.scala.md
[main/scala/flat/RG.scala]: ../../main/scala/flat/RG.scala.md
[main/scala/flat/DR.scala]: ../../main/scala/flat/DR.scala.md
[main/scala/flat/OG.scala]: ../../main/scala/flat/OG.scala.md
[main/scala/flat/RL.scala]: ../../main/scala/flat/RL.scala.md
[main/scala/flat/SQ.scala]: ../../main/scala/flat/SQ.scala.md
[main/scala/flat/PE.scala]: ../../main/scala/flat/PE.scala.md
[main/scala/flat/OS.scala]: ../../main/scala/flat/OS.scala.md
[main/scala/flat/CC.scala]: ../../main/scala/flat/CC.scala.md
[main/scala/flat/OX.scala]: ../../main/scala/flat/OX.scala.md
[main/scala/flat/OH.scala]: ../../main/scala/flat/OH.scala.md
[main/scala/flat/RN.scala]: ../../main/scala/flat/RN.scala.md
[main/scala/flat/DE.scala]: ../../main/scala/flat/DE.scala.md
[main/scala/flat/RA.scala]: ../../main/scala/flat/RA.scala.md
[main/scala/flat/RX.scala]: ../../main/scala/flat/RX.scala.md
[main/scala/flat/FT.scala]: ../../main/scala/flat/FT.scala.md
[main/scala/flat/AC.scala]: ../../main/scala/flat/AC.scala.md
[main/scala/flat/RP.scala]: ../../main/scala/flat/RP.scala.md
[main/scala/flat/lineTypes.scala]: ../../main/scala/flat/lineTypes.scala.md
[main/scala/flat/RT.scala]: ../../main/scala/flat/RT.scala.md
[main/scala/seqOps.scala]: ../../main/scala/seqOps.scala.md