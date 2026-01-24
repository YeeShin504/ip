package john.command;

import john.task.TaskList;
import john.Ui;
import john.Storage;

public class ListCommand extends CommandBase {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%s. %s\n", (i + 1), tasks.get(i));
        }
    }
}
