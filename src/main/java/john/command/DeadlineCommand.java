
package john.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import john.JohnException;
import john.storage.Storage;
import john.task.Deadline;
import john.task.Task;
import john.task.TaskList;
import john.ui.Ui;

/**
 * Command to add a deadline task to the task list.
 */
public class DeadlineCommand extends CommandBase {
    private static final String ADDED_MESSAGE = "Got it. I've added this task:\n    %s\n";
    private static final String COUNT_MESSAGE = "Now you have %d tasks in the list.\n";
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
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
        if (argument == null || argument.trim().isEmpty()) {
            throw new JohnException("The input of a deadline cannot be empty.");
        }
        String[] parts = argument.split("/by");
        if (parts.length > 2) {
            throw new JohnException("Too many arguments. A deadline should only have a description and a due date.");
        }
        String description = parts[0].trim();
        if (description.isEmpty()) {
            throw new JohnException("The description of a deadline cannot be empty.");
        }
        LocalDateTime deadline;
        try {
            deadline = LocalDateTime.parse(parts[1].trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new JohnException("Invalid date format. Please use the format: d/M/yyyy HHmm");
        }
        Task task = new Deadline(description, deadline);
        tasks.add(task);
        storage.saveTasks(tasks);
        return String.format(ADDED_MESSAGE, task) + String.format(COUNT_MESSAGE, tasks.size());
    }
}
