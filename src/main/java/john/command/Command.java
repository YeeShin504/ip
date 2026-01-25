package john.command;

import john.JohnException;

/**
 * Enum representing the different types of commands supported.
 */
public enum Command {
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    MARK,
    UNMARK,
    LIST,
    BYE,
    FIND;

    /**
     * Converts a string to the corresponding Command enum value.
     * @param command The command string
     * @return The corresponding Command enum value
     * @throws JohnException if the command is unknown
     */
    public static Command fromString(String command) {
        try {
            return Command.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new JohnException("I'm sorry, but I don't know what this means: " + command);
        }
    }
}
