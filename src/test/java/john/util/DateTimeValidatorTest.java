package john.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import john.JohnException;

/**
 * Tests for DateTimeValidator utility class.
 */
public class DateTimeValidatorTest {

    @Test
    public void parseDateTime_validDate_success() {
        LocalDateTime result = DateTimeValidator.parseDateTime("25/12/2024 1800", "deadline");
        assertEquals(LocalDateTime.of(2024, 12, 25, 18, 0), result);
    }

    @Test
    public void parseDateTime_invalidDate_throwsException() {
        JohnException exception = assertThrows(JohnException.class, () -> {
            DateTimeValidator.parseDateTime("30/2/2024 1800", "deadline");
        });
        assertEquals(true, exception.getMessage().contains("deadline date format is invalid"));
    }

    @Test
    public void parseDateTime_invalidFormat_throwsException() {
        JohnException exception = assertThrows(JohnException.class, () -> {
            DateTimeValidator.parseDateTime("invalid date", "start");
        });
        assertEquals(true, exception.getMessage().contains("start date format is invalid"));
    }

    @Test
    public void validateDateOrder_endBeforeStart_throwsException() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 25, 18, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 24, 18, 0);

        JohnException exception = assertThrows(JohnException.class, () -> {
            DateTimeValidator.validateDateOrder(start, end);
        });
        assertEquals(true, exception.getMessage().contains("end date of an event cannot be before the start date"));
    }

    @Test
    public void validateDateOrder_sameDate_throwsException() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 25, 18, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 25, 18, 0);

        JohnException exception = assertThrows(JohnException.class, () -> {
            DateTimeValidator.validateDateOrder(start, end);
        });
        assertEquals(true, exception.getMessage().contains("end date of an event cannot be the same"));
    }

    @Test
    public void validateDateOrder_validOrder_success() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 25, 18, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 26, 18, 0);

        DateTimeValidator.validateDateOrder(start, end); // Should not throw
    }

    @Test
    public void validateDateNotEmpty_emptyString_throwsException() {
        JohnException exception = assertThrows(JohnException.class, () -> {
            DateTimeValidator.validateDateNotEmpty("", "deadline");
        });
        assertEquals(true, exception.getMessage().contains("deadline date cannot be empty"));
    }

    @Test
    public void validateDateNotEmpty_null_throwsException() {
        JohnException exception = assertThrows(JohnException.class, () -> {
            DateTimeValidator.validateDateNotEmpty(null, "start");
        });
        assertEquals(true, exception.getMessage().contains("start date cannot be empty"));
    }

    @Test
    public void validateDateNotEmpty_validString_success() {
        DateTimeValidator.validateDateNotEmpty("25/12/2024 1800", "deadline"); // Should not throw
    }
}
