import WordCount.Result
import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.IOResult

import java.io.File
import akka.stream.scaladsl.{FileIO, Sink, Source}

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

trait WordCount {

  /**
    * Count the occurrences of unique words in a text document
    * @param textDocument containing one or more paragraphs
    * @return list of unique words and count of occurrences, sorted by greatest count
    */
  def count(textDocument: File): List[Result]

  /**
    * Count the occurrences of unique words in a text document
    *
    * @param textDocument containing one or more paragraphs
    * @return source of unique words and count of occurrences, sorted by greatest count
    */
  def countStream(textDocument: File): Source[Result, NotUsed]

}

object WordCount {

  final case class Result(word: String, count: Long) {
    override def toString: String = s"$word $count"
  }
  def apply()(implicit actorSystem: ActorSystem): WordCount =
    new DefaultWordCount()

}

class DefaultWordCount(implicit actorSystem: ActorSystem) extends WordCount {

  implicit val ec: ExecutionContext = actorSystem.dispatcher

  @deprecated
  override def count(textDocument: File): List[Result] =
    Await
      .result(
        countStream(textDocument).runWith(Sink.collection).map(_.toList),
        Duration.Inf
      )
      .sortBy(r => (r.count, r.word))(
        Ordering.Tuple2(Ordering.Long.reverse, Ordering.String.reverse)
      )

  override def countStream(textDocument: File): Source[Result, NotUsed] =
    FileIO
      .fromPath(textDocument.toPath)
      .map(_.utf8String.toLowerCase)
      .map(_.replaceAll("\n", " "))
      .map(_.replace(',', ' '))
      .map(_.replace('.', ' '))
      .mapConcat(line => line.split(' ').toList)
      .filterNot(_.isEmpty)
      .groupBy(Int.MaxValue, identity)
      .fold(Option.empty[Result]) {
        case (None, word)                   => Some(Result(word, 1))
        case (Some(Result(word, count)), _) => Some(Result(word, count + 1))
      }
      .mapConcat(_.toList)
      .mergeSubstreams
      .mapMaterializedValue(_ => NotUsed)
}

object WordCountApp extends App {
  val fileName =
    args.headOption.getOrElse("src/main/resources/word_count.txt")
  implicit val system: ActorSystem = ActorSystem("WordCountApp")
  WordCount().count(new File(fileName)).foreach(println)
}
