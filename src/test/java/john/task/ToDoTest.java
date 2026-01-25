package john.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ToDoTest {
    @Test
    void toString_and_toDataString() {
        ToDo todo = new ToDo("read book");
        assertEquals("[T] [ ] read book", todo.toString());
        assertEquals("T | 0 | read book\n", todo.toDataString());
    }

    @Test
    void fromDataString_valid() {
        ToDo todo = ToDo.fromDataString("T | 1 | read book");
        assertEquals("[T] [X] read book", todo.toString());
        assertTrue(todo.toDataString().startsWith("T | 1 |"));
    }

    @Test
    void fromDataString_invalidType_throws() {
        Exception e = assertThrows(john.JohnException.class, () -> ToDo.fromDataString("X | 0 | something"));
        assertTrue(e.getMessage().contains("Data string is not of type ToDo"));
    }
}
