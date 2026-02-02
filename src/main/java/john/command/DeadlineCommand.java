package john.command;

import john.JohnException;
import john.Storage;
import john.Ui;
import john.task.Deadline;
import john.task.Task;
import john.task.TaskList;

/**
 * Command to add a deadline task to the task list.
 */
public class DeadlineCommand extends CommandBase {
    private static final String ADDED_MESSAGE = "Got it. I've added this task:\n    %s\n";
    private static final String COUNT_MESSAGE = "Now you have %d tasks in the list.\n";
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
     * Executes the deadline command, adding a deadline task to the list.
     *
     * @param tasks   The task list to add the deadline to
     * @param ui      The user interface for displaying messages
     * @param storage The storage handler for saving tasks
     * @throws JohnException if the argument is invalid or date parsing fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (argument == null || argument.trim().isEmpty()) {
            throw new JohnException("The input of a deadline cannot be empty.");
        }
        String[] res = argument.split("/by");
        if (res.length > 2) {
            throw new JohnException("Too many arguments. A deadline should only have a description and a due date.");
        }
        String description = res[0].trim();
        if (description.isEmpty()) {
            throw new JohnException("The description of a deadline cannot be empty.");
        }
        java.time.LocalDateTime deadline;
        try {
            deadline = java.time.LocalDateTime.parse(res[1].trim(),
                    java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
        } catch (java.time.format.DateTimeParseException e) {
            throw new JohnException("Invalid date format. Please use the format: d/M/yyyy HHmm");
        }
        Task task = new Deadline(description, deadline);
        tasks.add(task);
        storage.saveTasks(tasks);
        System.out.printf(ADDED_MESSAGE, task);
        System.out.printf(COUNT_MESSAGE, tasks.size());
    }
}
