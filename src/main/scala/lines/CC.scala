package bio4j.data.uniprot.lines

import bio4j.data.uniprot.Comment

case class CC(val lines: Seq[String]) extends AnyVal {

  def comments: Seq[Comment] =
    commentBlocks map commentFromBlock

  private def commentFromBlock(lines: Seq[String]): Comment = {

    val (topic, _headContent) = lines.head.span(_!=':')

    val contents: Seq[String] = _headContent.stripPrefix(":") +: lines.tail

    topic match {

      // TODO match on all the possible options
      case _ => ???
    }
  }

  private def commentBlocks: Seq[Seq[String]] =
    commentBlocks_rec(lines, Seq())

  @annotation.tailrec
  private def commentBlocks_rec(commentLines: Seq[String], acc: Seq[Seq[String]]): Seq[Seq[String]] = {

    val (thisCommentDefLines, rest) = commentLines span { line =>  line startsWith "-!- " }

    val (thisCommentOtherLines, restOfComments) = rest span { _ startsWith "    " }

    val nextAcc: Seq[Seq[String]] =
      acc :+ (thisCommentDefLines.head.stripPrefix("-!-").trim +: thisCommentOtherLines.map(_.trim))

    if(restOfComments.isEmpty)
      nextAcc
    else
      commentBlocks_rec(
        commentLines = restOfComments,
        acc = nextAcc
      )
  }
}
