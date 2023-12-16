import com.sun.org.apache.xerces.internal.dom.ParentNode

trait Graph {

  // addEdge() adds an undirected edge to the graph
  def addEdge(node1: String, node2: String): Graph

  // findNodes() returns list of nodes connected to a
  // given node and within a max number of hops
  def findNodes(node: String, maxHops: Int): List[String]
}

object Graph {
  def apply(): Graph = new GraphImpl()
}

class GraphImpl(nodes: Map[String, Set[String]] = Map.empty) extends Graph {

  override def addEdge(node1: String, node2: String): Graph = {
    val node1Edges = nodes.getOrElse(node1, Set.empty) + node2
    val node2Edges = nodes.getOrElse(node2, Set.empty) + node1

    val updatedNode1 = (node1, node1Edges)
    val updatedNode2 = (node2, node2Edges)

    new GraphImpl(nodes + updatedNode1 + updatedNode2)
  }

  override def findNodes(node: String, maxHops: Int): List[String] = {

    def findNodesRec(
      node: String,
      hop: Int,
      parentNode: Option[String] = None
    ): List[String] =
      if (hop == maxHops)
        List.empty
      else {
        val edges =
          nodes.getOrElse(node, Set.empty).toList.filterNot(parentNode.contains)
        edges ++ edges
          .flatMap(edge => findNodesRec(edge, hop + 1, Option(node)))
      }
    findNodesRec(node, 0)
  }
}
