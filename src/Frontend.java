
import java.util.List;
import java.util.Scanner;

public class Frontend implements FrontendInterface {

    private Backend backend;
    private Scanner scnr;

    public Frontend(Backend backend, Scanner scanner) {

        this.backend = backend;
        this.scnr = scanner;

    }

    /**
     * Runs main command loop
     */
    @Override
    public void runMainLoop() {

        boolean loop = true;
        int command;

        // Look through commands until user exits
        while (loop) {

            // Display menu to user
            displayMainMenu();

            // Read user input
            if (scnr.hasNext()) {

                command = scnr.nextInt();

                // Command for loading data file
                if (command == 1) {

                    System.out.println("Specify a data file to load:");
                    String fileText = scnr.next();

                    loadDataFileCommand(fileText);

                }

                // Command for showing statistics
                if (command == 2) {

                    showStatisticsCommand();

                }

                // Command for showing closest connections
                if (command == 3) {

                    findClosestConnectionCommand();

                }

                // Command for exiting loop
                if (command == 4) {

                    exitCommand();
                    loop = false;

                }

            }

        }

        scnr.close();

    }

    /**
     * Displays main menu
     */
    @Override
    public void displayMainMenu() {

        System.out.println("Welcome to the Social Track App. Choose your command:");
        System.out.println("1 : Load a data file\n2 : Show statistics\n3 : Display closest connection\n4 : Exit app");

    }

    /**
     * Loads data file
     */
    @Override
    public void loadDataFileCommand(String filePath) {

        // Load file
        backend.readData(filePath);
        System.out.println("File loaded.\n");

    }

    /**
     * Gets and displays stats
     */
    @Override
    public void showStatisticsCommand() {

        // Get stats
        String output = backend.getStats();

        // Display stats
        System.out.println(output);

    }

    /**
     * Gets the user input to find and display the closest connection between
     * two people
     */
    @Override
    public void findClosestConnectionCommand() {

        // Get information from user
        System.out.println("Enter the name of the first person");
        String person1 = scnr.next();
        System.out.println("Enter the name of the second person");
        String person2 = scnr.next();

        // Get data from backend

        List<String> pathList = backend.getDistanceBetween(person1, person2).getPathBetweenFriends();
        int friends = backend.getDistanceBetween(person1, person2).numberOfIntermediaryFriends();

        // Display stats
        System.out.println("Closest path:");
        for (int i = 0; i < pathList.size(); i++) {

            System.out.println(pathList.get(i) + "\n");

        }

        System.out.println("Number of intermediary friends: " + friends);

    }

    /**
     * Exits app
     */
    @Override
    public void exitCommand() {

        // Exit app
        System.out.println("Exiting app...");
        System.exit(0);

    }

    public static void main(String[] args) {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        Backend backend = new Backend(graph);
        Frontend frontend = new Frontend(backend, new Scanner(System.in));
        frontend.runMainLoop();

    }

}
