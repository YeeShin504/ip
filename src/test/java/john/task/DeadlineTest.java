package john.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeadlineTest {
    @Test
    void toString_and_toDataString() {
        LocalDateTime dt = LocalDateTime.of(2026, 11, 2, 19, 30);
        Deadline deadline = new Deadline("return book", dt);
        assertTrue(deadline.toString().contains("[D] [ ] return book"));
        assertTrue(deadline.toString().contains("2 Nov 2026"));
        assertTrue(deadline.toDataString().startsWith("D | 0 |"));
    }

    @Test
    void fromDataString_valid() {
        LocalDateTime dt = LocalDateTime.of(2026, 11, 2, 19, 30);
        String data = String.format("D | 1 | return book | %s", dt.toString());
        Deadline deadline = Deadline.fromDataString(data);
        assertTrue(deadline.toString().contains("[D] [X] return book"));
        assertTrue(deadline.toString().contains("2 Nov 2026"));
    }

    @Test
    void fromDataString_invalidType_throws() {
        Exception e = assertThrows(john.JohnException.class,
                () -> Deadline.fromDataString("X | 0 | something | 2026-11-02T19:30"));
        assertTrue(e.getMessage().contains("Data string is not of type Deadline"));
    }
}
