package john.command;

import john.Storage;
import john.Ui;
import john.task.TaskList;

public class ListCommand extends CommandBase {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println("Here are the tasks in your list:");
        System.out.print(tasks);
    }
}
