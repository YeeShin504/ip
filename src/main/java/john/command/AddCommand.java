package john.command;

import john.storage.Storage;
import john.task.Task;
import john.task.TaskList;
import john.ui.Ui;

/**
 * Command to add a new task to the task list.
 */
public class AddCommand extends CommandBase {
    private static final String ADDED_MESSAGE = "Very well. I have added this task to your agenda:\n    %s\n";
    private static final String COUNT_MESSAGE = "You now have %d tasks in your list, %s.\n";
    private final Task task;

    /**
     * Constructs an AddCommand with the specified task.
     *
     * @param task The task to add
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the add command and returns the response string.
     *
     * @param tasks   The task list to add the task to
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @return The response string
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(task);
        storage.saveTasks(tasks);
        String userName = john.util.UserNameUtil.getUserName();
        return String.format(ADDED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size(), userName);
    }
}
