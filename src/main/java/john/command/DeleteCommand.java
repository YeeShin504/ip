package john.command;

import john.JohnException;
import john.storage.Storage;
import john.task.Task;
import john.task.TaskList;
import john.ui.Ui;

/**
 * Command to delete a task from the task list.
 */
public class DeleteCommand extends CommandBase {
    private static final String REMOVED_MESSAGE = "Very well. I have removed this task from your agenda:\n    %s\n";
    private static final String COUNT_MESSAGE = "You now have %d tasks remaining, sir/madam.\n";
    private final String taskNum;

    /**
     * Constructs a DeleteCommand for the specified task number.
     *
     * @param taskNum The task number to delete
     */
    public DeleteCommand(String taskNum) {
        assert taskNum != null : "Task number must not be null";
        this.taskNum = taskNum;
    }

    /**
     * Executes the delete command and returns the response string.
     *
     * @param tasks   The task list to remove the task from
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @return The response string
     * @throws JohnException if the task number is invalid
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (taskNum == null || taskNum.trim().isEmpty()) {
            throw new JohnException("I beg your pardon, but you must specify a task number to delete.");
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
        tasks.remove(task);
        storage.saveTasks(tasks);
        return String.format(REMOVED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size());
    }
}
