package john.command;

import john.task.Task;
import john.task.TaskList;
import john.Ui;
import john.Storage;

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
     * Executes the unmark command, marking the task as incomplete.
     *
     * @param tasks   The task list containing the task to unmark
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @throws JohnException if the task number is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.get(Integer.parseInt(taskNum) - 1);
        task.markIncomplete();
        storage.saveTasks(tasks);
    }
}
