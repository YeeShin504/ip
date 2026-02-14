package john;

import john.command.CommandBase;
import john.storage.Storage;
import john.task.TaskList;
import john.ui.Ui;

/**
 * The main class for the John application.
 */
public class John {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private CommandBase lastCommand;

    /**
     * Constructs a new John application instance.
     *
     * @param filePath Path to the data file for storage
     */
    public John(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = storage.loadTasks();
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main program loop for John.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                ui.showLine();
                CommandBase c = ui.readCommand();
                String response = c.execute(tasks, ui, storage);
                ui.showResponse(response);
                isExit = c.isExit();
            } catch (JohnException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        ui.close();
    }

    /**
     * Main method to start the John application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        new John("data/john.txt").run();
    }

    /**
     * Gets the response for a given user input.
     *
     * @param input The user input string
     * @return The response string from John
     * @throws JohnException if there is an error processing the command
     */
    public String getResponse(String input) throws JohnException {
        CommandBase c = ui.readCommand(input);
        lastCommand = c;
        return c.execute(tasks, ui, storage);
    }

    /**
     * Checks if the last command was an exit command.
     *
     * @return true if the last command was an exit command, false otherwise
     */
    public boolean isLastCommandExit() {
        return lastCommand != null && lastCommand.isExit();
    }
}
