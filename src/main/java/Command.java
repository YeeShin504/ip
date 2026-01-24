public enum Command {
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    MARK,
    UNMARK,
    LIST,
    BYE;

    public static Command fromString(String command) {
        try {
            return Command.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new JohnException("I'm sorry, but I don't know what this means: " + command);
        }
    }
}