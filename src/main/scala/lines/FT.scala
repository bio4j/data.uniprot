package bio4j.data.uniprot.lines

import bio4j.data.uniprot._

// TODO get data through slices, see http://web.expasy.org/docs/userman.html#FT_line
/*
  The format is

  ```
  CARBOHYD    251    251       N-linked (GlcNAc...).
  ```

  We have

  1. feature type: [0,8[
  2. from: [10,16[
  3. to: [18,24[
  4. description: [31,-[ and possibly the next lines/s
*/
case class FT(val lines: Seq[String]) extends AnyVal {

  def features: Seq[Feature] =
    featureBlocks map featureFrom

  private def featureBlocks: Seq[Seq[String]] =
    lines.foldLeft[Seq[Seq[String]]](Vector()){ (acc: Seq[Seq[String]], line: String) =>
      // extra lines for a feature
      if(line startsWith "    ") {
        acc.updated(acc.length -1, acc.last :+ line.trim)
      }
      else {
        acc :+ Vector(line)
      }
    }

  private def featureFrom(featureBlock: Seq[String]): Feature = {

    val firstLine = featureBlock.head

    Feature(
      key         = FeatureKey.fromString( firstLine.slice(0,8).trim ),
      from        = firstLine.slice(10,16).trim,
      to          = firstLine.slice(18,24).trim,
      description = (firstLine.drop(29).trim +: featureBlock.tail.map(_.trim)).mkString(" ")
    )
  }
}
