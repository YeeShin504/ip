public enum Command {
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    MARK,
    UNMARK,
    LIST,
    FIND,
    BYE,
    HELP;

    public static Command fromString(String command) {
        for (Command cmd : Command.values()) {
            if (cmd.name().equals(command.toUpperCase().trim())) {
                return cmd;
            }
        }
        throw new JohnException("I'm sorry, but I don't know what this means: " + command);
    }
}
