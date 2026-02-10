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
    }

    /**
     * Constructs a Todo task with the given description and completion status.
     *
     * @param description The task description
     * @param isCompleted  Whether the task is completed
     */
    public Todo(String description, boolean isCompleted) {
        super(description, isCompleted);
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }

    @Override
    public String toDataString() {
        String status = isCompleted ? "1" : "0";
        String escapedDescription = description.replace("|", "\\|");
        return String.format("T | %s | %s\n", status, escapedDescription);
    }

    /**
     * Creates a Todo object from a data string.
     *
     * @param dataString The data string
     * @return The Todo object
     * @throws JohnException if the data string is invalid
     */
    public static Todo fromDataString(String dataString) {
        String[] parts = dataString.split(" \\| ", 3);
        if (!parts[0].trim().equals("T")) {
            throw new JohnException("Data string is not of type Todo: " + dataString);
        }
        boolean isCompleted = parts[1].trim().equals("1");
        String description = parts[2].replace("\\|", "|");
        Todo todo = new Todo(description, isCompleted);
        return todo;
    }
}
