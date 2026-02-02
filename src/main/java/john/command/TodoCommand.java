package john.command;

import john.JohnException;
import john.Storage;
import john.Ui;
import john.task.TaskList;
import john.task.ToDo;

/**
 * Command to add a todo task to the task list.
 */
public class TodoCommand extends CommandBase {
    private static final String ADDED_MESSAGE = "Got it. I've added this task:\n    %s\n";
    private static final String COUNT_MESSAGE = "Now you have %d tasks in the list.\n";
    private final String argument;

    /**
     * Constructs a TodoCommand with the specified argument.
     *
     * @param argument The description of the todo task
     */
    public TodoCommand(String argument) {
        this.argument = argument;
    }

    /**
     * Executes the todo command, adding a todo task to the list.
     *
     * @param tasks   The task list to add the todo to
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @throws JohnException if the argument is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (argument.trim().isEmpty()) {
            throw new JohnException("The description of a todo cannot be empty");
        }
        ToDo task = new ToDo(argument);
        tasks.add(task);
        storage.saveTasks(tasks);
        System.out.printf(ADDED_MESSAGE, task);
        System.out.printf(COUNT_MESSAGE, tasks.size());
    }
}
