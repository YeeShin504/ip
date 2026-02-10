package john.command;

import john.storage.Storage;
import john.task.TaskList;
import john.ui.Ui;

/**
 * Command to sort tasks by type and chronological order in the task list.
 */
public class SortCommand extends CommandBase {

    private final boolean isLatestFirst;

    /**
     * Constructs a SortCommand with argument string.
     * @param argument The argument string may contain /latest to sort from latest to earliest.
     */
    public SortCommand(String argument) {
        this.isLatestFirst = argument != null && argument.trim().toLowerCase().contains("/latest");
    }

    /**
     * Executes the sort command and returns the response string.
     *
     * @param tasks   The task list to sort
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @return        The task list after sorting
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.sortByTypeAndDate(this.isLatestFirst);
        storage.saveTasks(tasks);
        String order = isLatestFirst ? "latest-first" : "earliest-first";
        String sortMsg = String.format("Tasks have been sorted by type and %s order.\n", order);
        String listMsg = new ListCommand().execute(tasks, ui, storage);
        return sortMsg + listMsg;
    }
}
