package john.task;

import john.JohnException;

/**
 * Represents a todo task.
 */
public class ToDo extends Task {
    /**
     * Constructs a ToDo task with the given description.
     *
     * @param description The task description
     */
    public ToDo(String description) {
        assert description != null : "ToDo description must not be null";
        super(description);
    }

    /**
     * Constructs a ToDo task with the given description and completion status.
     *
     * @param description The task description
     * @param isComplete  Whether the task is complete
     */
    public ToDo(String description, boolean isComplete) {
        assert description != null : "ToDo description must not be null";
        super(description, isComplete);
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }

    @Override
    public String toDataString() {
        String status = isComplete ? "1" : "0";
        String escapedDescription = description.replace("|", "\\|");
        return String.format("T | %s | %s\n", status, escapedDescription);
    }

    /**
     * Creates a ToDo object from a data string.
     *
     * @param dataString The data string
     * @return The ToDo object
     * @throws JohnException if the data string is invalid
     */
    public static ToDo fromDataString(String dataString) {
        assert dataString != null : "Data string must not be null";
        String[] parts = dataString.split(" \\| ", 3);
        assert parts.length == 3 : "ToDo data string must have 3 parts";
        if (!parts[0].trim().equals("T")) {
            throw new JohnException("Data string is not of type ToDo: " + dataString);
        }
        boolean isComplete = parts[1].trim().equals("1");
        String description = parts[2].replace("\\|", "|");
        ToDo todo = new ToDo(description, isComplete);
        return todo;
    }
}
