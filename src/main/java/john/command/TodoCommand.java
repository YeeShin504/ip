package john.command;

import john.JohnException;
import john.storage.Storage;
import john.task.TaskList;
import john.task.Todo;
import john.ui.Ui;

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
     * Executes the todo command and returns the response string.
     *
     * @param tasks   The task list to add the todo to
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @return The response string
     * @throws JohnException if the argument is invalid
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (argument.trim().isEmpty()) {
            throw new JohnException("The description of a todo cannot be empty");
        }
        Todo task = new Todo(argument);
        tasks.add(task);
        storage.saveTasks(tasks);
        return String.format(ADDED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size());
    }
}
