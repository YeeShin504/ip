public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    public static ToDo of(String description) {
        if (description.trim().isEmpty()) {
            throw new JohnException("The description of a todo cannot be empty");
        }
        return new ToDo(description);
    }

    public String toString() {
        return "[T] " + super.toString();
    }
}
