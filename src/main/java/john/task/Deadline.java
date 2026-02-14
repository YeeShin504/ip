package john.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import john.JohnException;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("h:mm a, d MMM yyyy");

    protected LocalDateTime deadline;

    /**
     * Constructs a Deadline task with the given description and deadline.
     *
     * @param description The task description
     * @param deadline    The deadline date and time
     */
    public Deadline(String description, LocalDateTime deadline) {
        super(description);
        assert description != null : "Deadline description must not be null";
        assert deadline != null : "Deadline date must not be null";
        this.deadline = deadline;
    }

    /**
     * Constructs a Deadline task with the given description, deadline, and
     * completion status.
     *
     * @param description The task description
     * @param deadline    The deadline date and time
     * @param isCompleted  Whether the task is completed
     */
    public Deadline(String description, LocalDateTime deadline, boolean isCompleted) {
        super(description, isCompleted);
        assert description != null : "Deadline description must not be null";
        assert deadline != null : "Deadline date must not be null";
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), deadline.format(DISPLAY_FORMATTER));
    }

    @Override
    public String toDataString() {
        String status = isCompleted ? "1" : "0";
        String escapedDescription = description.replace("|", "\\|");
        return String.format("D | %s | %s | %s", status, escapedDescription, deadline.toString());
    }

    /**
     * Creates a Deadline object from a data string.
     *
     * @param dataString The data string
     * @return The Deadline object
     * @throws JohnException if the data string is invalid
     */
    public static Deadline fromDataString(String dataString) {
        assert dataString != null : "Data string must not be null";
        String[] parts = dataString.split(" \\| ", 4);
        assert parts.length == 4 : "Deadline data string must have 4 parts";
        if (!parts[0].trim().equals("D")) {
            throw new JohnException("Data string is not of type Deadline: " + dataString);
        }
        boolean isCompleted = parts[1].trim().equals("1");
        String description = parts[2].replace("\\|", "|");
        LocalDateTime deadline = LocalDateTime.parse(parts[3].trim());
        Deadline deadlineTask = new Deadline(description, deadline, isCompleted);
        return deadlineTask;
    }

    @Override
    public int compareTo(Task other) {
        if (!(other instanceof Deadline)) {
            throw new IllegalArgumentException("Can only compare Deadline to Deadline");
        }
        Deadline d = (Deadline) other;
        // Descending by default (most recent first)
        return d.deadline.compareTo(this.deadline);
    }
}
