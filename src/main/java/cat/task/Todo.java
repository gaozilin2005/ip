package cat.task;

/**
 * Represents a todo task.
 * A <code>Todo</code> has only a description and a done/undone status.
 */
public class Todo extends Task {

    /**
     * Creates a new todo task.
     * @param description task description
     * @param isDone whether the task is already completed
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns the string representation of this todo for display.
     * Example: <code>[T] [ ] read book</code>
     * @return formatted string
     */
    @Override
    public String toString() {
        return "[T] " + super.toString();
    }

    /**
     * Returns the string format used to save this todo to storage.
     * Example: <code>T | 0 | read book</code>
     * @return save format string
     */
    @Override
    public String toSaveFormat() {
        return "T | " + getStatusIcon() + " | " + description;
    }
}
