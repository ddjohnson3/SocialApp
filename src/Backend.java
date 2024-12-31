
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Backend implements BackendInterface {

    private DijkstraGraph<String, Integer> socialGraph;

    public Backend(DijkstraGraph<String, Integer> socialGraph) {
        this.socialGraph = socialGraph;
    }

    /**
     * method reads the data file by parsing a dot file
     */
    public void readData(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("graph") || line.equals("}")) {
                    continue;
                }

                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1).trim();
                }
                if (!line.contains("--")) {
                    continue;
                }

                // Split nodes 
                String[] edges = line.split("--");
                String node1 = edges[0].replaceAll("\"", "").trim();
                String node2 = edges[1].replaceAll("\"", "").trim();

                // Insert nodes and edges into the graph
                socialGraph.insertNode(node1);
                socialGraph.insertNode(node2);

                // Default weight = 1
                socialGraph.insertEdge(node1, node2, 1);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found - " + filePath);
        }        
    }

    /**
     * method gets the statistics of the graph returns a string
     */
    public String getStats() {
        int numNodes = socialGraph.getNodeCount();
        int numEdges = socialGraph.getEdgeCount();
        double avgFriends = (double) numEdges / numNodes;
        return "Number of Nodes: " + numNodes + "\nNumber of Edges: " + numEdges + "\nAverage Number of Friends: " + String.format("%.2f", avgFriends);
    }

    /**
     * method gets the shortest path to from one person to another
     */
    public ClosestConnectionInterface<String> getDistanceBetween(String person1, String person2) {
        System.out.println("function call");
        List<String> shortestPath = socialGraph.shortestPathData(person1, person2);
        int intermediateFriends = shortestPath.size() - 2;

        return new ClosestConnection(shortestPath, intermediateFriends);
    }

    /**
     * Nested class
     */
    public class ClosestConnection implements ClosestConnectionInterface<String> {

        private List<String> path;
        private int friends;

        public ClosestConnection(List<String> closestPath, int friends) {
            this.path = closestPath;
            this.friends = friends;
        }

        public List<String> getPathBetweenFriends() {

            return path;
        }

        public int numberOfIntermediaryFriends() {
            return friends;
        }
    }
}
