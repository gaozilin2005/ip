package cat.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task.
 * A <code>Deadline</code> has a description, a due date,
 * and a done/undone status.
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Creates a deadline task.
     * @param description task description
     * @param by due date
     * @param isDone whether the task is completed
     */
    public Deadline(String description, LocalDate by, boolean isDone) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D] " + super.toString() + " (by: " + by.format(formatter) + ")";
    }

    @Override
    public String toSaveFormat() {
        return "D | " + getStatusIcon() + " | " + description + " | " + by;
    }

    /**
     * Checks if this deadline is due on the given date.
     * @param date date to check
     * @return true if due on the given date
     */
    public boolean dueOn(LocalDate date) {
        return date.equals(by);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deadline)) {
            return false;
        } else {
            Deadline other = (Deadline) o;
            return this.description.equals(other.getDescription())
                    && this.getBy().equals(other.getBy());
        }
    }

    /**
     * Returns the due date of the deadline.
     * @return due date
     */
    public LocalDate getBy() {
        return this.by;
    }
}
