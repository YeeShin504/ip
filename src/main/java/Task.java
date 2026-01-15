public class Task {
    protected String description;
    protected boolean isComplete = false;

    public Task(String description) {
        this.description = description;
        this.isComplete = false;
    }

    public static Task of(String description) {
        return new Task(description);
    }

    public String toString() {
        String status = isComplete ? "[X]" : "[ ]";
        return String.format("%s %s", status, description);
    }

    protected void markComplete() {
        if (isComplete) {
            System.out.println("This task has already been marked as done:");
        } else {
            isComplete = true;
            System.out.println("Nice! I've marked this task as done:");
            System.out.printf("    %s\n", this);
        }
    }

    protected void markIncomplete() {
        if (!isComplete) {
            System.out.println("This task has already been marked as not done:");
        } else {
            isComplete = false;
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.printf("    %s\n", this);
    }
}
