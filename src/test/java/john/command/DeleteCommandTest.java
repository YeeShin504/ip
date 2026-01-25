package john.command;

import john.task.TaskList;
import john.task.ToDo;
import john.Ui;
import john.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class DeleteCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        tasks.add(new ToDo("read book"));
        ui = new Ui();
        storage = new Storage("./data/test_delete_command.txt");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void execute_removesTaskAndPrintsMessage() {
        DeleteCommand cmd = new DeleteCommand("1");
        cmd.execute(tasks, ui, storage);
        assertEquals(0, tasks.size());
        String output = outContent.toString();
        assertTrue(output.contains("Noted. I've removed this task:"));
        assertTrue(output.contains("[T] [ ] read book"));
    }
}
