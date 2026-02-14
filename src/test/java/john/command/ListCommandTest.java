package john.command;

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

class ListCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        tasks.add(new Todo("read book"));
        Todo markedTodo = new Todo("marked task");
        markedTodo.markComplete();
        tasks.add(markedTodo);
        ui = new Ui();
        storage = new Storage("./data/test_list_command.txt");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        new java.io.File("./data/test_list_command.txt").delete();
    }

    @Test
    void execute_printsTaskList() {
        String response = new ListCommand().execute(tasks, ui, storage);
        assertTrue(response.contains("Certainly. Here are the tasks on your agenda:"));
        assertTrue(response.contains("1. [T] [ ] read book"));
        assertTrue(response.contains("2. [T] [X] marked task"));
    }
}
