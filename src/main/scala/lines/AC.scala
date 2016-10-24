package bio4j.data.uniprot.lines

import bio4j.data.uniprot.seqOps._

/*
  Example:

  ```
  Q16653; O00713; O00714; O00715; Q13054; Q13055; Q14855; Q92891;
  Q92892; Q92893; Q92894; Q92895; Q93053; Q96KU9; Q96KV0; Q96KV1;
  Q99605;
  ```
*/
case class AC(val lines: Seq[String]) extends AnyVal {

  private def joinedLines: String =
    lines.mkString("")

  def accesions: Seq[String] =
    joinedLines
      .splitSegments(_ == ';')
      .map(_.trim)
}
