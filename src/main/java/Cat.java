import java.util.Scanner;

public class Cat {
    private Scanner scanner;
    private String[] tasks;
    private int curr;

    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.printGreeting();
        cat.getInput();
    }

    public Cat() {
        scanner = new Scanner(System.in);
        tasks = new String[100];
        curr = 0;
    }

    public void getInput() {
        String input = this.scanner.nextLine();
        while (!input.equals("bye") && !input.equals("list")){
            tasks[curr] = input;
            curr++;
            System.out.println("____________________________________________________________\n");
            System.out.println("added: " + input);
            System.out.println("____________________________________________________________\n");
            input = scanner.nextLine();
        }
        if (input.equals("bye")) {
            this.printGoodbye();
        } else if (input.equals("list")) {
            this.printList(tasks, curr);
            this.getInput();
        }
    }

    public void printGreeting() {
        System.out.println("____________________________________________________________\n");
        System.out.println(" Hello :) I'm Cat\n");
        System.out.print(" What can I do for you?\n");
        System.out.println("____________________________________________________________\n");
    }

    public void printGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!\n");
        System.out.println("____________________________________________________________");
        scanner.close();
    }

    public void printList(String[] tasks, int curr) {
        System.out.println("____________________________________________________________\n");
        for (int i = 0; i < curr; i++) {
            System.out.println(i + 1 + ". " + tasks[i]);
        }
        System.out.println("____________________________________________________________\n");
    }
}
