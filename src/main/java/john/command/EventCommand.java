package john.command;

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
    private static final String ADDED_MESSAGE = "Got it. I've added this task:\n    %s\n";
    private static final String COUNT_MESSAGE = "Now you have %d tasks in the list.\n";
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
        if (argument == null || argument.isEmpty()) {
            throw new JohnException("The input of an event cannot be empty.");
        }
        String[] res = argument.split("/from");
        if (res.length == 1) {
            throw new JohnException("The start date of an event cannot be empty.");
        } else if (res.length > 2) {
            throw new JohnException("Too many arguments. An event should only have a start date");
        }
        String description = res[0].trim();
        if (description.isEmpty()) {
            throw new JohnException("The description of an event cannot be empty.");
        }
        String[] dateParts = res[1].trim().split("/to");
        if (dateParts.length == 1) {
            throw new JohnException("The end date of an event cannot be empty.");
        } else if (dateParts.length > 2) {
            throw new JohnException("Too many arguments. An event should only have an end date.");
        }
        java.time.LocalDateTime startDate;
        java.time.LocalDateTime endDate;
        try {
            startDate = java.time.LocalDateTime.parse(dateParts[0].trim(),
                    java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
            endDate = java.time.LocalDateTime.parse(dateParts[1].trim(),
                    java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
            if (endDate.isBefore(startDate)) {
                throw new JohnException("The end date of an event cannot be before the start date.");
            }
        } catch (java.time.format.DateTimeParseException e) {
            throw new JohnException("Invalid date format. Please use the format: d/M/yyyy HHmm");
        }
        Task task = new Event(description, startDate, endDate);
        tasks.add(task);
        storage.saveTasks(tasks);
        return String.format(ADDED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size());
    }
}
