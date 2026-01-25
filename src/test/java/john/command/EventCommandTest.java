package john.command;

import john.task.TaskList;
import john.Ui;
import john.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class EventCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage("./data/test_event_command.txt");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void execute_addsEventTask() {
        EventCommand cmd = new EventCommand("project meeting /from 11/2/2026 2359 /to 2/11/2026 0000");
        cmd.execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        String output = outContent.toString();
        assertTrue(output.contains("Got it. I've added this task:"));
        assertTrue(output.contains("[E] [ ] project meeting"));
        assertTrue(output.contains("11 Feb 2026"));
        assertTrue(output.contains("2 Nov 2026"));
    }

    @Test
    void execute_invalidDate_throwsException() {
        EventCommand cmd = new EventCommand("time travel /from 02/11/2026 12:34 /to 11/02/2026 0000");
        Exception e = assertThrows(john.JohnException.class, () -> cmd.execute(tasks, ui, storage));
        assertTrue(e.getMessage().contains("Invalid date format"));
    }
}
