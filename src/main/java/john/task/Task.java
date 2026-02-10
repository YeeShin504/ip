package john.task;

import john.JohnException;

/**
 * Abstract base class representing a task.
 */
public abstract class Task {
    protected String description;
    protected boolean isCompleted = false;

    /**
     * Constructs a Task with the given description.
     *
     * @param description The task description
     */
    public Task(String description) {
        assert description != null : "Task description must not be null";
        this.description = description;
        this.isCompleted = false;
    }

    /**
     * Constructs a Task with the given description and completion status.
     *
     * @param description The task description
     * @param isCompleted Whether the task is completed
     */
    public Task(String description, boolean isComplete) {
        assert description != null : "Task description must not be null";
        this.description = description;
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        String status = isCompleted ? "[X]" : "[ ]";
        return String.format("%s %s", status, description);
    }

    /**
     * Returns a string representation of the task for data storage.
     *
     * @return The data string
     */
    public abstract String toDataString();

    /**
     * Creates a Task object from a data string.
     *
     * @param dataString The data string
     * @return The Task object
     * @throws JohnException if the data string is invalid
     */
    public static Task fromDataString(String dataString) {
        assert dataString != null : "Data string must not be null";
        if (dataString.startsWith("T |")) {
            return Todo.fromDataString(dataString);
        } else if (dataString.startsWith("D |")) {
            return Deadline.fromDataString(dataString);
        } else if (dataString.startsWith("E |")) {
            return Event.fromDataString(dataString);
        } else {
            throw new JohnException("Unknown task type in data string: " + dataString);
        }
    }

    /**
     * Marks the task as completed and displays a message.
     */
    public void markComplete() {
        if (isCompleted) {
            System.out.println("This task has already been marked as done:");
        } else {
            isCompleted = true;
            System.out.println("Nice! I've marked this task as done:");
            System.out.printf("    %s\n", this);
        }
    }

    /**
     * Marks the task as incomplete and displays a message.
     */
    public void markIncomplete() {
        if (!isCompleted) {
            System.out.println("This task has already been marked as not done:");
        } else {
            isCompleted = false;
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.printf("    %s\n", this);
    }
}
