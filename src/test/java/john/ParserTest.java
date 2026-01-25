package john;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ParserTest {
    @Test
    void parse_listCommand() {
        assertEquals("john.command.ListCommand", Parser.parse("list all").getClass().getName());
    }

    @Test
    void parse_byeCommand() {
        assertEquals("john.command.ExitCommand", Parser.parse("bye ").getClass().getName());
    }

    @Test
    void parse_markCommand() {
        assertEquals("john.command.MarkCommand", Parser.parse("mark 1").getClass().getName());
    }

    @Test
    void parse_unmarkCommand() {
        assertEquals("john.command.UnmarkCommand", Parser.parse("unmark 1").getClass().getName());
    }

    @Test
    void parse_todoCommand() {
        assertEquals("john.command.TodoCommand", Parser.parse("todo something").getClass().getName());
    }

    @Test
    void parse_deleteCommand() {
        assertEquals("john.command.DeleteCommand", Parser.parse("delete 1").getClass().getName());
    }

    @Test
    void parse_deadlineCommand() {
        assertEquals("john.command.DeadlineCommand",
                Parser.parse("deadline do /by 1/1/2026 1200").getClass().getName());
    }

    @Test
    void parse_eventCommand() {
        assertEquals("john.command.EventCommand",
                Parser.parse("event party /from 1/1/2026 1200 /to 1/1/2026 1300").getClass().getName());
    }

    @Test
    void parse_unknownCommand_throwsException() {
        Exception exception = assertThrows(JohnException.class, () -> Parser.parse("unknown"));
        assertTrue(exception.getMessage().contains("don't know what this means"));
    }
}
