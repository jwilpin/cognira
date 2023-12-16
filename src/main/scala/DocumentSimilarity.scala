import akka.actor.ActorSystem

import java.io.File

trait DocumentSimilarity {

  /**
    * Determine the unique words in a pair of text documents and compute a similarity metric
    * for them, as defined by the following function:
    *  Jaccard similarity =
    *    (count of unique words in common) /
    *    (total number of unique words in the two documents)
    *
    * @param textDocument1
    * @param textDocument2
    * @return Jaccard similarity
    */
  def similarity(textDocument1: File, textDocument2: File): Double
}

object DocumentSimilarity {

  def apply(wordCount: WordCount): DocumentSimilarity =
    new DefaultDocumentSimilarity(wordCount)
}

class DefaultDocumentSimilarity(wordCount: WordCount)
    extends DocumentSimilarity {
  override def similarity(textDocument1: File, textDocument2: File): Double = {
    val wordCount1 = wordCount.count(textDocument1).map(_.word)
    val wordCount2 = wordCount.count(textDocument2).map(_.word)

    val uniqueCommonWords = wordCount1.count(wordCount2.contains(_)).toDouble
    val uniqueWords = (wordCount1 ++ wordCount2).toSet.size.toDouble
    uniqueCommonWords / uniqueWords
  }
}

object DocumentSimilarityApp extends App {
  implicit val system: ActorSystem = ActorSystem("DocumentSimilarityApp")

  val fileName1 =
    args.headOption.getOrElse("src/main/resources/document_similarity_1.txt")

  val fileName2 = args.lastOption
    .getOrElse("src/main/resources/document_similarity_2.txt")

  val similarity =
    DocumentSimilarity(WordCount())
      .similarity(new File(fileName1), new File(fileName2))

  println(similarity)
}
