package john.command;

import john.JohnException;
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
     * @throws JohnException if the keyword is empty
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new JohnException("I beg your pardon, but the search keyword cannot be empty.");
        }

        TaskList matchingTasks = tasks.findTasksByKeyword(keyword.trim());
        return "Certainly. I have found the following matching tasks:\n" + matchingTasks.toString();
    }
}
