package john.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.JohnException;
import john.storage.Storage;
import john.task.TaskList;
import john.task.Todo;
import john.ui.Ui;

class TodoCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage("./data/test_todo_command.txt");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        new java.io.File("./data/test_todo_command.txt").delete();
    }

    @Test
    void execute_addsToDoTask() {
        TodoCommand cmd = new TodoCommand("read book");
        String response = cmd.execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0) instanceof Todo);
        assertEquals("read book", tasks.get(0).toString().replaceAll(".*\\] ", ""));
        assertTrue(response.contains("Very well. I have added this task to your agenda:"));
        assertTrue(response.contains("[T] [ ] read book"));
        assertTrue(response.contains("You now have 1 task"));
        System.out.println("===== FULL RESPONSE =====");
        System.out.println(response);
        System.out.println("===== END RESPONSE =====");
    }

    @Test
    void execute_emptyDescription_throwsException() {
        TodoCommand cmd = new TodoCommand("");
        Exception e = assertThrows(JohnException.class, () -> cmd.execute(tasks, ui, storage));
        assertTrue(e.getMessage().contains("the description of a todo task cannot be empty"));
    }
}
