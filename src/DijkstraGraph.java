
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {

        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost) {
                return +1;
            }
            if (cost < other.cost) {
                return -1;
            }
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     *
     * @param map the map that the graph uses to map a data object to the node
     * object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found or
     * when either start or end data do not correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        if (start == null || end == null) {
            throw new NoSuchElementException();
        }

        // Initialize
        PriorityQueue<SearchNode> priorityQueue = new PriorityQueue<>();
        MapADT<NodeType, Double> costMap = new PlaceholderMap<>();
        Node startNode = nodes.get(start);

        if (startNode == null || nodes.get(end) == null) {
            throw new NoSuchElementException("start or end node not found in graph");
        }

        priorityQueue.add(new SearchNode(startNode, 0, null));
        costMap.put(start, 0.0);

        while (!priorityQueue.isEmpty()) {
            // remove highest priority and store it
            SearchNode current = priorityQueue.poll();
            Node currentNode = current.node;

            // found 
            if (currentNode.data.equals(end)) {
                return current;
            }

            // edges leaving
            for (Edge edge : currentNode.edgesLeaving) {
                Node successorNode = edge.successor;
                double newCost = current.cost + edge.data.doubleValue();

                if (!costMap.containsKey(successorNode.data)
                        || newCost < costMap.get(successorNode.data)) {
                    costMap.put(successorNode.data, newCost);
                    priorityQueue.add(new SearchNode(successorNode, newCost, current));
                }
            }

            // edges entering
            for (Edge edge : currentNode.edgesEntering) {
                Node predecessorNode = edge.predecessor;
                double newCost = current.cost + edge.data.doubleValue();

                if (!costMap.containsKey(predecessorNode.data)
                        || newCost < costMap.get(predecessorNode.data)) {
                    costMap.put(predecessorNode.data, newCost);
                    priorityQueue.add(new SearchNode(predecessorNode, newCost, current));
                }
            }
        }

        throw new NoSuchElementException("No path found");
    }
    /**
     * Returns the list of data values from nodes along the shortest path from
     * the node with the provided start value through the node with the provided
     * end value. This list of data values starts with the start value, ends
     * with the end value, and contains intermediary values in the order they
     * are encountered while traversing this shorteset path. This method uses
     * Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        SearchNode endNode = computeShortestPath(start, end);

        List<NodeType> path = new LinkedList<>();
        while (endNode != null) {
            path.add(0, endNode.node.data);
            endNode = endNode.predecessor;
        }

        return path;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest path
     * freom the node containing the start data to the node containing the end
     * data. This method uses Dijkstra's shortest path algorithm to find this
     * solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        SearchNode endNode = computeShortestPath(start, end);
        return endNode.cost;
    }

    /*
    @Test
    public void testAdjacentNodes() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

    	graph.insertNode("A");
    	graph.insertNode("B");
    	graph.insertNode("D");
    	graph.insertNode("D");
    	graph.insertNode("F");
    	graph.insertNode("G");
    	graph.insertNode("H");
    	graph.insertNode("I");
    	graph.insertNode("L");
    	graph.insertNode("M");
    	graph.insertNode("E");

    	graph.insertEdge("D", "G", 2);
    	graph.insertEdge("D","A",7);
    	graph.insertEdge("G", "L", 7);
    	graph.insertEdge("I", "L", 5);
    	graph.insertEdge("F", "G", 9);
    	graph.insertEdge("I", "D", 1);
    	graph.insertEdge("H", "I", 2);
    	graph.insertEdge("I", "H", 2);
    	graph.insertEdge("A", "H", 8);
    	graph.insertEdge("H", "B", 6);
    	graph.insertEdge("G", "L", 7);
    	graph.insertEdge("A", "B", 1);
    	graph.insertEdge("A", "M", 5);
    	graph.insertEdge("B", "M", 3);
    	graph.insertEdge("M", "E", 3);
    	graph.insertEdge("M", "F", 4);

    	assertEquals("Shortest Path A to B", List.of("A", "B"), graph.shortestPathData("A", "B"));

    }
    @Test
    public void testNonAdjacentNodes() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

    	graph.insertNode("A");
    	graph.insertNode("B");
    	graph.insertNode("D");
    	graph.insertNode("D");
    	graph.insertNode("F");
    	graph.insertNode("G");
    	graph.insertNode("H");
    	graph.insertNode("I");
    	graph.insertNode("L");
    	graph.insertNode("M");
    	graph.insertNode("E");

    	graph.insertEdge("D", "G", 2);
    	graph.insertEdge("D","A",7);
    	graph.insertEdge("G", "L", 7);
    	graph.insertEdge("I", "L", 5);
    	graph.insertEdge("F", "G", 9);
    	graph.insertEdge("I", "D", 1);
    	graph.insertEdge("H", "I", 2);
    	graph.insertEdge("I", "H", 2);
    	graph.insertEdge("A", "H", 8);
    	graph.insertEdge("H", "B", 6);
    	graph.insertEdge("G", "L", 7);
    	graph.insertEdge("A", "B", 1);
    	graph.insertEdge("A", "M", 5);
    	graph.insertEdge("B", "M", 3);
    	graph.insertEdge("M", "E", 3);
    	graph.insertEdge("M", "F", 4);

    	assertEquals("Shortest path D to L", List.of("D", "G", "L"), graph.shortestPathData("D", "L"));
    }

    @Test
    public void testNonExsistingPath() {
    	DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

    	graph.insertNode("A");
    	graph.insertNode("B");
    	graph.insertNode("D");
    	graph.insertNode("D");
    	graph.insertNode("F");
    	graph.insertNode("G");
    	graph.insertNode("H");
    	graph.insertNode("I");
    	graph.insertNode("L");
    	graph.insertNode("M");
    	graph.insertNode("E");

    	graph.insertEdge("D", "G", 2);
    	graph.insertEdge("D","A",7);
    	graph.insertEdge("G", "L", 7);
    	graph.insertEdge("I", "L", 5);
    	graph.insertEdge("F", "G", 9);
    	graph.insertEdge("I", "D", 1);
    	graph.insertEdge("H", "I", 2);
    	graph.insertEdge("I", "H", 2);
    	graph.insertEdge("A", "H", 8);
    	graph.insertEdge("H", "B", 6);
    	graph.insertEdge("G", "L", 7);
    	graph.insertEdge("A", "B", 1);
    	graph.insertEdge("A", "M", 5);
    	graph.insertEdge("B", "M", 3);
    	graph.insertEdge("M", "E", 3);
    	graph.insertEdge("M", "F", 4);
    	
    	assertThrows(NoSuchElementException.class, () -> {
   		graph.shortestPathData("L", "D");
   		});	
    	
    	assertThrows(NoSuchElementException.class, () -> {
        graph.shortestPathCost("L", "D");
        });
    	
    }
     */
}
