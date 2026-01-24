public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    public ToDo(String description, boolean isComplete) {
        super(description, isComplete);
    }

    public static ToDo of(String description) {
        if (description.trim().isEmpty()) {
            throw new JohnException("The description of a todo cannot be empty");
        }
        return new ToDo(description);
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

    public static ToDo fromDataString(String dataString) {
        String[] parts = dataString.split(" \\| ", 3);
        if (!parts[0].trim().equals("T")) {
            throw new JohnException("Data string is not of type ToDo: " + dataString);
        }
        boolean isComplete = parts[1].trim().equals("1");
        String description = parts[2].replace("\\|", "|");
        ToDo todo = new ToDo(description, isComplete);
        return todo;
    }
}
