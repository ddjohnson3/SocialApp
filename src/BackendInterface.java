/**
 * This is the individual backend interface for the backend class of the social app.
 * @param <T> generic type
 */
public interface BackendInterface {

    /**
     * Constructor for the class that takes an instance of the GraphADT as a constructor parameter.
     */
    //public IndividualBackendInterface(GraphADT data)

    /**
     * This method is to read data from a file.
     *
     * @param filePath that stores the data's file path
     */
    public void readData(String filePath);

    /**
     * This method gets a string with statistics about the dataset that includes the number of
     * nodes (participants), the number of edges (friendships), and the average number of friends
     * of all the participants.
     *
     * @return the string that contains the stats for the dataset
     */
    public String getStats();

    /**
     * This method gets the closest connection (shortest path of friends) between two participants.
     *
     * @param person1 first participant
     * @param person2 second participant
     * @return the instance of ClosestConnectionInterface
     */
    public ClosestConnectionInterface<String> getDistanceBetween(String person1, String person2);
}
