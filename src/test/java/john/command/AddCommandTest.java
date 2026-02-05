package john.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

class AddCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage("./data/test_add_command.txt");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        new java.io.File("./data/test_add_command.txt").delete();
    }

    @Test
    void execute_addsTaskAndPrintsMessage() {
        ToDo todo = new ToDo("read book");
        AddCommand cmd = new AddCommand(todo);
        String response = cmd.execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        assertTrue(response.contains("Got it. I've added this task:"));
        assertTrue(response.contains("[T] [ ] read book"));
        assertTrue(response.contains("Now you have 1 tasks in the list."));
    }
}
