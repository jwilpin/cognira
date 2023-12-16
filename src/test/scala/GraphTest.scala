import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GraphTest extends AnyWordSpec with Matchers {

  "Graph" when {

    "Given a graph with the following edges:" should {

      """
        |"A", "B"
        |"A", "C"
        |"B", "D"
        |"C", "E"
        |"E", "F"
        |"E", "G"
        |""".stripMargin

      val graph = Graph()
        .addEdge("A", "B")
        .addEdge("A", "C")
        .addEdge("B", "D")
        .addEdge("C", "E")
        .addEdge("E", "F")
        .addEdge("E", "G")

      "find nodes for A with 1 as maxHops" in {
        graph.findNodes("A", 1) should contain theSameElementsAs List("B", "C")
      }

      "find nodes for A with 2 as maxHops" in {
        graph.findNodes("A", 2) should contain theSameElementsAs List(
          "B",
          "D",
          "C",
          "E"
        )
      }

      "find nodes for C with 2 as maxHops" in {
        graph.findNodes("C", 2) should contain theSameElementsAs List(
          "A",
          "B",
          "E",
          "F",
          "G"
        )
      }
    }
  }
}
