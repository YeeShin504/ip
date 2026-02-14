package john.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import john.JohnException;

/**
 * Utility class for validating and parsing date/time strings.
 */
public class DateTimeValidator {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/uuuu HHmm")
            .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Parses a date string into a LocalDateTime object.
     *
     * @param dateString The date string to parse
     * @param dateType   The type of date (e.g., "start", "end", "deadline") for error messages
     * @return The parsed LocalDateTime
     * @throws JohnException if the date string is invalid
     */
    public static LocalDateTime parseDateTime(String dateString, String dateType) {
        try {
            return LocalDateTime.parse(dateString, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new JohnException(buildDateErrorMessage(dateString, dateType, e));
        }
    }

    /**
     * Validates that the end date is after the start date.
     *
     * @param startDate The start date
     * @param endDate   The end date
     * @throws JohnException if end date is before or equal to start date
     */
    public static void validateDateOrder(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new JohnException("The end date of an event cannot be before the start date.");
        }
        if (endDate.isEqual(startDate)) {
            throw new JohnException("The end date of an event cannot be the same as the start date. "
                    + "Please provide different start and end times.");
        }
    }

    /**
     * Validates that a date string is not empty.
     *
     * @param dateString The date string to validate
     * @param dateType   The type of date for error messages
     * @throws JohnException if the date string is empty
     */
    public static void validateDateNotEmpty(String dateString, String dateType) {
        if (dateString == null || dateString.trim().isEmpty()) {
            throw new JohnException("The " + dateType + " date cannot be empty. "
                    + "Please provide a date in format: d/M/yyyy HHmm");
        }
    }

    private static String buildDateErrorMessage(String dateString, String dateType, DateTimeParseException e) {
        String errorMsg = "I must inform you that the " + dateType + " date format is invalid. ";
        if (e.getMessage().contains("Invalid date")) {
            errorMsg += "The date '" + dateString + "' does not exist (e.g., February 30th is invalid). ";
        } else {
            errorMsg += "Please use the format: d/M/yyyy HHmm (e.g., 25/12/2024 1800). ";
        }
        errorMsg += "Error: " + e.getMessage();
        return errorMsg;
    }
}
