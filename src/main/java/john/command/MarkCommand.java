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
        assert taskNum != null : "Task number must not be null";
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
        if (taskNum == null || taskNum.trim().isEmpty()) {
            throw new JohnException("I beg your pardon, but you must specify a task number to mark.");
        }

        int taskIndex;
        try {
            taskIndex = Integer.parseInt(taskNum.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new JohnException("I'm afraid '" + taskNum + "' is not a valid task number. "
                    + "Please provide a positive integer.");
        }

        if (taskIndex < 0) {
            throw new JohnException("Task number must be positive. You provided: " + (taskIndex + 1));
        }

        Task task = tasks.get(taskIndex);
        String message = task.markComplete();
        storage.saveTasks(tasks);
        return message;
    }
}
