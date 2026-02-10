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
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        assert description != null : "Event description must not be null";
        assert startDate != null : "Event start date must not be null";
        assert endDate != null : "Event end date must not be null";
        super(description);
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
     * @param isComplete  Whether the task is complete
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate, boolean isComplete) {
        assert description != null : "Event description must not be null";
        assert startDate != null : "Event start date must not be null";
        assert endDate != null : "Event end date must not be null";
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
        this.isComplete = isComplete;
    }

    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), startDate.format(DISPLAY_FORMATTER),
                endDate.format(DISPLAY_FORMATTER));
    }

    @Override
    public String toDataString() {
        String status = isComplete ? "1" : "0";
        String escapedDescription = description.replace("|", "\\|");
        return String.format("E | %s | %s | %s | %s\n", status, escapedDescription, startDate.toString(),
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
        assert parts.length == 5 : "Event data string must have 5 parts";
        if (!parts[0].trim().equals("E")) {
            throw new JohnException("Data string is not of type Event: " + dataString);
        }
        boolean isComplete = parts[1].trim().equals("1");
        String description = parts[2].replace("\\|", "|");
        LocalDateTime startDate = LocalDateTime.parse(parts[3].trim());
        LocalDateTime endDate = LocalDateTime.parse(parts[4].trim());
        Event event = new Event(description, startDate, endDate, isComplete);
        return event;
    }
}
