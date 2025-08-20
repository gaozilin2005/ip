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
            tasks[curr] = new Task(input);
            curr++;
            System.out.println("____________________________________________________________\n");
            System.out.println("added: " + input);
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
        System.out.println("____________________________________________________________\n");
        for (int i = 0; i < curr; i++) {
            System.out.println(i + 1 + ". [" + tasks[i].getStatusIcon() + "] " + tasks[i]);
        }
        System.out.println("____________________________________________________________\n");
    }
}
