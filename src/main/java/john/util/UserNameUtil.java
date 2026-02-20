package john.util;

/**
 * Utility class for retrieving the current system username.
 *
 * This class provides a method to get the current user's name from the system properties or environment variables.
 * If unavailable, it returns "Sir/Mdm" as a fallback.
 */
public class UserNameUtil {
    /**
    * Returns the current system username, or "Sir/Mdm" if unavailable.
    */
    public static String getUserName() {
        String name = System.getProperty("user.name");
        if (name == null || name.isBlank()) {
            name = System.getenv("USERNAME");
        }
        return (name == null || name.isBlank()) ? "Sir/Mdm" : name;
    }
}
