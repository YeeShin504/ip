package john.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.storage.Storage;
import john.task.TaskList;
import john.ui.Ui;

class ExitCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage("./data/test_exit_command.txt");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        new java.io.File("./data/test_exit_command.txt").delete();
    }

    @Test
    void execute_printsGoodbyeAndSavesTasks() {
        TodoCommand todo = new TodoCommand("read book");
        todo.execute(tasks, ui, storage);

        ExitCommand cmd = new ExitCommand();
        String response = cmd.execute(tasks, ui, storage);
        assertTrue(response.contains("Your tasks have been saved."));
        assertTrue(response.contains("Until next time, I remain at your service."));

        TaskList loadedTasks = storage.loadTasks();
        assertTrue(loadedTasks.get(0).toString().contains("[T] [ ] read book"));
    }

    @Test
    void isExit_returnsTrue() {
        ExitCommand cmd = new ExitCommand();
        assertTrue(cmd.isExit());
    }
}
