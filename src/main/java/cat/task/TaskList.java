package cat.task;

import java.time.LocalDate;
import java.util.ArrayList;

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
     * Prints all tasks in the list with their index.
     */
    public void printList() {
        this.printLine();
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < ls.size(); i++) {
            System.out.println(i + 1 + ". " + ls.get(i));
        }
        this.printLine();
    }

    /**
     * Marks a task as done.
     * @param taskNum index of task in list (0-based)
     */
    public void markDone(int taskNum) {
        this.ls.get(taskNum).markDone();
    }

    /**
     * Marks a task as not done.
     * @param taskNum index of task in list (0-based)
     */
    public void unmarkDone(int taskNum) {
        this.ls.get(taskNum).unmarkDone();
    }

    /**
     * Deletes a task from the list and prints a message.
     * @param taskNum index of task in list (0-based)
     */
    public void delete(int taskNum) {
        this.printLine();
        System.out.println("Noted. I've removed this task: \n" +
                ls.get(taskNum) + "\n Now you have " + (ls.size() - 1) +
                " tasks in the list.");
        this.printLine();
    }

    /**
     * Adds a task to the list and prints a message.
     * @param task the task to add
     */
    public void add(Task task) {
        ls.add(task);
        this.printLine();
        System.out.println("Got it. I've added this task: \n" + task);
        System.out.println("Now you have " + ls.size() + " tasks in the list.");
        this.printLine();
    }

    /**
     * Prints all deadline tasks due on the given date.
     * @param date date to filter deadlines
     */
    public void printDueOnDate(LocalDate date) {
        for (Task task : ls) {
            if (task instanceof Deadline && ((Deadline) task).dueOn(date)) {
                System.out.println(task);
            }
        }
        this.printLine();
    }

    /**
     * Searches for tasks whose descriptions contain the given keyword.
     * If matching tasks are found, prints them with their details.
     * Otherwise, prints a message saying no tasks were found.
     *
     * @param keyword the word or phrase to search for, e.g. <code>"book"</code>
     * @return list of tasks
	*/
    public void search(String keyword) {
        ArrayList<Task> found = new ArrayList<>();

        for (Task task : this.ls) {
            if (task.getDescription().contains(keyword)) {
                found.add(task);
            }
        }

        if (found.size() != 0) {
            printLine();
            System.out.println("Here are the matching tasks in your list:");
            int counter = 1;
            for (Task task : found) {
                System.out.println(counter + ". " + task.toString());
                counter++;
            }
            printLine();
        } else {
            printLine();
            System.out.println("No tasks found :(");
            printLine();
        }
    }

    public ArrayList<Task> getTasks() {
        return this.ls;
    }

    /**
     * Returns the description of a task at a given index.
     * @param num index of the task
     * @return description of the task
     */
    public String getDescription(int num) {
        return this.ls.get(num).getDescription();
    }

    /**
     * Returns the number of tasks in the list.
     * @return number of tasks
     */
    public int getSize() {
        return this.ls.size();
    }

    /**
     * Prints a line separator.
     */
    public void printLine() {
        System.out.println("____________________________________________________________");
    }
}
