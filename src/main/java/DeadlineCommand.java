public class DeadlineCommand extends CommandBase {
    private static final String ADDED_MESSAGE = "Got it. I've added this task:\n    %s\n";
    private static final String COUNT_MESSAGE = "Now you have %d tasks in the list.\n";
    private final String argument;
    public DeadlineCommand(String argument) {
        this.argument = argument;
    }
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
            deadline = java.time.LocalDateTime.parse(res[1].trim(), java.time.format.DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
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
