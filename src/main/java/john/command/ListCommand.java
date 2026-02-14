package john.command;

import john.storage.Storage;
import john.task.TaskList;
import john.ui.Ui;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends CommandBase {
    /**
     * Executes the list command and returns the response string.
     *
     * @param tasks   The task list containing the task to mark
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @return The response string
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return "Certainly. Here are the tasks on your agenda:\n" + tasks.toString();
    }
}
