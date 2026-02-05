package john.command;

import john.JohnException;
import john.storage.Storage;
import john.task.Task;
import john.task.TaskList;
import john.ui.Ui;

/**
 * Command to mark a task as incomplete.
 */
public class UnmarkCommand extends CommandBase {
    private final String taskNum;

    /**
     * Constructs an UnmarkCommand for the specified task number.
     *
     * @param taskNum The task number to mark as incomplete
     */
    public UnmarkCommand(String taskNum) {
        this.taskNum = taskNum;
    }

    /**
     * Executes the unmark command and returns the response string.
     *
     * @param tasks   The task list containing the task to unmark
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @return The response string
     * @throws JohnException if the task number is invalid
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.get(Integer.parseInt(taskNum) - 1);
        task.markIncomplete();
        storage.saveTasks(tasks);
        return "OK, I've marked this task as not done yet:\n    " + task;
    }
}
