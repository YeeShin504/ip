package john;

import john.command.CommandBase;

/**
 * Handles user interface interactions for the John application.
 */
public class Ui {
    private final java.util.Scanner sc = new java.util.Scanner(System.in);

    /**
     * Shows the welcome message to the user.
     */
    public void showWelcome() {
        String logo = """
                       __      __       \s
                      / /___  / /_  ____
                 __  / / __ \\/ __ \\/ __ \\
                / /_/ / /_/ / / / / / / /\s
                \\____/\\____/_/ /_/_/ /_/
                """;
        showLine();
        System.out.println("Hello! I'm\n" + logo);
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Shows a separator line in the UI.
     */
    public void showLine() {
        System.out.println("_".repeat(100));
    }

    /**
     * Shows an error message when loading tasks fails.
     */
    public void showLoadingError() {
        System.out.println("Error loading tasks from file.");
    }

    /**
     * Shows an error message to the user.
     *
     * @param message The error message to display
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Reads a command from the user input.
     *
     * @return The parsed CommandBase object
     */
    public CommandBase readCommand() {
        String input = sc.nextLine();
        return john.Parser.parse(input);
    }

    /**
     * Closes the UI and releases resources.
     */
    public void close() {
        sc.close();
    }
}
