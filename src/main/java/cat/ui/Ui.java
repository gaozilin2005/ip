package cat.ui;

public class Ui {
    public Ui() {

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
    }

    public void printLine() {
        System.out.println("____________________________________________________________");
    }

    public void showLoadingError() {
        System.out.println("OOPS!!! Could not load tasks.");
    }
}
