
public class Deadline extends Task {
    protected String deadline;

    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    public static Deadline of(String input) {
        String[] res = input.split("/");
        StringBuilder description = new StringBuilder();
        String deadline = "";
        for (String s : res) {
            String[] args = s.split(" ", 2);
            if (args[0].equals("by")) {
                deadline = args.length > 1 ? args[1] : "";
            } else {
                description.append(" ").append(s.trim());
            }
        }
        return new Deadline(description.toString().trim(), deadline.trim());
    }

    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), deadline);
    }
}
