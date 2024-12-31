import java.util.List;

/**
 * An interface for a class that stores the results of a shortest path search, the closest
 * connection between two participants.
 * @param <T>
 */
public interface ClosestConnectionInterface<T> {

    /**
     * This method gets the path (as a list of intermediary friends) between the two participants,
     * @return list of friends between 2 participants.
     */
    public List<T> getPathBetweenFriends();

    /**
     * This method gets the number of intermediary friends that connect the two participants.
     * @return number of friends that connect 2 participants
     */
    public int numberOfIntermediaryFriends();
}