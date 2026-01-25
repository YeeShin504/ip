package john.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.JohnException;
import john.Storage;
import john.Ui;
import john.task.TaskList;
import john.task.ToDo;

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

    @Test
    void execute_addsToDoTask() {
        TodoCommand cmd = new TodoCommand("read book");
        cmd.execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0) instanceof ToDo);
        assertEquals("read book", tasks.get(0).toString().replaceAll(".*\\] ", ""));
        String output = outContent.toString();
        assertTrue(output.contains("Got it. I've added this task:"));
        assertTrue(output.contains("[T] [ ] read book"));
    }

    @Test
    void execute_emptyDescription_throwsException() {
        TodoCommand cmd = new TodoCommand("");
        Exception e = assertThrows(JohnException.class, () -> cmd.execute(tasks, ui, storage));
        assertTrue(e.getMessage().contains("The description of a todo cannot be empty"));
    }
}
