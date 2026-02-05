package john.command;

import john.storage.Storage;
import john.task.TaskList;
import john.ui.Ui;

/**
 * Command to exit the application.
 */
public class ExitCommand extends CommandBase {
    /**
     * Executes the exit command and returns the response string.
     *
     * @param tasks   The task list to operate on
     * @param ui      The user interface
     * @param storage The storage handler
     * @return The response string
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        storage.saveTasks(tasks);
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Indicates that this command will exit the application.
     *
     * @return true
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
