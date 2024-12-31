import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;
import java.nio.file.NoSuchFileException;

public class BackendDeveloperTests {
    /**
     * Test case for the readData method in the Backend class.
     */
    @Test
    public void testReadData() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        BackendInterface backend = new Backend(graph);
        String filePath = "socialnetwork.dot";

        assertThrows(NoSuchFileException.class, () -> {
            backend.readData("incorrectFile");
        });

        backend.readData(filePath);
        assertTrue(graph.containsNode>0);
    }
    /**
     * Test case for the getStats method in the Backend class.
     */
    @Test
    public void testGetStats() {

        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        BackendInterface backend = new Backend(graph);
        backend.readData("socialnetwork.dot");

        String stats = backend.getStats();

        assertEquals("Nodes: 21, Edges: 22, Average friends: 2.5", backend.getStats());

    }
    /**
     * Test case for the getDistanceBetween method in the Backend class.
     */
    @Test
    public void testGetDistanceBetween() {

        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        BackendInterface backend = new Backend(graph);
        backend.readData("socialnetwork.dot");



        ClosestConnectionInterface<String> connection = backend.getDistanceBetween("user0", "user37");

        assertEquals(List.of("user8", "user26", "user16", "user10"), connection.getPathBetweenFriends());
        assertEquals(2, connection.numberOfIntermediaryFriends());
    }
    /**
     * Test case for the getPathBetweenFriends method in the ClosestConnection class.
     */

    @Test
    public void testGetPathBetweenFriends() {
        List<String> expectedPath = Arrays.asList("user1", "user2", "user3");
        ClosestConnectionInterface<String> closestConnection = new ClosestConnection<>(expectedPath, 2);

        assertEquals(expectedPath,closestConnection.getPathBetweenFriends());
    }
    /**
     * Test case for the numberOfIntermediaryFriends method in the ClosestConnection class.
     */
    @Test
    public void testNumberOfIntermediaryFriends() {

        ClosestConnectionInterface<String> closestConnection = new ClosestConnection<>(Arrays.asList("user1", "user2", "u\
ser3"), expectedNumberOfIntermediaryFriends);

        int intermediaryFriends = closestConnection.numberOfIntermediaryFriends();
assertEquals(3, intermediaryFriends);
    }

}
