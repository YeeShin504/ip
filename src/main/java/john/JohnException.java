package john;

/**
 * Custom exception class for John application errors.
 */
public class JohnException extends IllegalArgumentException {
    /**
     * Constructs a new JohnException with the specified detail message.
     *
     * @param message the detail message
     */
    public JohnException(String message) {
        super(message);
    }

    /**
     * Retrieves the error message with a custom prefix.
     *
     * @return the error message
     */
    @Override
    public String getMessage() {
        return "OOPS!!! " + super.getMessage();
    }
}
