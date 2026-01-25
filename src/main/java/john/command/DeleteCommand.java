package john.command;

import john.Storage;
import john.Ui;
import john.task.Task;
import john.task.TaskList;

public class DeleteCommand extends CommandBase {
    private static final String REMOVED_MESSAGE = "Noted. I've removed this task:\n    %s\n";
    private static final String COUNT_MESSAGE = "Now you have %d tasks in the list.\n";
    private final String taskNum;

    public DeleteCommand(String taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.get(Integer.parseInt(taskNum) - 1);
        tasks.remove(task);
        storage.saveTasks(tasks);
        System.out.printf(REMOVED_MESSAGE, task);
        System.out.printf(COUNT_MESSAGE, tasks.size());
    }
}
