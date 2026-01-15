public class Event extends Task {
    protected String startDate;
    protected String endDate;

    public Event(String description, String startDate, String endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Event of(String input) {
        String[] res = input.split("/");
        StringBuilder description = new StringBuilder();
        String startDate = "", endDate = "";
        for (String s : res) {
            String[] args = s.split(" ", 2);
            switch (args[0]) {
                case "from":
                    startDate = args.length > 1 ? args[1] : "";
                    break;
                case "to":
                    endDate = args.length > 1 ? args[1] : "";
                    break;
                default:
                    description.append(" ").append(s.trim());
                    break;
            }
        }
        return new Event(description.toString().trim(), startDate.trim(), endDate.trim());
    }

    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), startDate, endDate);
    }
}
