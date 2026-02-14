package john.command;

import java.time.LocalDateTime;

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
    private static final String ADDED_MESSAGE = "Very well. I have added this task to your agenda:\n    %s\n";
    private static final String COUNT_MESSAGE = "You now have %d tasks in your list, sir/madam.\n";
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
        LocalDateTime startDate = parseDateTime(dateStrings[0], "start");
        LocalDateTime endDate = parseDateTime(dateStrings[1], "end");
        validateDateOrder(startDate, endDate);

        Task task = new Event(description, startDate, endDate);
        tasks.add(task);
        storage.saveTasks(tasks);
        return String.format(ADDED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size());
    }

    private void validateArgumentNotEmpty() {
        if (argument == null || argument.trim().isEmpty()) {
            throw new JohnException("I beg your pardon, but the input for an event cannot be empty.");
        }
    }

    private String parseDescription() {
        validateParameterCount("/from", 5, "start date");

        String[] parts = argument.split("/from");
        if (parts.length == 1) {
            throw new JohnException("The start date of an event cannot be empty. "
                    + "Please use format: event <description> /from <start> /to <end>");
        }

        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new JohnException("I apologize, sir/madam, but the description of an event cannot be empty.");
        }
        return description;
    }

    private String[] parseDateStrings() {
        String[] parts = argument.split("/from");
        String datePart = parts[1].trim();

        validateParameterCountInString(datePart, "/to", 3, "end date");

        String[] dateParts = datePart.split("/to");
        if (dateParts.length == 1) {
            throw new JohnException("The end date of an event cannot be empty. "
                    + "Please use format: event <description> /from <start> /to <end>");
        }

        String startString = dateParts[0].trim();
        String endString = dateParts[1].trim();

        DateTimeValidator.validateDateNotEmpty(startString, "start");
        DateTimeValidator.validateDateNotEmpty(endString, "end");

        return new String[]{startString, endString};
    }

    private void validateParameterCount(String parameter, int paramLength, String paramName) {
        int count = (argument.length() - argument.replace(parameter, "").length()) / paramLength;
        if (count > 1) {
            throw new JohnException("You have specified the " + parameter + " parameter multiple times. "
                    + "Please provide only one " + paramName + ".");
        }
    }

    private void validateParameterCountInString(String text, String parameter, int paramLength, String paramName) {
        int count = (text.length() - text.replace(parameter, "").length()) / paramLength;
        if (count > 1) {
            throw new JohnException("You have specified the " + parameter + " parameter multiple times. "
                    + "Please provide only one " + paramName + ".");
        }
    }

    private LocalDateTime parseDateTime(String dateString, String dateType) {
        return DateTimeValidator.parseDateTime(dateString, dateType);
    }

    private void validateDateOrder(LocalDateTime startDate, LocalDateTime endDate) {
        DateTimeValidator.validateDateOrder(startDate, endDate);
    }
}
