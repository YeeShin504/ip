package john.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import john.JohnException;

class TaskListTest {
    @Test
    void addTask_shouldBeRetrievableAndRemovable() {
        TaskList list = new TaskList();
        Todo todo = new Todo("read book");
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
        Todo t1 = new Todo("a");
        Todo t2 = new Todo("b");
        TaskList list = new TaskList();
        list.add(t1);
        list.add(t2);
        assertEquals(2, list.getAll().size());
        assertTrue(list.getAll().contains(t1));
        assertTrue(list.getAll().contains(t2));
    }
}
