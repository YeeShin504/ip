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
import john.task.Todo;
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
        Todo todo = new Todo("read book");
        AddCommand cmd = new AddCommand(todo);
        String response = cmd.execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        assertTrue(response.contains("Very well. I have added this task to your agenda:"));
        assertTrue(response.contains("[T] [ ] read book"));
        assertTrue(response.contains("You now have 1 tasks in your list, sir/madam."));
    }
}
