import WordCount.Result

import java.io.File
import scala.io.Source

trait WordCount {

  /**
    * Count the occurrences of unique words in a text document
    * @param textDocument containing one or more paragraphs
    * @return list of unique words and count of occurrences, sorted by greatest count
    */
  def count(textDocument: File): List[Result]

}

object WordCount {

  final case class Result(word: String, count: Long) {
    override def toString: String = s"$word $count"
  }
  def apply(): WordCount = new DefaultWordCount()

}

class DefaultWordCount extends WordCount {
  override def count(textDocument: File): List[Result] =
    Source
      .fromFile(textDocument)
      .getLines
      .toList
      .flatMap(line => line.split(' ').toList)
      .groupBy(_.toLowerCase)
      .map { case (k, v) => Result(k, v.size) }
      .toList
      .sortBy(r => (r.count, r.word))(
        Ordering.Tuple2(Ordering.Long.reverse, Ordering.String.reverse)
      )
}

object WordCountApp extends App {
  val fileName =
    args.headOption.getOrElse("src/main/resources/word_count.txt")

  WordCount().count(new File(fileName)).foreach(println)
}
