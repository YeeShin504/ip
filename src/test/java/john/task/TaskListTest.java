package john.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import john.JohnException;

class TaskListTest {
    @Test
    void add_and_get_and_remove() {
        TaskList list = new TaskList();
        ToDo todo = new ToDo("read book");
        list.add(todo);
        assertEquals(1, list.size());
        assertEquals(todo, list.get(0));
        list.remove(todo);
        assertEquals(0, list.size());
    }

    @Test
    void get_invalidIndex_throws() {
        TaskList list = new TaskList();
        Exception e = assertThrows(JohnException.class, () -> list.get(1));
        assertTrue(e.getMessage().contains("Invalid task number"));
    }

    @Test
    void getAll_returnsAllTasks() {
        ToDo t1 = new ToDo("a");
        ToDo t2 = new ToDo("b");
        TaskList list = new TaskList(Arrays.asList(t1, t2));
        assertEquals(2, list.getAll().size());
        assertTrue(list.getAll().contains(t1));
        assertTrue(list.getAll().contains(t2));
    }
}
