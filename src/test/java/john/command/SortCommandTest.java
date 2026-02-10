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
        // Unsorted: [deadline1, banana deadline, e1, deadline2, e2, e3, deadline3, zebra todo, apple todo]
        new SortCommand("").execute(tasks, ui, storage);
        // Should be: [deadline2, banana deadline, deadline1, deadline3, apple event, event2,
        //             event1, apple todo, zebra todo]
        // (deadlines earliest to latest, then events by start earliest to latest,
        //  then todos alphabetically)
        // Same dates sorted alphabetically by description
        String after = tasks.toString();
        int idxD2 = after.indexOf("deadline2");
        int idxBanana = after.indexOf("banana deadline");
        int idxD1 = after.indexOf("deadline1");
        int idxD3 = after.indexOf("deadline3");
        int idxAppleEvent = after.indexOf("apple event");
        int idxE2 = after.indexOf("event2");
        int idxE1 = after.indexOf("event1");
        int idxAppleTodo = after.indexOf("apple todo");
        int idxZebraTodo = after.indexOf("zebra todo");
        // Check deadline order (d2 earliest, then d1 with banana before deadline1, then d3)
        assertTrue(idxD2 < idxBanana && idxBanana < idxD1 && idxD1 < idxD3);
        // Check event order (apple event and event2 same date, alphabetically, then event1)
        assertTrue(idxD3 < idxAppleEvent && idxAppleEvent < idxE2 && idxE2 < idxE1);
        // Check todos are alphabetically ordered regardless
        assertTrue(idxE1 < idxAppleTodo && idxAppleTodo < idxZebraTodo);
    }

    @Test
    void execute_sortsByGroupAndDate_latest() {
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
        // Unsorted: [deadline1, banana deadline, e1, deadline2, e2, e3, deadline3, zebra todo, apple todo]
        new SortCommand("/latest").execute(tasks, ui, storage);
        // Should be: [deadline3, banana deadline, deadline1, deadline2, event1, apple event,
        //             event2, apple todo, zebra todo]
        // (deadlines latest to earliest, then events by start latest to earliest,
        //  then todos alphabetically)
        // Same dates sorted alphabetically by description
        String after = tasks.toString();
        int idxD3 = after.indexOf("deadline3");
        int idxBanana = after.indexOf("banana deadline");
        int idxD1 = after.indexOf("deadline1");
        int idxD2 = after.indexOf("deadline2");
        int idxE1 = after.indexOf("event1");
        int idxAppleEvent = after.indexOf("apple event");
        int idxE2 = after.indexOf("event2");
        int idxAppleTodo = after.indexOf("apple todo");
        int idxZebraTodo = after.indexOf("zebra todo");
        // Check deadline order (d3 latest, then d1 with banana before deadline1, then d2)
        assertTrue(idxD3 < idxBanana && idxBanana < idxD1 && idxD1 < idxD2);
        // Check event order (e1 latest, then apple event and event2 same date, alphabetically)
        assertTrue(idxD2 < idxE1 && idxE1 < idxAppleEvent && idxAppleEvent < idxE2);
        // Check todos are alphabetically ordered regardless
        assertTrue(idxE2 < idxAppleTodo && idxAppleTodo < idxZebraTodo);
    }
}
