package john.command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.storage.Storage;
import john.task.TaskList;
import john.task.Todo;
import john.ui.Ui;

class MarkCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        tasks.add(new Todo("read book"));
        ui = new Ui();
        storage = new Storage("./data/test_mark_command.txt");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        new java.io.File("./data/test_mark_command.txt").delete();
    }

    @Test
    void execute_marksTaskAndPrintsMessage() {
        MarkCommand cmd = new MarkCommand("1");
        String response = cmd.execute(tasks, ui, storage);
        assertTrue(tasks.get(0).toString().contains("[X]"));
        assertTrue(response.contains("Nice! I've marked this task as done:"));
        assertTrue(response.contains("[T] [X] read book"));
    }
}
