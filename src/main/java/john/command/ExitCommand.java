package john.command;

import john.Storage;
import john.Ui;
import john.task.TaskList;

/**
 * Command to exit the application.
 */
public class ExitCommand extends CommandBase {
    /**
     * Executes the exit command, saving tasks and displaying a goodbye message.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        storage.saveTasks(tasks);
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Indicates that this command will exit the application.
     * 
     * @return true
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
