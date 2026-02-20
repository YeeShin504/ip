
package john.command;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static final Pattern DEADLINE_PATTERN = Pattern.compile(
        "^(.*?)\\s*/by\\s*(.*)$",
        Pattern.CASE_INSENSITIVE);
    private static final String ADDED_MESSAGE = "Very well. I have added this task to your agenda:\n    %s\n";
    private static final String COUNT_MESSAGE = "You now have %d tasks in your list, %s.\n";
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
        LocalDateTime deadline = DateTimeValidator.parseDateTime(dateString, "deadline");

        Task task = new Deadline(description, deadline);
        tasks.add(task);
        storage.saveTasks(tasks);
        String userName = john.util.UserNameUtil.getUserName();
        return String.format(ADDED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size(), userName);
    }

    private void validateArgumentNotEmpty() {
        if (argument == null || argument.trim().isEmpty()) {
            throw new JohnException("I beg your pardon, but the input for a deadline cannot be empty.");
        }
    }

    private String parseDescription() {
        Matcher matcher = DEADLINE_PATTERN.matcher(argument.trim());
        if (!matcher.matches()) {
            throw new JohnException("Invalid format. Please use: deadline <description> /by <date>");
        }
        String description = matcher.group(1).trim();
        if (description.isEmpty()) {
            String userName = john.util.UserNameUtil.getUserName();
            throw new JohnException(
                "I apologize, " + userName + ", but the description of a deadline cannot be empty."
            );
        }
        return description;
    }

    private String parseDateString() {
        Matcher matcher = DEADLINE_PATTERN.matcher(argument.trim());
        if (!matcher.matches()) {
            throw new JohnException("Invalid format. Please use: deadline <description> /by <date>");
        }
        String dateString = matcher.group(2).trim();
        DateTimeValidator.validateDateNotEmpty(dateString, "deadline");
        return dateString;
    }
}
