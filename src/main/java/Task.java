public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markDone() {
        this.isDone = true;
        System.out.println("____________________________________________________________\n" +
                "Nice! I've marked this task as done: \n" + this +
                "\n____________________________________________________________");
    }

    public void unmarkDone() {
        this.isDone = false;
        System.out.println("____________________________________________________________\n" +
                "OK, I've marked this task as not done yet: \n" + this +
                "\n____________________________________________________________");
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    public abstract String toSaveFormat();
}
