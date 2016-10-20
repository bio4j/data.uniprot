package bio4j.data.uniprot.test

import org.scalatest.FunSuite

import bio4j.data.uniprot._
import java.time.LocalDate

class FlatFileEntryTests extends FunSuite {

  lazy val entry =
"""
ID   ZWILC_MOUSE             Reviewed;         589 AA.
AC   Q8R060; Q9D2E4; Q9D761;
DT   15-JAN-2008, integrated into UniProtKB/Swiss-Prot.
DT   01-JUN-2002, sequence version 1.
DT   07-SEP-2016, entry version 95.
DE   RecName: Full=Protein zwilch homolog;
GN   Name=Zwilch;
OS   Mus musculus (Mouse).
OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi;
OC   Mammalia; Eutheria; Euarchontoglires; Glires; Rodentia; Sciurognathi;
OC   Muroidea; Muridae; Murinae; Mus; Mus.
OX   NCBI_TaxID=10090;
RN   [1]
RP   NUCLEOTIDE SEQUENCE [LARGE SCALE MRNA].
RC   STRAIN=C57BL/6J; TISSUE=Testis, and Tongue;
RX   PubMed=16141072; DOI=10.1126/science.1112014;
RA   Carninci P., Kasukawa T., Katayama S., Gough J., Frith M.C., Maeda N.,
RA   Oyama R., Ravasi T., Lenhard B., Wells C., Kodzius R., Shimokawa K.,
RA   Bajic V.B., Brenner S.E., Batalov S., Forrest A.R., Zavolan M.,
RA   Davis M.J., Wilming L.G., Aidinis V., Allen J.E.,
RA   Ambesi-Impiombato A., Apweiler R., Aturaliya R.N., Bailey T.L.,
RA   Bansal M., Baxter L., Beisel K.W., Bersano T., Bono H., Chalk A.M.,
RA   Chiu K.P., Choudhary V., Christoffels A., Clutterbuck D.R.,
RA   Crowe M.L., Dalla E., Dalrymple B.P., de Bono B., Della Gatta G.,
RA   di Bernardo D., Down T., Engstrom P., Fagiolini M., Faulkner G.,
RA   Fletcher C.F., Fukushima T., Furuno M., Futaki S., Gariboldi M.,
RA   Georgii-Hemming P., Gingeras T.R., Gojobori T., Green R.E.,
RA   Gustincich S., Harbers M., Hayashi Y., Hensch T.K., Hirokawa N.,
RA   Hill D., Huminiecki L., Iacono M., Ikeo K., Iwama A., Ishikawa T.,
RA   Jakt M., Kanapin A., Katoh M., Kawasawa Y., Kelso J., Kitamura H.,
RA   Kitano H., Kollias G., Krishnan S.P., Kruger A., Kummerfeld S.K.,
RA   Kurochkin I.V., Lareau L.F., Lazarevic D., Lipovich L., Liu J.,
RA   Liuni S., McWilliam S., Madan Babu M., Madera M., Marchionni L.,
RA   Matsuda H., Matsuzawa S., Miki H., Mignone F., Miyake S., Morris K.,
RA   Mottagui-Tabar S., Mulder N., Nakano N., Nakauchi H., Ng P.,
RA   Nilsson R., Nishiguchi S., Nishikawa S., Nori F., Ohara O.,
RA   Okazaki Y., Orlando V., Pang K.C., Pavan W.J., Pavesi G., Pesole G.,
RA   Petrovsky N., Piazza S., Reed J., Reid J.F., Ring B.Z., Ringwald M.,
RA   Rost B., Ruan Y., Salzberg S.L., Sandelin A., Schneider C.,
RA   Schoenbach C., Sekiguchi K., Semple C.A., Seno S., Sessa L., Sheng Y.,
RA   Shibata Y., Shimada H., Shimada K., Silva D., Sinclair B.,
RA   Sperling S., Stupka E., Sugiura K., Sultana R., Takenaka Y., Taki K.,
RA   Tammoja K., Tan S.L., Tang S., Taylor M.S., Tegner J., Teichmann S.A.,
RA   Ueda H.R., van Nimwegen E., Verardo R., Wei C.L., Yagi K.,
RA   Yamanishi H., Zabarovsky E., Zhu S., Zimmer A., Hide W., Bult C.,
RA   Grimmond S.M., Teasdale R.D., Liu E.T., Brusic V., Quackenbush J.,
RA   Wahlestedt C., Mattick J.S., Hume D.A., Kai C., Sasaki D., Tomaru Y.,
RA   Fukuda S., Kanamori-Katayama M., Suzuki M., Aoki J., Arakawa T.,
RA   Iida J., Imamura K., Itoh M., Kato T., Kawaji H., Kawagashira N.,
RA   Kawashima T., Kojima M., Kondo S., Konno H., Nakano K., Ninomiya N.,
RA   Nishio T., Okada M., Plessy C., Shibata K., Shiraki T., Suzuki S.,
RA   Tagami M., Waki K., Watahiki A., Okamura-Oho Y., Suzuki H., Kawai J.,
RA   Hayashizaki Y.;
RT   "The transcriptional landscape of the mammalian genome.";
RL   Science 309:1559-1563(2005).
RN   [2]
RP   NUCLEOTIDE SEQUENCE [LARGE SCALE MRNA].
RC   STRAIN=FVB/N; TISSUE=Mammary tumor;
RX   PubMed=15489334; DOI=10.1101/gr.2596504;
RG   The MGC Project Team;
RT   "The status, quality, and expansion of the NIH full-length cDNA
RT   project: the Mammalian Gene Collection (MGC).";
RL   Genome Res. 14:2121-2127(2004).
RN   [3]
RP   IDENTIFICATION BY MASS SPECTROMETRY [LARGE SCALE ANALYSIS].
RC   TISSUE=Spleen, and Testis;
RX   PubMed=21183079; DOI=10.1016/j.cell.2010.12.001;
RA   Huttlin E.L., Jedrychowski M.P., Elias J.E., Goswami T., Rad R.,
RA   Beausoleil S.A., Villen J., Haas W., Sowa M.E., Gygi S.P.;
RT   "A tissue-specific atlas of mouse protein phosphorylation and
RT   expression.";
RL   Cell 143:1174-1189(2010).
CC   -!- FUNCTION: Essential component of the mitotic checkpoint, which
CC       prevents cells from prematurely exiting mitosis. Required for the
CC       assembly of the dynein-dynactin and MAD1-MAD2 complexes onto
CC       kinetochores. Its function related to the spindle assembly
CC       machinery is proposed to depend on its association in the mitotic
CC       RZZ complex (By similarity). {ECO:0000250|UniProtKB:Q9H900}.
CC   -!- SUBUNIT: Component of the RZZ complex composed of KNTC1/ROD, ZW10
CC       and ZWILCH; in the complex interacts directly with KNTC1/ROD (By
CC       similarity). {ECO:0000250|UniProtKB:Q9H900}.
CC   -!- SUBCELLULAR LOCATION: Chromosome, centromere, kinetochore
CC       {ECO:0000250|UniProtKB:Q9H900}.
CC   -!- SIMILARITY: Belongs to the ZWILCH family. {ECO:0000305}.
DR   EMBL; AK009559; BAB26358.2; -; mRNA.
DR   EMBL; AK019825; BAB31869.2; -; mRNA.
DR   EMBL; BC027435; AAH27435.1; -; mRNA.
DR   RefSeq; NP_080783.3; NM_026507.4.
DR   UniGene; Mm.335237; -.
DR   ProteinModelPortal; Q8R060; -.
DR   SMR; Q8R060; 1-309, 337-588.
DR   IntAct; Q8R060; 2.
DR   STRING; 10090.ENSMUSP00000112790; -.
DR   iPTMnet; Q8R060; -.
DR   PhosphoSite; Q8R060; -.
DR   EPD; Q8R060; -.
DR   MaxQB; Q8R060; -.
DR   PaxDb; Q8R060; -.
DR   PRIDE; Q8R060; -.
DR   GeneID; 68014; -.
DR   KEGG; mmu:68014; -.
DR   UCSC; uc012gva.1; mouse.
DR   CTD; 55055; -.
DR   MGI; MGI:1915264; Zwilch.
DR   eggNOG; KOG4803; Eukaryota.
DR   eggNOG; ENOG410Y8MB; LUCA.
DR   HOGENOM; HOG000155813; -.
DR   HOVERGEN; HBG108779; -.
DR   InParanoid; Q8R060; -.
DR   KO; K11579; -.
DR   PhylomeDB; Q8R060; -.
DR   ChiTaRS; Zwilch; mouse.
DR   PRO; PR:Q8R060; -.
DR   Proteomes; UP000000589; Unplaced.
DR   Bgee; ENSMUSG00000032400; -.
DR   GO; GO:0000777; C:condensed chromosome kinetochore; IEA:UniProtKB-SubCell.
DR   GO; GO:0000776; C:kinetochore; ISO:MGI.
DR   GO; GO:1990423; C:RZZ complex; ISO:MGI.
DR   GO; GO:0051301; P:cell division; IEA:UniProtKB-KW.
DR   GO; GO:0007093; P:mitotic cell cycle checkpoint; ISS:UniProtKB.
DR   GO; GO:0007067; P:mitotic nuclear division; IEA:UniProtKB-KW.
DR   InterPro; IPR018630; RZZ-complex_zwilch.
DR   Pfam; PF09817; DUF2352; 1.
PE   1: Evidence at protein level;
KW   Cell cycle; Cell division; Centromere; Chromosome; Complete proteome;
KW   Kinetochore; Mitosis; Phosphoprotein; Reference proteome.
FT   CHAIN         1    589       Protein zwilch homolog.
FT                                /FTId=PRO_0000314801.
FT   MOD_RES      88     88       Phosphoserine.
FT                                {ECO:0000250|UniProtKB:Q9H900}.
FT   CONFLICT     19     19       E -> G (in Ref. 1; BAB26358).
FT                                {ECO:0000305}.
FT   CONFLICT    215    215       D -> V (in Ref. 1; BAB26358).
FT                                {ECO:0000305}.
FT   CONFLICT    285    285       E -> G (in Ref. 1; BAB26358).
FT                                {ECO:0000305}.
SQ   SEQUENCE   589 AA;  66839 MW;  D4CF69E0E818A988 CRC64;
     MWSRMNRAAE EFYARLRQEF NEEKKGASKD PFIYEADVQV QLISKGQPSL LKTILNENDS
     VFLVEKVVLE KEETSQVEEL QSEETAISDL SAGENIRPLA LPVGRARQLI GLYTMAHNPN
     MTHLKIKQPV TALPPLWVRC DGSDPEGTCW LGAELITTND IIAGVILYVL TCKADKNYSE
     DLENLKTSHK KRHHVSAVTA RGFAQYELFK SDDLDDTVAP SQTTVTLDLS WSPVDEMLQT
     PPLSSTAALN IRVQSGESRG CLSHLHRELK FLLVLADGIR TGVTEWLEPL ETKSALEFVQ
     EFLNDLNKLD EFDDSTKKDK QKEAVNHDAA AVVRSMLLTV RGDLDFAEQL WCRMSSSVVS
     YQDLVKCFTL ILQSLQRGDI QPWLHSGSNS LLSKLIHQSY HGAMDSVPLS GTTPLQMLLE
     IGLDKLKKDY ISFFVSQELA SLNHLEYFIS PSVSTQEQVC RVQKLHHILE ILVICMLFIK
     PQHELLFSLT QSCIKYYKQN PLDEQHIFQL PVRPAAVKNL YQSEKPQKWR VELSNSQKRV
     KTVWQLSDSS PVDHSSFHRP EFPELTLNGS LEERTAFVNM LTCSQVHFK
"""

  test("can parse sample entry") {

    val entryLines =
      entry.split('\n').dropWhile(_.isEmpty)

    val e =
      parsers.flatFileEntryFrom(entryLines)

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
  }
}
