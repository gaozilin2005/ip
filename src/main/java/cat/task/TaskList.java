package cat.task;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> ls;

    public TaskList(ArrayList<Task> ls) {
        this.ls = ls;
    }

    public void printList() {
        this.printLine();
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < ls.size(); i++) {
            System.out.println(i + 1 + ". " + ls.get(i));
        }
        this.printLine();
    }

    public void markDone(int taskNum) {
        this.ls.get(taskNum).markDone();
    }

    public void unmarkDone(int taskNum) {
        this.ls.get(taskNum).unmarkDone();
    }

    public void delete(int taskNum) {
        this.printLine();
        System.out.println("Noted. I've removed this task: \n" +
                ls.get(taskNum) + "\n Now you have " + (ls.size() - 1) +
                " tasks in the list.");
        this.printLine();
    }

    public void add(Task task) {
        ls.add(task);
        this.printLine();
        System.out.println("Got it. I've added this task: \n" + task);
        System.out.println("Now you have " + ls.size() + " tasks in the list.");
        this.printLine();
    }

    public void printDueOnDate(LocalDate date) {
        for (Task task : ls) {
            if (task instanceof Deadline && ((Deadline) task).dueOn(date)) {
                System.out.println(task);
            }
        }
        this.printLine();
    }

    public ArrayList<Task> getTasks() {
        return this.ls;
    }

    public void printLine() {
        System.out.println("____________________________________________________________");
    }
}
