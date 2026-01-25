package john.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.Storage;
import john.Ui;
import john.task.TaskList;
import john.task.ToDo;

class UnmarkCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ToDo todo = new ToDo("read book");
        todo.markComplete();
        tasks.add(todo);
        ui = new Ui();
        storage = new Storage("./data/test_unmark_command.txt");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void execute_unmarksTaskAndPrintsMessage() {
        UnmarkCommand cmd = new UnmarkCommand("1");
        cmd.execute(tasks, ui, storage);
        assertTrue(tasks.get(0).toString().contains("[ ]"));
        String output = outContent.toString();
        assertTrue(output.contains("OK, I've marked this task as not done yet:"));
        assertTrue(output.contains("[T] [ ] read book"));
    }
}
