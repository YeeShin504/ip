package john.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import john.storage.Storage;
import john.task.Deadline;
import john.task.TaskList;
import john.ui.Ui;

class SortCommandTest {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new Ui();
        storage = new Storage("./data/test_sort_command.txt");
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        new java.io.File("./data/test_sort_command.txt").delete();
    }

    @Test
    void execute_sortsByGroupAndDate_default() {
        setupTestTasks();
        new SortCommand("").execute(tasks, ui, storage);
        String after = tasks.toString();
        verifyDefaultSortOrder(after);
    }

    @Test
    void execute_sortsByGroupAndDate_latest() {
        setupTestTasks();
        new SortCommand("/latest").execute(tasks, ui, storage);
        String after = tasks.toString();
        verifyLatestSortOrder(after);
    }

    private void setupTestTasks() {
        LocalDateTime d1 = LocalDateTime.of(2026, 11, 2, 19, 30);
        LocalDateTime d2 = LocalDateTime.of(2025, 5, 10, 8, 0);
        LocalDateTime d3 = LocalDateTime.of(2027, 1, 1, 12, 0);
        john.task.Event e1 = new john.task.Event("event1", d1, d3); // start: d1
        john.task.Event e2 = new john.task.Event("event2", d2, d1); // start: d2
        john.task.Event e3 = new john.task.Event("apple event", d2, d1); // same start as e2
        john.task.Todo t1 = new john.task.Todo("zebra todo");
        john.task.Todo t2 = new john.task.Todo("apple todo");
        tasks.add(new Deadline("deadline1", d1));
        tasks.add(new Deadline("banana deadline", d1)); // same date as deadline1
        tasks.add(e1);
        tasks.add(new Deadline("deadline2", d2));
        tasks.add(e2);
        tasks.add(e3);
        tasks.add(new Deadline("deadline3", d3));
        tasks.add(t1);
        tasks.add(t2);
    }

    private void verifyDefaultSortOrder(String output) {
        // Expected: deadline2, banana deadline, deadline1, deadline3,
        //           apple event, event2, event1, apple todo, zebra todo
        int idxD2 = output.indexOf("deadline2");
        int idxBanana = output.indexOf("banana deadline");
        int idxD1 = output.indexOf("deadline1");
        int idxD3 = output.indexOf("deadline3");
        int idxAppleEvent = output.indexOf("apple event");
        int idxE2 = output.indexOf("event2");
        int idxE1 = output.indexOf("event1");
        int idxAppleTodo = output.indexOf("apple todo");
        int idxZebraTodo = output.indexOf("zebra todo");

        assertTrue(idxD2 < idxBanana && idxBanana < idxD1 && idxD1 < idxD3);
        assertTrue(idxD3 < idxAppleEvent && idxAppleEvent < idxE2 && idxE2 < idxE1);
        assertTrue(idxE1 < idxAppleTodo && idxAppleTodo < idxZebraTodo);
    }

    private void verifyLatestSortOrder(String output) {
        // Expected: deadline3, banana deadline, deadline1, deadline2,
        //           event1, apple event, event2, apple todo, zebra todo
        int idxD3 = output.indexOf("deadline3");
        int idxBanana = output.indexOf("banana deadline");
        int idxD1 = output.indexOf("deadline1");
        int idxD2 = output.indexOf("deadline2");
        int idxE1 = output.indexOf("event1");
        int idxAppleEvent = output.indexOf("apple event");
        int idxE2 = output.indexOf("event2");
        int idxAppleTodo = output.indexOf("apple todo");
        int idxZebraTodo = output.indexOf("zebra todo");

        assertTrue(idxD3 < idxBanana && idxBanana < idxD1 && idxD1 < idxD2);
        assertTrue(idxD2 < idxE1 && idxE1 < idxAppleEvent && idxAppleEvent < idxE2);
        assertTrue(idxE2 < idxAppleTodo && idxAppleTodo < idxZebraTodo);
    }
}
