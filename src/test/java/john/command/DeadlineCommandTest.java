package john.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.Storage;
import john.Ui;
import john.task.TaskList;

class DeadlineCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage("./data/test_deadline_command.txt");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void execute_addsDeadlineTask() {
        DeadlineCommand cmd = new DeadlineCommand("return book /by 2/11/2026 1930");
        cmd.execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        String output = outContent.toString();
        assertTrue(output.contains("Got it. I've added this task:"));
        assertTrue(output.contains("[D] [ ] return book"));
        assertTrue(output.contains("2 Nov 2026"));
    }

    @Test
    void execute_invalidDate_throwsException() {
        DeadlineCommand cmd = new DeadlineCommand("do homework /by no idea");
        Exception e = assertThrows(john.JohnException.class, () -> cmd.execute(tasks, ui, storage));
        assertTrue(e.getMessage().contains("Invalid date format"));
    }
}
