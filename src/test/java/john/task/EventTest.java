package john.task;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class EventTest {
    @Test
    void toString_and_toDataString() {
        LocalDateTime start = LocalDateTime.of(2026, 2, 11, 23, 59);
        LocalDateTime end = LocalDateTime.of(2026, 11, 2, 0, 0);
        Event event = new Event("project meeting", start, end);
        assertTrue(event.toString().contains("[E] [ ] project meeting"));
        assertTrue(event.toString().contains("11 Feb 2026"));
        assertTrue(event.toString().contains("2 Nov 2026"));
        assertTrue(event.toDataString().startsWith("E | 0 |"));
    }

    @Test
    void fromDataString_valid() {
        LocalDateTime start = LocalDateTime.of(2026, 2, 11, 23, 59);
        LocalDateTime end = LocalDateTime.of(2026, 11, 2, 0, 0);
        String data = String.format("E | 1 | project meeting | %s | %s", start.toString(), end.toString());
        Event event = Event.fromDataString(data);
        assertTrue(event.toString().contains("[E] [X] project meeting"));
        assertTrue(event.toString().contains("11 Feb 2026"));
        assertTrue(event.toString().contains("2 Nov 2026"));
    }

    @Test
    void fromDataString_invalidType_throws() {
        String s = "X | 0 | something | 2026-02-11T23:59 | 2026-11-02T00:00";
        Exception e = assertThrows(john.JohnException.class, () -> Event.fromDataString(s));
        assertTrue(e.getMessage().contains("Data string is not of type Event"));
    }
}
