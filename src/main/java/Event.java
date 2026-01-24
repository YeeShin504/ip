import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private static final DateTimeFormatter IN_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
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

    public static Event of(String input) {
        if (input == null || input.isEmpty()) {
            throw new JohnException("The input of an event cannot be empty.");
        }
        ;
        String[] res = input.split("/from");
        if (res.length == 1) {
            throw new JohnException("The start date of an event cannot be empty.");
        } else if (res.length > 2) {
            throw new JohnException(
                    "Too many arguments. An event should only have a start date");
        }
        String description = res[0].trim();
        if (description.isEmpty()) {
            throw new JohnException("The description of an event cannot be empty.");
        }

        String[] dateParts = res[1].trim().split("/to");
        if (dateParts.length == 1) {
            throw new JohnException("The end date of an event cannot be empty.");
        } else if (dateParts.length > 2) {
            throw new JohnException(
                    "Too many arguments. An event should only have an end date.");
        }

        LocalDateTime startDate, endDate;
        try {
            startDate = LocalDateTime.parse(dateParts[0].trim(), IN_FORMATTER);
            endDate = LocalDateTime.parse(dateParts[1].trim(), IN_FORMATTER);
            if (endDate.isBefore(startDate)) {
                throw new JohnException("The end date of an event cannot be before the start date.");
            }
        } catch (DateTimeParseException e) {
            throw new JohnException("Invalid date format. Please use the format: d/M/yyyy HHmm");
        }

        return new Event(description, startDate, endDate);
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
