import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private static final DateTimeFormatter IN_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("h:mm a, d MMM yyyy");

    protected LocalDateTime deadline;

    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        this.deadline = deadline;
    }

    public Deadline(String description, LocalDateTime deadline, boolean isComplete) {
        super(description, isComplete);
        this.deadline = deadline;
    }

    public static Deadline of(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new JohnException("The input of a deadline cannot be empty.");
        }
        String[] res = input.split("/by");
        if (res.length > 2) {
            throw new JohnException("Too many arguments. A deadline should only have a description and a due date.");
        }
        String description = res[0].trim();
        if (description.isEmpty()) {
            throw new JohnException("The description of a deadline cannot be empty.");
        }

        LocalDateTime deadline;
        try {
            deadline = LocalDateTime.parse(res[1].trim(), IN_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new JohnException("Invalid date format. Please use the format: d/M/yyyy HHmm");
        }

        return new Deadline(description, deadline);
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), deadline.format(DISPLAY_FORMATTER));
    }

    @Override
    public String toDataString() {
        String status = isComplete ? "1" : "0";
        String escapedDescription = description.replace("|", "\\|");
        return String.format("D | %s | %s | %s\n", status, escapedDescription, deadline.toString());
    }

    public static Deadline fromDataString(String dataString) {
        String[] parts = dataString.split(" \\| ", 4);
        if (!parts[0].trim().equals("D")) {
            throw new JohnException("Data string is not of type Deadline: " + dataString);
        }
        boolean isComplete = parts[1].trim().equals("1");
        String description = parts[2].replace("\\|", "|");
        LocalDateTime deadline = LocalDateTime.parse(parts[3].trim());
        Deadline deadlineTask = new Deadline(description, deadline, isComplete);
        return deadlineTask;
    }
}
