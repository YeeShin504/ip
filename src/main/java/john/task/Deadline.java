package john.task;

import john.JohnException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
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
