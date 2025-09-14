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

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }

    @Override
    public String toSaveFormat() {
        return "T | " + getStatusIcon() + " | " + description;
    }
}
