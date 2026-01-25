package john.command;

import john.Storage;
import john.Ui;
import john.task.Task;
import john.task.TaskList;

public class MarkCommand extends CommandBase {
    private final String taskNum;

    public MarkCommand(String taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.get(Integer.parseInt(taskNum) - 1);
        task.markComplete();
        storage.saveTasks(tasks);
    }
}
