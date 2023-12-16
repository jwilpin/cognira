import WordCount.Result
import akka.actor.ActorSystem
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import akka.actor.typed.scaladsl.adapter._
import org.scalatest.BeforeAndAfterAll
class WordCountTest
    extends AnyFlatSpec
    with FileUtil
    with Matchers
    with BeforeAndAfterAll {

  val testKit = ActorTestKit()
  implicit val actorSystem: ActorSystem = testKit.system.classicSystem

  override def afterAll(): Unit = testKit.shutdownTestKit()

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
