package john.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import john.JohnException;
import john.storage.Storage;
import john.task.Event;
import john.task.Task;
import john.task.TaskList;
import john.ui.Ui;

/**
 * Command to add an event task to the task list.
 */
public class EventCommand extends CommandBase {
    private static final String ADDED_MESSAGE = "Very well. I have added this task to your agenda:\n    %s\n";
    private static final String COUNT_MESSAGE = "You now have %d tasks in your list, sir/madam.\n";
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
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
        if (argument == null || argument.trim().isEmpty()) {
            throw new JohnException("I beg your pardon, but the input for an event cannot be empty.");
        }

        // Check for duplicate /from parameters
        int fromCount = (argument.length() - argument.replace("/from", "").length()) / 5;
        if (fromCount > 1) {
            throw new JohnException("You have specified the /from parameter multiple times. "
                    + "Please provide only one start date.");
        }

        String[] parts = argument.split("/from");
        if (parts.length == 1) {
            throw new JohnException("The start date of an event cannot be empty. "
                    + "Please use format: event <description> /from <start> /to <end>");
        }

        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new JohnException("I apologize, sir/madam, but the description of an event cannot be empty.");
        }

        // Check for duplicate /to parameters
        int toCount = (parts[1].length() - parts[1].replace("/to", "").length()) / 3;
        if (toCount > 1) {
            throw new JohnException("You have specified the /to parameter multiple times. "
                    + "Please provide only one end date.");
        }

        String[] dateParts = parts[1].trim().split("/to");
        if (dateParts.length == 1) {
            throw new JohnException("The end date of an event cannot be empty. "
                    + "Please use format: event <description> /from <start> /to <end>");
        }

        String startString = dateParts[0].trim();
        String endString = dateParts[1].trim();

        if (startString.isEmpty()) {
            throw new JohnException("The start date cannot be empty. Please provide a date in format: d/M/yyyy HHmm");
        }
        if (endString.isEmpty()) {
            throw new JohnException("The end date cannot be empty. Please provide a date in format: d/M/yyyy HHmm");
        }

        LocalDateTime startDate;
        LocalDateTime endDate;
        try {
            startDate = LocalDateTime.parse(startString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            String errorMsg = "I must inform you that the start date format is invalid. ";
            if (e.getMessage().contains("Invalid date")) {
                errorMsg += "The date '" + startString + "' does not exist. ";
            } else {
                errorMsg += "Please use the format: d/M/yyyy HHmm (e.g., 25/12/2024 1800). ";
            }
            errorMsg += "Error: " + e.getMessage();
            throw new JohnException(errorMsg);
        }

        try {
            endDate = LocalDateTime.parse(endString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            String errorMsg = "I must inform you that the end date format is invalid. ";
            if (e.getMessage().contains("Invalid date")) {
                errorMsg += "The date '" + endString + "' does not exist. ";
            } else {
                errorMsg += "Please use the format: d/M/yyyy HHmm (e.g., 25/12/2024 1800). ";
            }
            errorMsg += "Error: " + e.getMessage();
            throw new JohnException(errorMsg);
        }

        if (endDate.isBefore(startDate)) {
            throw new JohnException("The end date of an event cannot be before the start date.");
        }

        if (endDate.isEqual(startDate)) {
            throw new JohnException("The end date of an event cannot be the same as the start date. "
                    + "Please provide different start and end times.");
        }

        Task task = new Event(description, startDate, endDate);
        tasks.add(task);
        storage.saveTasks(tasks);
        return String.format(ADDED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size());
    }
}
