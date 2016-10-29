
```scala
package bio4j.data.uniprot.test

import org.scalatest.FunSuite
import bio4j.data.uniprot._, flat._
import java.time.LocalDate

class Lines extends FunSuite {

  test("ID") {

    val line1 = ID("CYC_BOVIN               Reviewed;         104 AA.")
    val line2 = ID("GIA2_GIALA              Reviewed;         296 AA.")
    val line3 = ID("Q5JU06_HUMAN            Unreviewed;       268 AA.")

    assert {
      line1.id      === "CYC_BOVIN" &&
      line1.status  === Reviewed    &&
      line1.length  === 104
    }

    assert {
      line2.id      === "GIA2_GIALA"  &&
      line2.status  === Reviewed      &&
      line2.length  === 296
    }

    assert {
      line3.id      === "Q5JU06_HUMAN"  &&
      line3.status  === Unreviewed      &&
      line3.length  === 268
    }
  }

  test("AC") {

    val lines1 = AC(
      Seq(
        "Q16653; O00713; O00714; O00715; Q13054; Q13055; Q14855; Q92891;",
        "Q92892; Q92893; Q92894; Q92895; Q93053; Q96KU9; Q96KV0; Q96KV1;"
      )
    )

    val lines2 = AC(
      Seq("Q99605;")
    )

    assert {
      lines1.accesions === Seq("Q16653", "O00713", "O00714", "O00715", "Q13054", "Q13055", "Q14855", "Q92891", "Q92892", "Q92893", "Q92894", "Q92895", "Q93053", "Q96KU9", "Q96KV0", "Q96KV1")
    }
  }

  test("DT") {

    val lines1 = DT(
      Seq(
        "01-OCT-1996, integrated into UniProtKB/Swiss-Prot.",
        "01-OCT-1996, sequence version 1.",
        "07-FEB-2006, entry version 49."
      )
    )

    val lines2 = DT(
      Seq(
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

    val lines1 = DE(
      Seq(
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

      lines1.description === Description(
        recommendedName = Some(
          RecommendedName(
            full  = "Annexin A5",
            short = Seq("Annexin-5"),
            ec    = Seq()
          )
        ),

        alternativeNames = Vector(
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
        ),

        submittedNames = Vector()
      )
    }
  }

  test("GN") {

    val gn = GN(
      Seq(
        "Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;",
        "and",
        "Name=Jon99Ciii; Synonyms=SER2, SER5, Ser99Db;",
        "OrderedLocusNames=b1237, c1701, z2013, ECs1739;",
        "ORFNames=CG15519;"
      )
    )

    val firstGeneName   = gn.geneNames(0)
    val secondGeneName  = gn.geneNames(1)

    assert {
      firstGeneName === GeneName(
        name              = Some(Name("Jon99Cii", Seq("SER1", "SER5", "Ser99Da"))),
        orderedLocusNames = Seq(),
        ORFNames          = Seq("CG7877")
      )
    }

    assert {
      secondGeneName === GeneName(
        name              = Some( Name("Jon99Ciii", Seq("SER2", "SER5", "Ser99Db")) ),
        orderedLocusNames = Seq("b1237", "c1701", "z2013", "ECs1739"),
        ORFNames          = Seq("CG15519")
      )
    }
  }

  test("OG") {

    val og1 = OG(Seq("Hydrogenosome."))
    val og2 = OG(Seq("Mitochondrion."))
    val og4 = OG(
      Seq(
        "Plasmid R6-5, Plasmid IncFII R100 (NR1), and",
        "Plasmid IncFII R1-19 (R1 drd-19)."
      )
    )

    assert {
      og1.organelles === Seq(Hydrogenosome)   &&
      og2.organelles === Seq(Mitochondrion)   &&
      og4.organelles === Seq(
        Plasmid("R6-5"),
        Plasmid("IncFII R100 (NR1)"),
        Plasmid("IncFII R1-19 (R1 drd-19)")
      )
    }
  }

  test("OX") {

    val ox1 = OX("NCBI_TaxID=9606;")

    assert {
      ox1.taxonomyCrossReference === TaxonomyCrossReference("9606")
    }
  }

  test("OH") {

    val oh1 = OH(
      Vector(
        "NCBI_TaxID=9481; Callithrix.",
        "NCBI_TaxID=9536; Cercopithecus hamlyni (Owl-faced monkey) (Hamlyn's monkey).",
        "NCBI_TaxID=9539; Macaca (macaques).",
        "NCBI_TaxID=9598; Pan troglodytes (Chimpanzee)."
      )
    )

    assert {

      oh1.taxonomyCrossReferences === Seq(
        TaxonomyCrossReference("9481"),
        TaxonomyCrossReference("9536"),
        TaxonomyCrossReference("9539"),
        TaxonomyCrossReference("9598")
      )
    }
  }

  test("CC") {

    val cc1 = CC(
      Vector(
        "-!- ALLERGEN: Causes an allergic reaction in human. Minor allergen of",
        "    bovine dander.",
        "-!- DISRUPTION PHENOTYPE: Mice display impaired B-cell development",
        "    which does not progress pass the progenitor stage.",
        "-!- CATALYTIC ACTIVITY: ATP + L-glutamate + NH(3) = ADP + phosphate +",
        "    L-glutamine.",
        "-!- CAUTION: It is uncertain whether Met-1 or Met-3 is the initiator.",
        "-!- COFACTOR: Pyridoxal phosphate.",
        "-!- COFACTOR: FAD. {ECO:0000255|HAMAP-Rule:MF_01202}."

      )
    )

    assert {
      cc1.comments === Vector(
        Allergen("Causes an allergic reaction in human. Minor allergen of bovine dander."),
        DisruptionPhenotype("Mice display impaired B-cell development which does not progress pass the progenitor stage."),
        CatalyticActivity("ATP + L-glutamate + NH(3) = ADP + phosphate + L-glutamine."),
        Caution("It is uncertain whether Met-1 or Met-3 is the initiator."),
        Cofactor("Pyridoxal phosphate."),
        Cofactor("FAD. {ECO:0000255|HAMAP-Rule:MF_01202}.")
      )
    }

    val isoformsCC = CC(
      Vector(
        "-!- ALTERNATIVE PRODUCTS:",
        "    Event=Alternative splicing, Alternative initiation; Named isoforms=8;",
        "      Comment=Additional isoforms seem to exist;",
        "    Name=1; Synonyms=Non-muscle isozyme;",
        "      IsoId=Q15746-1; Sequence=Displayed;",
        "    Name=2;",
        "      IsoId=Q15746-2; Sequence=VSP_004791;",
        "    Name=3A;",
        "      IsoId=Q15746-3; Sequence=VSP_004792, VSP_004794;",
        "    Name=3B;",
        "      IsoId=Q15746-4; Sequence=VSP_004791, VSP_004792, VSP_004794;",
        "    Name=4;",
        "      IsoId=Q15746-5; Sequence=VSP_004792, VSP_004793;",
        "    Name=6; Synonyms=Telokin;",
        "      IsoId=Q15746-8; Sequence=VSP_018846;",
        "      Note=Produced by alternative initiation at Met-1761 of isoform",
        "      1. Has no catalytic activity;"
      )
    )

    assert {
      isoformsCC.comments === Vector(
        Isoform("1","Q15746-1",true),
        Isoform("2","Q15746-2",false),
        Isoform("3A","Q15746-3",false),
        Isoform("3B","Q15746-4",false),
        Isoform("4","Q15746-5",false),
        Isoform("6","Q15746-8",false)
      )
    }

  }

  test("DR") {

    val dr1 = DR(
      Vector(
        "EMBL; U29082; AAA68403.1; -; Genomic_DNA.",
        "Allergome; 3541; Asc s 1.0101.",
        "ArachnoServer; AS000173; kappa-hexatoxin-Hv1b.",
        "Bgee; ENSMUSG00000032315; -.",
        "BindingDB; P06709; -.",
        "BioCyc; EcoCyc:USHA-MONOMER; -.",
        "BioGrid; 69392; 1."
        // "BioMuta; TF; -.",
        // "BRENDA; 3.5.99.5; 3804.",
        // "CAZy; GH109; Glycoside Hydrolase Family 109.",
        // "CCDS; CCDS18166.1; -. [O89019-1]",
        // "CDD; cd01948; EAL; 1."
      )
    )

    assert {
      dr1.databaseCrossReferences.map{ _.identifier } === Vector(
        "U29082", "3541", "AS000173", "ENSMUSG00000032315", "P06709", "EcoCyc:USHA-MONOMER", "69392"
      )
    }
  }

  test("PE") {

    val pe1 = PE("1: Evidence at protein level")
    val pe2 = PE("2: Evidence at transcript level")
    val pe3 = PE("3: Inferred from homology")
    val pe4 = PE("4: Predicted")
    val pe5 = PE("5: Uncertain")

    assert {
      pe1.proteinExistence === EvidenceAtProteinLevel     &&
      pe2.proteinExistence === EvidenceAtTranscriptLevel  &&
      pe3.proteinExistence === InferredFromHomology       &&
      pe4.proteinExistence === Predicted                  &&
      pe5.proteinExistence === Uncertain
    }
  }

  test("KW") {

    val kw1 = KW(
      Vector(
        "3D-structure; Alternative splicing; Alzheimer disease; Amyloid;",
        "Apoptosis; Cell adhesion; Coated pits; Copper;"
      )
    )

    assert {
      kw1.keywords === Vector(
        Keyword("3D-structure"),
        Keyword("Alternative splicing"),
        Keyword("Alzheimer disease"),
        Keyword("Amyloid"),
        Keyword("Apoptosis"),
        Keyword("Cell adhesion"),
        Keyword("Coated pits"),
        Keyword("Copper")
      )
    }
  }

  test("FT") {
    val ft1 = FT(
      Seq(
        "NON_TER       1      1",
        "SIGNAL       <1     10       {ECO:0000250}.",
        "CHAIN        19     87       A-agglutinin.",
        "PROPEP       22     43       Removed by a dipeptidylpeptidase.",
        "MOD_RES      41     41       Arginine amide. {ECO:0000250}.",
        "DISULFID    110    115",
        "CARBOHYD    251    251       N-linked (GlcNAc...).",
        "                             /FTId=CAR_000070.",
        "CONFLICT    327    327       E -> R (in Ref. 2).",
        "CONFLICT     77     77       Missing (in Ref. 1)."
      )
    )

    assert {
      ft1.features === Vector(
        Feature(NON_TER,"1","1",""),
        Feature(SIGNAL,"<1","10","{ECO:0000250}."),
        Feature(CHAIN,"19","87","A-agglutinin."),
        Feature(PROPEP,"22","43","Removed by a dipeptidylpeptidase."),
        Feature(MOD_RES,"41","41","Arginine amide. {ECO:0000250}."),
        Feature(DISULFID,"110","115",""),
        Feature(CARBOHYD,"251","251","N-linked (GlcNAc...). /FTId=CAR_000070."),
        Feature(CONFLICT,"327","327","E -> R (in Ref. 2)."),
        Feature(CONFLICT,"77","77","Missing (in Ref. 1).")
      )
    }
  }

  test("SQ") {

    val sq1 = SQ(
      "SEQUENCE   486 AA;  55639 MW;  D7862E867AD74383 CRC64;"
    )

    assert {
      sq1.sequenceHeader === SequenceHeader(
        length          = 486,
        molecularWeight = 55639,
        crc64           = "D7862E867AD74383"
      )
    }
  }

  test("  ") {

    val sd1 = SequenceData(
      Vector(
        "MTILASICKL GNTKSTSSSI GSSYSSAVSF GSNSVSCGEC GGDGPSFPNA SPRTGVKAGV",
        "NVDGLLGAIG KTVNGMLISP NGGGGGMGMG GGSCGCI"
      )
    )

    assert {
      sd1.sequence === Sequence(
        "MTILASICKLGNTKSTSSSIGSSYSSAVSFGSNSVSCGECGGDGPSFPNASPRTGVKAGVNVDGLLGAIGKTVNGMLISPNGGGGGMGMGGGSCGCI"
      )
    }
  }
}

```




[test/scala/LineParsingSpeed.scala]: LineParsingSpeed.scala.md
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