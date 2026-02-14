
package john.command;

import java.time.LocalDateTime;

import john.JohnException;
import john.storage.Storage;
import john.task.Deadline;
import john.task.Task;
import john.task.TaskList;
import john.ui.Ui;
import john.util.DateTimeValidator;

/**
 * Command to add a deadline task to the task list.
 */
public class DeadlineCommand extends CommandBase {
    private static final String ADDED_MESSAGE = "Very well. I have added this task to your agenda:\n    %s\n";
    private static final String COUNT_MESSAGE = "You now have %d tasks in your list, sir/madam.\n";
    private final String argument;

    /**
     * Constructs a DeadlineCommand with the specified argument.
     *
     * @param argument The argument string containing description and due date
     */
    public DeadlineCommand(String argument) {
        this.argument = argument;
    }

    /**
     * Executes the deadline command and returns the response string.
     *
     * @param tasks   The task list to add the deadline to
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @return The response string
     * @throws JohnException if the argument is invalid or date parsing fails
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        validateArgumentNotEmpty();
        String description = parseDescription();
        String dateString = parseDateString();
        LocalDateTime deadline = parseDateTime(dateString);

        Task task = new Deadline(description, deadline);
        tasks.add(task);
        storage.saveTasks(tasks);
        return String.format(ADDED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size());
    }

    private void validateArgumentNotEmpty() {
        if (argument == null || argument.trim().isEmpty()) {
            throw new JohnException("I beg your pardon, but the input for a deadline cannot be empty.");
        }
    }

    private String parseDescription() {
        validateParameterCount();

        String[] parts = argument.split("/by");
        if (parts.length == 1) {
            throw new JohnException("The deadline date cannot be empty. Please use format: "
                    + "deadline <description> /by <date>");
        }

        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new JohnException("I apologize, sir/madam, but the description of a deadline cannot be empty.");
        }
        return description;
    }

    private void validateParameterCount() {
        int byCount = (argument.length() - argument.replace("/by", "").length()) / 3;
        if (byCount > 1) {
            throw new JohnException("You have specified the /by parameter multiple times. "
                    + "Please provide only one deadline date.");
        }
    }

    private String parseDateString() {
        String[] parts = argument.split("/by");
        String dateString = parts[1].trim();

        DateTimeValidator.validateDateNotEmpty(dateString, "deadline");
        return dateString;
    }

    private LocalDateTime parseDateTime(String dateString) {
        return DateTimeValidator.parseDateTime(dateString, "deadline");
    }
}
