package cat.task;

/**
 * Represents an event task.
 * An <code>Event</code> has a description, a start time, an end time,
 * and a done/undone status.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates an event task.
     * @param description task description
     * @param from start time or date
     * @param to end time or date
     * @param isDone whether the task is completed
     */
    public Event(String description, String from, String to, boolean isDone) {
        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String toSaveFormat() {
        return "D | " + getStatusIcon() + " | " + description + " | " + from + " to " + to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        } else {
            Event other = (Event) o;
            return this.description.equals(other.getDescription())
                    && this.getFrom().equals(other.getFrom())
                    && this.getTo().equals(other.getTo());
        }
    }

    /**
     * Returns the start time/date of the event.
     * @return event start
     */
    public String getFrom() {
        return this.from;
    }

    /**
     * Returns the end time/date of the event.
     * @return event end
     */
    public String getTo() {
        return this.to;
    }
}
