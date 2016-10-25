package bio4j.data.uniprot.lines

// TODO get data through slices, see http://web.expasy.org/docs/userman.html#FT_line
case class FT(val lines: Seq[String]) extends AnyVal
