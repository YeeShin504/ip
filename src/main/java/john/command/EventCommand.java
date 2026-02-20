package john.command;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import john.JohnException;
import john.storage.Storage;
import john.task.Event;
import john.task.Task;
import john.task.TaskList;
import john.ui.Ui;
import john.util.DateTimeValidator;

/**
 * Command to add an event task to the task list.
 */
public class EventCommand extends CommandBase {
    private static final Pattern EVENT_PATTERN = Pattern.compile(
        "^(.*?)\\s*/from\\s*(.*?)\\s*/to\\s*(.*)$",
        Pattern.CASE_INSENSITIVE);
    private static final String ADDED_MESSAGE = "Very well. I have added this task to your agenda:\n    %s\n";
    private static final String COUNT_MESSAGE = "You now have %d tasks in your list, %s.\n";
    private final String argument;

    /**
     * Constructs an EventCommand with the specified argument.
     *
     * @param argument The argument string containing description, start, and end
     *                 date
     */
    public EventCommand(String argument) {
        this.argument = argument;
    }

    /**
     * Executes the event command and returns the response string.
     *
     * @param tasks   The task list to add the event to
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @return The response string
     * @throws JohnException if the argument is invalid or date parsing fails
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        validateArgumentNotEmpty();
        String description = parseDescription();
        String[] dateStrings = parseDateStrings();
        LocalDateTime startDate = DateTimeValidator.parseDateTime(dateStrings[0], "start");
        LocalDateTime endDate = DateTimeValidator.parseDateTime(dateStrings[1], "end");
        DateTimeValidator.validateDateOrder(startDate, endDate);

        Task task = new Event(description, startDate, endDate);
        tasks.add(task);
        storage.saveTasks(tasks);
        String userName = john.util.UserNameUtil.getUserName();
        return String.format(ADDED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size(), userName);
    }

    private void validateArgumentNotEmpty() {
        if (argument == null || argument.trim().isEmpty()) {
            throw new JohnException("I beg your pardon, but the input for an event cannot be empty.");
        }
    }

    private String parseDescription() {
        Matcher matcher = EVENT_PATTERN.matcher(argument.trim());
        if (!matcher.matches()) {
            throw new JohnException("Invalid format. Please use: event <description> /from <start> /to <end>");
        }
        String description = matcher.group(1).trim();
        if (description.isEmpty()) {
            String userName = john.util.UserNameUtil.getUserName();
            throw new JohnException("I apologize, " + userName + ", but the description of an event cannot be empty.");
        }
        return description;
    }

    private String[] parseDateStrings() {
        Matcher matcher = EVENT_PATTERN.matcher(argument.trim());
        if (!matcher.matches()) {
            throw new JohnException("Invalid format. Please use: event <description> /from <start> /to <end>");
        }
        String startString = matcher.group(2).trim();
        String endString = matcher.group(3).trim();
        DateTimeValidator.validateDateNotEmpty(startString, "start");
        DateTimeValidator.validateDateNotEmpty(endString, "end");
        return new String[]{startString, endString};
    }
}
