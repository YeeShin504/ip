public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    public static ToDo of(String description) {
        return new ToDo(description);
    }

    public String toString() {
        return "[T] " + super.toString();
    }
}
