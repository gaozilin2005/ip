import java.util.Scanner;
import java.util.ArrayList;

public class Cat {
    private Scanner scanner;
    private ArrayList<Task> tasks;

    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.printGreeting();
        cat.run();
    }

    public Cat() {
        scanner = new Scanner(System.in);
        tasks = new ArrayList<>();
    }

    public void run() {
        String input;
        while (!(input = scanner.nextLine()).equals("bye")) {
            handleInput(input);
        }
        printGoodbye();
    }

    public void handleInput(String input) {
        try {
            if (input.equals("bye")) {
                this.printGoodbye();
            } else if (input.equals("list")) {
                this.printList(tasks);
            } else if (input.matches("^mark .+$")) {
                String[] parts = input.split(" ");
                int taskNum = Integer.parseInt(parts[1]) - 1;
                tasks.get(taskNum).markDone();
            } else if (input.matches("^unmark .+$")) {
                String[] parts = input.split(" ");
                int taskNum = Integer.parseInt(parts[1]) - 1;
                tasks.get(taskNum).unmarkDone();
            } else if (input.startsWith("delete")) {
                String[] parts = input.split("delete ");
                int taskNum = Integer.parseInt(parts[1]) - 1;
                this.printLine();
                System.out.println("Noted. I've removed this task: \n" +
                        tasks.get(taskNum) + "\n Now you have " + (tasks.size() - 1) +
                        " tasks in the list.");
                this.printLine();
                tasks.remove(taskNum);
            } else {
                Task task = null;
                if (input.startsWith("deadline")) {
                    if (input.matches("deadline|deadline ")) {
                        throw new EmptyException(
                                "OOPS!!! The description of a deadline cannot be empty.");
                    }
                    String[] parts = input.split(" /by ");
                    String[] parts2 = parts[0].split("deadline ");
                    task = new Deadline(parts2[1], parts[1]);
                } else if (input.startsWith("todo")) {
                    if (input.matches("todo|todo ")) {
                        throw new EmptyException(
                                "OOPS!!! The description of a todo cannot be empty.");
                    }
                    String[] parts = input.split("todo ");
                    task = new Todo(parts[1]);
                } else if (input.startsWith("event")) {
                    if (input.matches("todo|todo ")) {
                        throw new EmptyException(
                                "OOPS!!! The description of a event cannot be empty.");
                    }
                    String[] parts = input.split("event ");
                    String[] parts2 = parts[1].split(" /from ");
                    String[] parts3 = parts2[1].split(" /to ");
                    task = new Event(parts2[0], parts3[0], parts3[1]);
                } else {
                    throw new InvalidException(
                            "OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
                tasks.add(task);
                this.printLine();
                System.out.println("Got it. I've added this task: \n" + task);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                this.printLine();
            }
        } catch (EmptyException e) {
            this.printLine();
            System.out.println(e.getMessage());
            this.printLine();
        } catch (InvalidException e) {
            this.printLine();
            System.out.println(e.getMessage());
            this.printLine();
        }
    }

    public void printGreeting() {
        this.printLine();
        System.out.println(" Hello :) I'm Cat");
        System.out.print(" What can I do for you?\n");
        this.printLine();
    }

    public void printGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        this.printLine();
        scanner.close();
    }

    public void printList(ArrayList<Task> tasks) {
        this.printLine();
        System.out.println("Here are the tasks in your list: ");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i + 1 + ". " + tasks.get(i));
        }
        this.printLine();
    }

    public void printLine() {
        System.out.println("____________________________________________________________");
    }
}
