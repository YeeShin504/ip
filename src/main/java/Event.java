public class Event extends Task {
    protected String startDate;
    protected String endDate;

    public Event(String description, String startDate, String endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Event of(String input) {
        if (input == null || input.isEmpty()) {
            throw new JohnException("The input of an event cannot be empty.");
        }
        ;
        String[] res = input.split("/");
        if (res.length > 3) {
            throw new JohnException("Too many arguments. An event should only have a description, start date and end date.");
        }
        String description = "";
        String startDate = "", endDate = "";
        for (String s : res) {
            String[] args = s.split(" ", 2);
            switch (args[0]) {
                case "from":
                    if (!startDate.isEmpty()) {
                        throw new JohnException("Start date must not have more than 1 due date.");
                    }
                    startDate = args.length > 1 ? args[1].trim() : "";
                    break;
                case "to":
                    if (!endDate.isEmpty()) {
                        throw new JohnException("End date must not have more than 1 due date.");
                    }
                    endDate = args.length > 1 ? args[1].trim() : "";
                    break;
                default:
                    description = s.trim();
                    break;
            }
        }

        if (description.isEmpty()) {
            throw new JohnException("The description of an event cannot be empty.");
        }
        if (startDate.isEmpty()) {
            String option = endDate.isEmpty() ? "task" : "deadline";
            String message = String.format("The start date cannot be empty. Do you want to create a %s instead?", option);
            throw new JohnException(message);
        }
        if (endDate.isEmpty()) {
            throw new JohnException("The end date cannot be empty.");
        }
        return new Event(description, startDate, endDate);
    }

    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), startDate, endDate);
    }
}
