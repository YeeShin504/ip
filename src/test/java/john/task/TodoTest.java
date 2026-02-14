package john.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TodoTest {
    @Test
    void toString_and_toDataString() {
        Todo todo = new Todo("read book");
        assertEquals("[T] [ ] read book", todo.toString());
        assertEquals("T | 0 | read book", todo.toDataString());
    }

    @Test
    void fromDataString_valid() {
        Todo todo = Todo.fromDataString("T | 1 | read book");
        assertEquals("[T] [X] read book", todo.toString());
        assertTrue(todo.toDataString().startsWith("T | 1 |"));
    }

    @Test
    void fromDataString_invalidType_throws() {
        Exception e = assertThrows(john.JohnException.class, () -> Todo.fromDataString("X | 0 | something"));
        assertTrue(e.getMessage().contains("Data string is not of type Todo"));
    }
}
