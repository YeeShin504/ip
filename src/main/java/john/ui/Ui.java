package john.ui;

import java.util.Scanner;

import john.command.CommandBase;

/**
 * Handles user interface interactions for the John application.
 */
public class Ui {
    private static final String SYNTAX_GUIDE = """
        Here are some commands you can try:
          - list: View all tasks
          - todo <description>: Add a ToDo task
          - deadline <description> /by <date>: Add a Deadline task
          - event <description> /from <start> /to <end>: Add an Event task
          - mark <task number>: Mark a task as done
          - unmark <task number>: Mark a task as not done
          - delete <task number>: Remove a task
          - find <keyword>: Search for tasks containing a keyword
          - sort [/latest]: Sort tasks by type and date
          - bye: Exit the application
        """;
    private final Scanner sc = new Scanner(System.in);

    /**
     * Retrieves the welcome message as a String for display in the GUI.
     */
    public static String getWelcomeMessage() {
        String logo = """
                       __      __
                      / /___  / /_  ____
                 __  / / __ \\/ __ \\/ __ \\
                / /_/ / /_/ / / / / / / /
                \\____/\\____/_/ /_/_/ /_/
                """;
        StringBuilder sb = new StringBuilder();
        sb.append("Good day! I am\n").append(logo).append("\n");
        sb.append("Your personal butler, at your service.\n");
        sb.append("How may I assist you today?\n");
        sb.append(SYNTAX_GUIDE);
        return sb.toString();
    }

    /**
     * Shows the welcome message to the user (console version).
     */
    public static void showWelcome() {
        Ui.showLine();
        System.out.println(getWelcomeMessage());
        Ui.showLine();
    }

    /**
     * Shows a separator line in the UI.
     */
    public static void showLine() {
        System.out.println("_".repeat(100));
    }

    /**
     * Shows an error message when loading tasks fails.
     */
    public void showLoadingError() {
        String userName = john.util.UserNameUtil.getUserName();
        System.out.println("I apologize, " + userName + ". There was an error retrieving your tasks from storage.");
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
