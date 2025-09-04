package cat.task;

/**
 * Represents a generic task.
 * A <code>Task</code> has a description and a done/undone status.
 * Subclasses such as {@link Todo}, {@link Deadline}, and {@link Event}
 * define their own save formats.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description and status.
     * @param description task description
     * @param isDone whether the task is completed
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon of this task.
     * <code>"X"</code> if done, <code>" "</code> if not done.
     * @return status icon
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as done and returns a confirmation.
     */
    public String markDone() {
        this.isDone = true;
        return ("____________________________________________________________\n"
                + "Nice! I've marked this task as done: \n" + this
                + "\n____________________________________________________________");
    }

    /**
     * Marks the task as not done and prints a confirmation.
     */
    public String unmarkDone() {
        this.isDone = false;
        return ("____________________________________________________________\n"
                + "OK, I've marked this task as not done yet: \n" + this
                + "\n____________________________________________________________");
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns the string format used to save this task to storage.
     * Implemented by subclasses.
     * @return save format string
     */
    public abstract String toSaveFormat();

    /**
     * Returns the description of this task.
     * @return task description
     */
    public String getDescription() {
        return this.description;
    }
}
