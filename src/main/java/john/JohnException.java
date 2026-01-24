package john;

public class JohnException extends IllegalArgumentException {
    public JohnException(String message) {
        super(message);
    }

    public String getMessage() {
        return "OOPS!!! " + super.getMessage();
    }
}
