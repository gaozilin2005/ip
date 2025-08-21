import java.util.Scanner;

public class Cat {
    private Scanner scanner;
    private Task[] tasks;
    private int curr;

    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.printGreeting();
        cat.getInput(cat.scanner.nextLine());
    }

    public Cat() {
        scanner = new Scanner(System.in);
        tasks = new Task[100];
        curr = 0;
    }

    public void getInput(String input) {
        if (input.equals("bye")) {
            this.printGoodbye();
        } else if (input.equals("list")) {
            this.printList(tasks, curr);
            this.getInput(this.scanner.nextLine());
        } else if (input.matches("^mark .+$")) {
            String[] parts = input.split(" ");
            int taskNum = Integer.parseInt(parts[1]) - 1;
            tasks[taskNum].markDone();
            this.getInput(this.scanner.nextLine());
        } else if (input.matches("^unmark .+$")) {
            String[] parts = input.split(" ");
            int taskNum = Integer.parseInt(parts[1]) - 1;
            tasks[taskNum].unmarkDone();
            this.getInput(this.scanner.nextLine());
        } else {
            Task task = null;
            if (input.matches("^deadline .+$")) {
                String[] parts = input.split(" /by ");
                String[] parts2 = parts[0].split("deadline ");
                task = new Deadline(parts2[1], parts[1]);
            } else if (input.matches ("^todo .+$")) {
                String[] parts = input.split("todo ");
                task = new Todo(parts[1]);
            } else if (input.matches("^event .+$")) {
                String[] parts = input.split("event ");
                String[] parts2 = parts[1].split(" /from ");
                String[] parts3 = parts2[1].split(" /to ");
                task = new Event(parts2[0], parts3[0], parts3[1]);
            }
            tasks[curr] = task;
            curr++;
            System.out.println("____________________________________________________________\n");
            System.out.println("Got it. I've added this task: \n" + tasks[curr - 1]);
            System.out.println("Now you have " + curr + " tasks in the list.");
            System.out.println("____________________________________________________________\n");
            this.getInput(this.scanner.nextLine());
        }

    }

    public void printGreeting() {
        System.out.println("____________________________________________________________\n");
        System.out.println(" Hello :) I'm Cat\n");
        System.out.print(" What can I do for you?\n");
        System.out.println("____________________________________________________________\n");
    }

    public void printGoodbye() {
        System.out.println("Bye. Hope to see you again soon!\n");
        System.out.println("____________________________________________________________");
        scanner.close();
    }

    public void printList(Task[] tasks, int curr) {
        System.out.println("____________________________________________________________\n" +
                "Here are the tasks in your list: ");
        for (int i = 0; i < curr; i++) {
            System.out.println(i + 1 + ". " + tasks[i]);
        }
        System.out.println("____________________________________________________________\n");
    }
}
