package john.command;

import john.task.TaskList;
import john.Ui;
import john.Storage;

public class ExitCommand extends CommandBase {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        storage.saveTasks(tasks);
        System.out.println("Bye. Hope to see you again soon!");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
