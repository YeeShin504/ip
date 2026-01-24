package john.command;

import john.task.Task;
import john.task.TaskList;
import john.Ui;
import john.Storage;

public class AddCommand extends CommandBase {
    private static final String ADDED_MESSAGE = "Got it. I've added this task:\n    %s\n";
    private static final String COUNT_MESSAGE = "Now you have %d tasks in the list.\n";
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(task);
        storage.saveTasks(tasks);
        System.out.printf(ADDED_MESSAGE, task);
        System.out.printf(COUNT_MESSAGE, tasks.size());
    }
}
