package john.command;

import john.task.Task;
import john.task.TaskList;
import john.Ui;
import john.Storage;

/**
 * Command to mark a task as complete.
 */
public class MarkCommand extends CommandBase {
    private final String taskNum;

    /**
     * Constructs a MarkCommand for the specified task number.
     * 
     * @param taskNum The task number to mark as complete
     */
    public MarkCommand(String taskNum) {
        this.taskNum = taskNum;
    }

    /**
     * Executes the mark command, marking the task as complete.
     *
     * @param tasks   The task list containing the task to mark
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @throws JohnException if the task number is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.get(Integer.parseInt(taskNum) - 1);
        task.markComplete();
        storage.saveTasks(tasks);
    }
}
