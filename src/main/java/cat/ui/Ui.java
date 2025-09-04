package cat.ui;

/**
 * Handles all user interface interactions.
 * A <code>Ui</code> prints greetings, error messages, and separators to the console.
 */
public class Ui {

    /**
     * Creates a new Ui object.
     */
    public Ui() {
    }

    /**
     * Prints the goodbye message shown when the program ends.
     */
    public void printGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        this.printLine();
    }

    /**
     * Prints a horizontal line separator.
     */
    public void printLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints an error message shown when tasks cannot be loaded from storage.
     */
    public void showLoadingError() {
        System.out.println("OOPS!!! Could not load tasks.");
    }
}
