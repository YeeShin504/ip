package john.task;

import john.JohnException;

public abstract class Task {
    protected String description;
    protected boolean isComplete = false;

    public Task(String description) {
        this.description = description;
        this.isComplete = false;
    }

    public Task(String description, boolean isComplete) {
        this.description = description;
        this.isComplete = isComplete;
    }

    @Override
    public String toString() {
        String status = isComplete ? "[X]" : "[ ]";
        return String.format("%s %s", status, description);
    }

    public abstract String toDataString();

    public static Task fromDataString(String dataString) {
        if (dataString.startsWith("T |")) {
            return ToDo.fromDataString(dataString);
        } else if (dataString.startsWith("D |")) {
            return Deadline.fromDataString(dataString);
        } else if (dataString.startsWith("E |")) {
            return Event.fromDataString(dataString);
        } else {
            throw new JohnException("Unknown task type in data string: " + dataString);
        }
    }

    public void markComplete() {
        if (isComplete) {
            System.out.println("This task has already been marked as done:");
        } else {
            isComplete = true;
            System.out.println("Nice! I've marked this task as done:");
            System.out.printf("    %s\n", this);
        }
    }

    public void markIncomplete() {
        if (!isComplete) {
            System.out.println("This task has already been marked as not done:");
        } else {
            isComplete = false;
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.printf("    %s\n", this);
    }
}
