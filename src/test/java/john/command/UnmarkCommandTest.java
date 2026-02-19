package john.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.storage.Storage;
import john.task.TaskList;
import john.task.Todo;
import john.ui.Ui;

class UnmarkCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        Todo todo = new Todo("read book");
        todo.markComplete();
        tasks.add(todo);
        ui = new Ui();
        storage = new Storage("./data/test_unmark_command.txt");
    }

    @AfterEach
    void tearDown() {
        new java.io.File("./data/test_unmark_command.txt").delete();
    }

    @Test
    void execute_unmarksTaskAndPrintsMessage() {
        UnmarkCommand cmd = new UnmarkCommand("1");
        String response = cmd.execute(tasks, ui, storage);
        assertTrue(tasks.get(0).toString().contains("[ ]"));
        assertTrue(response.contains("OK, I've marked this task as not done yet:"));
        assertTrue(response.contains("[T] [ ] read book"));
    }
}
