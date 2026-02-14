package john;

import john.command.Command;
import john.command.CommandBase;
import john.command.DeadlineCommand;
import john.command.DeleteCommand;
import john.command.EventCommand;
import john.command.ExitCommand;
import john.command.FindCommand;
import john.command.ListCommand;
import john.command.MarkCommand;
import john.command.SortCommand;
import john.command.TodoCommand;
import john.command.UnmarkCommand;

/**
 * Parses user input into command objects.
 */
public class Parser {
    /**
     * Parses the user input and returns the appropriate Command object.
     *
     * @param input The user input string
     * @return The corresponding CommandBase object
     * @throws JohnException if the input is invalid
     */
    public static CommandBase parse(String input) {
        assert input != null : "Input to Parser.parse must not be null";

        // Normalize whitespace: trim leading/trailing spaces and collapse multiple spaces
        String normalized = input.trim().replaceAll("\\s+", " ");

        if (normalized.isEmpty()) {
            throw new JohnException("I beg your pardon, but the command cannot be empty.");
        }

        String[] parts = normalized.split(" ", 2);
        String commandWord = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";

        // Validate command word contains only alphanumeric characters
        if (!commandWord.matches("[a-zA-Z0-9]+")) {
            throw new JohnException("I'm sorry, but the command contains invalid characters: " + commandWord);
        }

        Command cmd = Command.fromString(commandWord);
        switch (cmd) {
        case LIST:
            return new ListCommand();
        case BYE:
            return new ExitCommand();
        case MARK:
            return new MarkCommand(argument);
        case UNMARK:
            return new UnmarkCommand(argument);
        case TODO:
            return new TodoCommand(argument);
        case DELETE:
            return new DeleteCommand(argument);
        case DEADLINE:
            return new DeadlineCommand(argument);
        case EVENT:
            return new EventCommand(argument);
        case FIND:
            return new FindCommand(argument);
        case SORT:
            return new SortCommand(argument);
        default:
            break;
        }
        throw new JohnException("I beg your pardon, sir/madam, but I do not understand: " + input);
    }
}
