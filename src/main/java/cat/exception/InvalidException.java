package cat.exception;

/**
 * Thrown when a user input does not match any valid command.
 * Example: <code>blah</code> would trigger an <code>InvalidException</code>.
 */
public class InvalidException extends Exception {
    /**
     * Creates a new invalid exception with the given message.
     * @param message error message to display
     */
    public InvalidException(String message) {
        super(message);
    }
}
