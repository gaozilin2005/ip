public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markDone() {
        System.out.println("____________________________________________________________\n" +
                "Nice! I've marked this task as done: \n" +
                "[X] " + this +
                "\n____________________________________________________________\n");
        this.isDone = true;
    }

    public void unmarkDone() {
        System.out.println("____________________________________________________________\n" +
                "OK, I've marked this task as not done yet: \n" +
                "[ ] " + this +
                "\n____________________________________________________________\n");
        this.isDone = false;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
