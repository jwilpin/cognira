import akka.actor.ActorSystem
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DocumentSimilarityTest
    extends AnyFlatSpec
    with FileUtil
    with Matchers
    with BeforeAndAfterAll {

  val testKit = ActorTestKit()
  implicit val actorSystem: ActorSystem = testKit.system.classicSystem

  override def afterAll(): Unit = testKit.shutdownTestKit()

  behavior of "DocumentSimilarity"

  it should "calculate document similarity for sample scenarios" in {
    val file1 = createTmpFile(
      "DocumentSimilarity1",
      List("Alpha zeta gamma alpha delta zeta alpha.")
    )
    val file2 = createTmpFile(
      "DocumentSimilarity2",
      List("Beta alpha, gamma delta. Alpha beta epsilon theta.")
    )

    val result = DocumentSimilarity(WordCount()).similarity(file1, file2)

    result shouldBe 0.428571 +- 0.000001
  }
}
