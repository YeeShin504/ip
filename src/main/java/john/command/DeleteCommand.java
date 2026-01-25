package john.command;

import john.task.Task;
import john.task.TaskList;
import john.Ui;
import john.Storage;

/**
 * Command to delete a task from the task list.
 */
public class DeleteCommand extends CommandBase {
    private static final String REMOVED_MESSAGE = "Noted. I've removed this task:\n    %s\n";
    private static final String COUNT_MESSAGE = "Now you have %d tasks in the list.\n";
    private final String taskNum;

    /**
     * Constructs a DeleteCommand for the specified task number.
     * 
     * @param taskNum The task number to delete
     */
    public DeleteCommand(String taskNum) {
        this.taskNum = taskNum;
    }

    /**
     * Executes the delete command, removing the task from the list.
     *
     * @param tasks   The task list to remove the task from
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @throws JohnException if the task number is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.get(Integer.parseInt(taskNum) - 1);
        tasks.remove(task);
        storage.saveTasks(tasks);
        System.out.printf(REMOVED_MESSAGE, task);
        System.out.printf(COUNT_MESSAGE, tasks.size());
    }
}
