package john.command;

import john.JohnException;
import john.Storage;
import john.Ui;
import john.task.TaskList;
import john.task.ToDo;

public class TodoCommand extends CommandBase {
    private static final String ADDED_MESSAGE = "Got it. I've added this task:\n    %s\n";
    private static final String COUNT_MESSAGE = "Now you have %d tasks in the list.\n";
    private final String argument;

    public TodoCommand(String argument) {
        this.argument = argument;
    }

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
