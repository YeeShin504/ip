package john.task;

import john.JohnException;

/**
 * Represents a todo task.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo task with the given description.
     *
     * @param description The task description
     */
    public Todo(String description) {
        super(description);
        assert description != null : "Todo description must not be null";
    }

    /**
     * Constructs a Todo task with the given description and completion status.
     *
     * @param description The task description
     * @param isCompleted  Whether the task is completed
     */
    public Todo(String description, boolean isCompleted) {
        super(description, isCompleted);
        assert description != null : "Todo description must not be null";
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }

    @Override
    public String toDataString() {
        String status = isCompleted ? "1" : "0";
        String escapedDescription = description.replace("|", "\\|");
        return String.format("T | %s | %s", status, escapedDescription);
    }

    /**
     * Creates a Todo object from a data string.
     *
     * @param dataString The data string
     * @return The Todo object
     * @throws JohnException if the data string is invalid
     */
    public static Todo fromDataString(String dataString) {
        assert dataString != null : "Data string must not be null";
        String[] parts = dataString.split(" \\| ", 3);
        assert parts.length == 3 : "Todo data string must have 3 parts";
        if (!parts[0].trim().equals("T")) {
            throw new JohnException("Data string is not of type Todo: " + dataString);
        }
        boolean isCompleted = parts[1].trim().equals("1");
        String description = parts[2].replace("\\|", "|");
        Todo todo = new Todo(description, isCompleted);
        return todo;
    }

    @Override
    public int compareTo(Task other) {
        if (!(other instanceof Todo)) {
            throw new IllegalArgumentException("Can only compare Todo to Todo");
        }
        Todo t = (Todo) other;
        // Ascending by default (alphabetical A-Z)
        return this.description.compareToIgnoreCase(t.description);
    }
}
