package john.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaskTest {
    @Test
    void markComplete_and_markIncomplete() {
        Todo todo = new Todo("read book");
        assertFalse(todo.toString().contains("[X]"));
        todo.markComplete();
        assertTrue(todo.toString().contains("[X]"));
        todo.markIncomplete();
        assertFalse(todo.toString().contains("[X]"));
    }

    @Test
    void fromDataString_invalidType_throws() {
        Exception e = assertThrows(john.JohnException.class, () -> Task.fromDataString("X | 0 | something"));
        assertTrue(e.getMessage().contains("Unknown task type in data string"));
    }
}
