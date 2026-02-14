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
    private static final String ADDED_MESSAGE = "Very well. I have added this task to your agenda:\n    %s\n";
    private static final String COUNT_MESSAGE = "You now have %d tasks in your list, sir/madam.\n";
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
        if (argument == null || argument.trim().isEmpty()) {
            throw new JohnException("I apologize, but the description of a todo task cannot be empty.");
        }

        String description = argument.trim();
        Todo task = new Todo(description);
        tasks.add(task);
        storage.saveTasks(tasks);
        return String.format(ADDED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size());
    }
}
