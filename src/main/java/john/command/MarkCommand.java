package john.command;

import john.JohnException;
import john.storage.Storage;
import john.task.Task;
import john.task.TaskList;
import john.ui.Ui;

/**
 * Command to mark a task as completed.
 */
public class MarkCommand extends CommandBase {
    private final String taskNum;

    /**
     * Constructs a MarkCommand for the specified task number.
     *
     * @param taskNum The task number to mark as completed
     */
    public MarkCommand(String taskNum) {
        this.taskNum = taskNum;
    }

    /**
     * Executes the mark command and returns the response string.
     *
     * @param tasks   The task list containing the task to mark
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @return The response string
     * @throws JohnException if the task number is invalid
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.get(Integer.parseInt(taskNum) - 1);
        task.markComplete();
        storage.saveTasks(tasks);
        return "Nice! I've marked this task as done:\n    " + task;
    }
}
