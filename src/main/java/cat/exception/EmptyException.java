package cat.exception;

/**
 * Thrown when a user command is missing a required description or detail.
 * Example: <code>todo</code> without any description triggers an <code>EmptyException</code>.
 */
public class EmptyException extends Exception {
    /**
     * Creates a new empty exception with the given message.
     * @param message error message to display
     */
    public EmptyException(String message) {
        super(message);
    }
}
