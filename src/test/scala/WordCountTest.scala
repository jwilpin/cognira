import WordCount.Result
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WordCountTest extends AnyFlatSpec with FileUtil with Matchers {

  behavior of "WordCount"

  it should "count words for sample scenario" in {
    val fileContent = List("Alpha zeta gamma alpha delta zeta alpha")
    val file = createTmpFile("WordCountTest", fileContent)

    val result = WordCount().count(file)

    result should contain theSameElementsInOrderAs List(
      Result("alpha", 3),
      Result("zeta", 2),
      Result("gamma", 1),
      Result("delta", 1)
    )
  }

  it should "count words for sample scenario with multiple lines" in {
    val fileContent = List(
      "Alpha",
      "zeta",
      "gamma",
      "alpha",
      "delta",
      "zeta",
      "alpha"
    )
    val file = createTmpFile("WordCountTest", fileContent)

    val result = WordCount().count(file)

    result should contain theSameElementsInOrderAs List(
      Result("alpha", 3),
      Result("zeta", 2),
      Result("gamma", 1),
      Result("delta", 1)
    )
  }
}
