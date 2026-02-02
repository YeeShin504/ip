package john.command;

import john.Storage;
import john.Ui;
import john.task.Task;
import john.task.TaskList;

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
     * Executes the add command, adding the task to the list and saving it.
     *
     * @param tasks   The task list to add the task to
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(task);
        storage.saveTasks(tasks);
        System.out.printf(ADDED_MESSAGE, task);
        System.out.printf(COUNT_MESSAGE, tasks.size());
    }
}
