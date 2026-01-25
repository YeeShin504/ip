package john.command;

import john.Storage;
import john.Ui;
import john.task.TaskList;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends CommandBase {
    /**
     * Executes the list command, displaying all tasks.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println("Here are the tasks in your list:");
        System.out.print(tasks);
    }
}
