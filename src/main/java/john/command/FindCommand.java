package john.command;

import john.Ui;
import john.task.TaskList;

/**
 * Represents a command to find and list all tasks containing a specific
 * keyword.
 */
public class FindCommand extends CommandBase {
    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword to search for.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching for tasks containing the keyword
     * and displaying the matching tasks to the user.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying output.
     * @param storage The storage handler (not used in this command).
     */
    @Override
    public void execute(TaskList tasks, Ui ui, john.Storage storage) {
        TaskList matchingTasks = tasks.findTasksByKeyword(keyword);
        ui.showLine();
        System.out.println(" Here are the matching tasks in your list:");
        System.out.print(matchingTasks);
        ui.showLine();
    }
}
