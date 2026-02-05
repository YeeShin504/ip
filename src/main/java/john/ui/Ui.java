package john.ui;

import java.util.Scanner;

import john.command.CommandBase;

/**
 * Handles user interface interactions for the John application.
 */
public class Ui {
    private final Scanner sc = new Scanner(System.in);

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
     * Shows a response message to the user.
     *
     * @param response The response message to display
     */
    public void showResponse(String response) {
        System.out.println(response);
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
     * Reads a command from a given input string.
     *
     * @param input The user input string
     * @return The parsed CommandBase object
     */
    public CommandBase readCommand(String input) {
        return john.Parser.parse(input);
    }

    /**
     * Closes the UI and releases resources.
     */
    public void close() {
        sc.close();
    }
}
