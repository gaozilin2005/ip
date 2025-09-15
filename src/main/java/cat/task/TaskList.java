package cat.task;

import static java.util.stream.Collectors.joining;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Represents a list of tasks.
 * A <code>TaskList</code> stores and manages multiple {@link Task} objects.
 */
public class TaskList {
    private ArrayList<Task> ls;

    /**
     * Creates a task list with the given tasks.
     * @param ls list of tasks
     */
    public TaskList(ArrayList<Task> ls) {
        this.ls = ls;
    }

    /**
     * Formats all tasks in the list with their index.
     */
    public String formatList() {
        if (ls.isEmpty()) {
            return "oops no tasks yet";
        }
        String output = "here are the tasks in your list! \n";
        output += IntStream.range(0, ls.size())
                .mapToObj(i -> String.format("%d. %s", i + 1, ls.get(i)))
                .collect(joining("\n"));
        return output;
    }

    /**
     * Marks a task as done.
     * @param taskNum index of task in list (0-based)
     */
    public String markDone(int taskNum) {
        return this.ls.get(taskNum).markDone();
    }

    /**
     * Marks a task as not done.
     * @param taskNum index of task in list (0-based)
     */
    public String unmarkDone(int taskNum) {
        return this.ls.get(taskNum).unmarkDone();
    }

    /**
     * Deletes a task from the list and prints a message.
     * @param taskNum index of task in list (0-based)
     */
    public String delete(int taskNum) {
        Task removed = ls.get(taskNum);
        ls.remove(taskNum);
        return ("okayy i've removed this task: \n"
                + removed + "\n now you have " + ls.size() + " tasks in the list.\n");
    }

    /**
     * Adds a task to the list and prints a message.
     * @param task the task to add
     */
    public String add(Task task) {
        ls.add(task);
        return "got it!! i've added this task: \n" + task
                + "\nnow you have " + ls.size() + " tasks in the list.\n";
    }

    /**
     * Prints all deadline tasks due on the given date.
     * @param date date to filter deadlines
     */
    public String dueOnDate(LocalDate date) {
        var due = ls.stream()
                .filter(t -> t instanceof Deadline && ((Deadline) t).dueOn(date))
                .toList();
        if (due.isEmpty()) {
            return "yay no tasks due on " + date;
        }
        return due.stream().map(Task::toString).collect(joining("\n"));
    }

    /**
     * Searches for tasks whose descriptions contain the given keyword.
     * If matching tasks are found, prints them with their details.
     * Otherwise, prints a message saying no tasks were found.
     *
     * @param keyword the word or phrase to search for, e.g. <code>"book"</code>
     */
    public String search(String keyword) {
        ArrayList<Task> found = new ArrayList<>();

        for (Task task : this.ls) {
            if (task.getDescription().contains(keyword)) {
                found.add(task);
            }
        }

        if (found.size() != 0) {
            String output = "here are the matching tasks in your list:\n";
            int counter = 1;
            for (Task task : found) {
                output += counter + ". " + task.toString();
                counter++;
            }
            return output;
        } else {
            return "uhoh no tasks found :(\n";
        }
    }

    /**
     * Returns the underlying list of tasks managed by this {@code TaskList}.
     * <p>
     * The returned {@link ArrayList} is the actual internal storage, not a copy.
     * Modifications to this list will directly affect the {@code TaskList}.
     * </p>
     *
     * @return the list of {@link Task} objects currently stored in this task list
     */
    public ArrayList<Task> getTasks() {
        return this.ls;
    }
}
