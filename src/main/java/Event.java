import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("h:mm a, d MMM yyyy");

    protected LocalDateTime startDate;
    protected LocalDateTime endDate;

    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(String description, LocalDateTime startDate, LocalDateTime endDate, boolean isComplete) {
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

    public static Event fromDataString(String dataString) {
        String[] parts = dataString.split(" \\| ", 5);
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
