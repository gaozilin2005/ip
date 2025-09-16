package cat.exception;
/**
 * Thrown when a user specifies a task index that is not valid
 * (e.g., index out of range, negative index, or non-existent task).
 */
public class InvalidTaskIndexException extends Exception {

    /**
     * Constructs a new {@code InvalidTaskIndexException} with the specified detail message.
     *
     * @param message detail message giving more information about the error
     */
    public InvalidTaskIndexException(String message) {
        super(message);
    }
}