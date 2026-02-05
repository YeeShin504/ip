package john.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.storage.Storage;
import john.task.TaskList;
import john.task.ToDo;
import john.ui.Ui;

class MarkCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        tasks.add(new ToDo("read book"));
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
        cmd.execute(tasks, ui, storage);
        assertTrue(tasks.get(0).toString().contains("[X]"));
        String output = outContent.toString();
        assertTrue(output.contains("Nice! I've marked this task as done:"));
        assertTrue(output.contains("[T] [X] read book"));
    }
}
