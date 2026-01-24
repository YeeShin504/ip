package john.command;

import john.task.Task;
import john.task.TaskList;
import john.task.Event;
import john.Ui;
import john.Storage;
import john.JohnException;

public class EventCommand extends CommandBase {
    private static final String ADDED_MESSAGE = "Got it. I've added this task:\n    %s\n";
    private static final String COUNT_MESSAGE = "Now you have %d tasks in the list.\n";
    private final String argument;

    public EventCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
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
        java.time.LocalDateTime startDate, endDate;
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
        System.out.printf(ADDED_MESSAGE, task);
        System.out.printf(COUNT_MESSAGE, tasks.size());
    }
}
