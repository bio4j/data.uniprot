package bio4j.data.uniprot.test

import org.scalatest.FunSuite
import bio4j.data.uniprot._
import java.time.LocalDate

class Lines extends FunSuite {

  test("ID") {

    val line1 = lines.ID("CYC_BOVIN               Reviewed;         104 AA.")
    val line2 = lines.ID("GIA2_GIALA              Reviewed;         296 AA.")
    val line3 = lines.ID("Q5JU06_HUMAN            Unreviewed;       268 AA.")

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

    val lines1 = lines.AC(
      Seq(
        "Q16653; O00713; O00714; O00715; Q13054; Q13055; Q14855; Q92891;",
        "Q92892; Q92893; Q92894; Q92895; Q93053; Q96KU9; Q96KV0; Q96KV1;"
      )
    )

    val lines2 = lines.AC(
      Seq("Q99605;")
    )

    assert {
      lines1.accesions === Seq("Q16653", "O00713", "O00714", "O00715", "Q13054", "Q13055", "Q14855", "Q92891", "Q92892", "Q92893", "Q92894", "Q92895", "Q93053", "Q96KU9", "Q96KV0", "Q96KV1")
    }
  }

  test("DT") {

    val lines1 = lines.DT(
      Seq(
        "01-OCT-1996, integrated into UniProtKB/Swiss-Prot.",
        "01-OCT-1996, sequence version 1.",
        "07-FEB-2006, entry version 49."
      )
    )

    val lines2 = lines.DT(
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

    val lines1 = lines.DE(
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

  test("GN") {

    val gn = lines.GN(
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

    // TODO
    // OG   Hydrogenosome.
    // OG   Mitochondrion.
    // OG   Nucleomorph.
    // OG   Plasmid name.
    // OG   Plastid.
    // OG   Plastid; Apicoplast.
    // OG   Plastid; Chloroplast.
    // OG   Plastid; Organellar chromatophore.
    // OG   Plastid; Cyanelle.
    // OG   Plastid; Non-photosynthetic plastid.
    // OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
    // OG   Plasmid IncFII R1-19 (R1 drd-19).

    val og1 = lines.OG(Seq("Hydrogenosome."))
    val og2 = lines.OG(Seq("Mitochondrion."))
    val og4 = lines.OG(
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

    val ox1 = lines.OX("NCBI_TaxID=9606;")

    assert {
      ox1.taxonomyCrossReference === TaxonomyCrossReference("9606")
    }
  }

  test("OH") {

    val oh1 = lines.OH(
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

    val cc1 = lines.CC(
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
  }

  test("DR") {

    val dr1 = lines.DR(
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
}
