package john.command;

import john.Storage;
import john.Ui;
import john.task.TaskList;

/**
 * Abstract base class for all command implementations.
 */
public abstract class CommandBase {
    /**
     * Executes the command with the given task list, UI, and storage.
     *
     * @param tasks   The task list to operate on
     * @param ui      The user interface
     * @param storage The storage handler
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Indicates whether this command should exit the application.
     *
     * @return true if the command is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}
