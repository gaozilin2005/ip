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
     * Prints an error message shown when tasks cannot be loaded from storage.
     */
    public void showLoadingError() {
        System.out.println("OOPS!!! Could not load tasks.");
    }
}
