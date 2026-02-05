package john.command;

import john.storage.Storage;
import john.task.TaskList;
import john.ui.Ui;

/**
 * Abstract base class for all command implementations.
 */
public abstract class CommandBase {
    /**
     * Executes the command and returns a response string.
     *
     * @param tasks   The task list to operate on
     * @param ui      The user interface
     * @param storage The storage handler
     * @return The response string from executing the command
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Indicates whether this command should exit the application.
     *
     * @return true if the command is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}
