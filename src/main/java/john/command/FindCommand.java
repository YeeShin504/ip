package john.command;

import john.storage.Storage;
import john.task.TaskList;
import john.ui.Ui;

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
     * Executes the find command and returns the response string.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying output.
     * @param storage The storage handler (not used in this command).
     * @return The response string
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matchingTasks = tasks.findTasksByKeyword(keyword);
        return "Certainly. I have found the following matching tasks:\n" + matchingTasks.toString();
    }
}
