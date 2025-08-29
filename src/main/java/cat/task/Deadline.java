package cat.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDate by;

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

    public boolean dueOn(LocalDate date) {
        return date.equals(by);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } if (!(o instanceof Deadline)) {
            return false;
        } else {
            Deadline other = (Deadline) o;
            return this.description.equals(other.getDescription())
                    && this.getBy().equals(other.getBy());
        }
    }

    public LocalDate getBy() {
        return this.by;
    }
}
