package john.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import john.JohnException;

/**
 * Represents an event task with a start and end date.
 */
public class Event extends Task {
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("h:mm a, d MMM yyyy");

    protected LocalDateTime startDate;
    protected LocalDateTime endDate;

    /**
     * Constructs an Event task with the given description, start date, and end
     * date.
     *
     * @param description The task description
     * @param startDate   The start date and time
     * @param endDate     The end date and time
     * @throws JohnException if end date is before or equal to start date
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        assert description != null : "Event description must not be null";
        assert startDate != null : "Event start date must not be null";
        assert endDate != null : "Event end date must not be null";

        if (endDate.isBefore(startDate)) {
            throw new JohnException("Event end date cannot be before start date.");
        }
        if (endDate.isEqual(startDate)) {
            throw new JohnException("Event end date cannot be the same as start date.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructs an Event task with the given description, start date, end date,
     * and completion status.
     *
     * @param description The task description
     * @param startDate   The start date and time
     * @param endDate     The end date and time
     * @param isCompleted Whether the task is completed
     * @throws JohnException if end date is before or equal to start date
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate, boolean isCompleted) {
        super(description);
        assert description != null : "Event description must not be null";
        assert startDate != null : "Event start date must not be null";
        assert endDate != null : "Event end date must not be null";

        if (endDate.isBefore(startDate)) {
            throw new JohnException("Event end date cannot be before start date.");
        }
        if (endDate.isEqual(startDate)) {
            throw new JohnException("Event end date cannot be the same as start date.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), startDate.format(DISPLAY_FORMATTER),
                endDate.format(DISPLAY_FORMATTER));
    }

    @Override
    public String toDataString() {
        String status = isCompleted ? "1" : "0";
        String escapedDescription = description.replace("|", "\\|");
        return String.format("E | %s | %s | %s | %s", status, escapedDescription, startDate.toString(),
                endDate.toString());
    }

    /**
     * Creates an Event object from a data string.
     *
     * @param dataString The data string
     * @return The Event object
     * @throws JohnException if the data string is invalid
     */
    public static Event fromDataString(String dataString) {
        assert dataString != null : "Data string must not be null";
        String[] parts = dataString.split(" \\| ", 5);

        if (parts.length != 5) {
            throw new JohnException("Invalid Event data format: expected 5 parts, got "
                    + parts.length + " in: " + dataString);
        }

        if (!parts[0].trim().equals("E")) {
            throw new JohnException("Data string is not of type Event: " + dataString);
        }

        String statusStr = parts[1].trim();
        if (!statusStr.equals("0") && !statusStr.equals("1")) {
            throw new JohnException("Invalid completion status '" + statusStr
                    + "' in Event data: " + dataString);
        }
        boolean isCompleted = statusStr.equals("1");

        String description = parts[2].replace("\\|", "|").trim();
        if (description.isEmpty()) {
            throw new JohnException("Event description cannot be empty in data: " + dataString);
        }

        LocalDateTime startDate;
        LocalDateTime endDate;
        try {
            startDate = LocalDateTime.parse(parts[3].trim());
        } catch (Exception e) {
            throw new JohnException("Invalid start date format in Event data: " + dataString
                    + ". Error: " + e.getMessage());
        }

        try {
            endDate = LocalDateTime.parse(parts[4].trim());
        } catch (Exception e) {
            throw new JohnException("Invalid end date format in Event data: " + dataString
                    + ". Error: " + e.getMessage());
        }

        return new Event(description, startDate, endDate, isCompleted);
    }

    @Override
    public int compareTo(Task other) {
        if (!(other instanceof Event)) {
            throw new IllegalArgumentException("Can only compare Event to Event");
        }
        Event e = (Event) other;
        // Descending by default (most recent start first)
        return e.startDate.compareTo(this.startDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Event other = (Event) obj;
        return startDate.equals(other.startDate) && endDate.equals(other.endDate);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(description, startDate, endDate);
    }
}
