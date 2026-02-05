package john.command;

import john.storage.Storage;
import john.task.Task;
import john.task.TaskList;
import john.ui.Ui;

/**
 * Command to add a new task to the task list.
 */
public class AddCommand extends CommandBase {
    private static final String ADDED_MESSAGE = "Got it. I've added this task:\n    %s\n";
    private static final String COUNT_MESSAGE = "Now you have %d tasks in the list.\n";
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
        return String.format(ADDED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size());
    }
}
