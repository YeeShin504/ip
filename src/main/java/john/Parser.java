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
        String[] parts = input.trim().split(" ", 2);
        String argument = parts.length > 1 ? parts[1] : "";
        Command cmd = Command.fromString(parts[0]);
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
        throw new JohnException("I'm sorry, but I don't know what this means: " + input);
    }
}
