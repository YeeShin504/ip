package john.task;

import john.JohnException;

/**
 * Abstract base class representing a task.
 */
public abstract class Task implements Comparable<Task> {
    protected String description;
    protected boolean isCompleted = false;

    /**
     * Constructs a Task with the given description.
     *
     * @param description The task description
     * @throws JohnException if description is empty or whitespace
     */
    public Task(String description) {
        assert description != null : "Task description must not be null";

        if (description.trim().isEmpty()) {
            throw new JohnException("Task description cannot be empty or whitespace.");
        }

        this.description = description;
        this.isCompleted = false;
    }

    /**
     * Constructs a Task with the given description and completion status.
     *
     * @param description The task description
     * @param isCompleted Whether the task is completed
     * @throws JohnException if description is empty or whitespace
     */
    public Task(String description, boolean isCompleted) {
        assert description != null : "Task description must not be null";

        if (description.trim().isEmpty()) {
            throw new JohnException("Task description cannot be empty or whitespace.");
        }

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
     * Marks the task as completed and returns a message.
     *
     * @return The message indicating the task's completion status
     */
    public String markComplete() {
        if (isCompleted) {
            return "This task has already been marked as done:\n    " + this;
        } else {
            isCompleted = true;
            return "Nice! I've marked this task as done:\n    " + this;
        }
    }

    /**
     * Marks the task as incomplete and returns a message.
     *
     * @return The message indicating the task's incompletion status
     */
    public String markIncomplete() {
        if (!isCompleted) {
            return "This task has already been marked as not done:\n    " + this;
        } else {
            isCompleted = false;
            return "OK, I've marked this task as not done yet:\n    " + this;
        }
    }

    /**
     * Checks if this task is a duplicate of another task (ignoring completion status).
     * Two tasks are considered duplicates if they have the same description and dates (if applicable).
     *
     * @param obj The object to compare with
     * @return true if the tasks are duplicates
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task other = (Task) obj;
        return description.equals(other.description);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(description);
    }
}
