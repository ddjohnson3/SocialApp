import java.util.Scanner;

/**
 * This is the interface for the frontend class for the social app.
 */
public interface FrontendInterface {

    /**
     * Constructor for the design of frontEnd version of the app.
     *
     * @param backEnd Takes the interface of the backend version of the app.
     * @param input   Scanner object for user input.
     */
    //public FrontendInterface(BackendInterface backend, Scanner scanner)
    
    /**
     * Starts the main command loop, prompting the user to select a command and then processing it.
     * The loop continues until the user decides to exit.
     */
    public void runMainLoop();

    /**
     * Displays the main menu and handles the user input to navigate to the appropriate command
     * method. The menu should provide options for all the functionalities available to the user.
     */
    public void displayMainMenu();

    /**
     * Handles the command for loading a data file. The user will be prompted to enter the path
     * to the data file, and this method will initiate the loading process.
     * If the file is not found or is invalid, the user should be notified and returned to the main menu.
     * @param filePath read the user's input for the data
     */
    public void loadDataFileCommand(String filePath);

    /**
     * Shows statistics about the dataset such as the number of participants (nodes),
     * number of edges (friendships), and the average number of friends per participant.
     * If the data is not yet loaded, the user should be informed accordingly.
     *
     */
    public void showStatisticsCommand();

    /**
     * Prompts the user for two participants and displays the closest
     * connection between the two, including all intermediary friends.
     * If either participant is not found, or if there is no connection,
     * the user should be informed with an appropriate message.
     */
    public void findClosestConnectionCommand();

    /**
     * Terminates the application. This should cleanly exit the command loop
     * and perform any necessary cleanup operations before closing the application.
     */
    public void exitCommand();

}
