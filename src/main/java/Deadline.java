
public class Deadline extends Task {
    protected String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    public static Deadline of(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new JohnException("The input of a deadline cannot be empty.");
        }
        String[] res = input.split("/");
        if (res.length > 2) {
            throw new JohnException("Too many arguments. A deadline should only have a description and a due date.");
        }
        String description = "";
        String deadline = "";
        for (String s : res) {
            String[] args = s.split(" ", 2);
            if (args[0].equals("by")) {
                if (!deadline.isEmpty()) {
                    throw new JohnException("Deadline must not have more than 1 due date.");
                }
                deadline = args.length > 1 ? args[1].trim() : "";
            } else {
                description = s.trim();
            }
        }

        if (description.isEmpty()) {
            throw new JohnException("The description of a deadline cannot be empty.");
        }
        if (deadline.isEmpty()) {
            throw new JohnException("The due date cannot be empty. Do you want to create a todo instead?");
        }
        return new Deadline(description, deadline);
    }

    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), deadline);
    }
}
