package cat;

import javafx.application.Application;

/**
 * Launcher class for the Cat task manager.
 * <p>
 * Acts as the true entry point of the program to avoid issues
 * with directly running a class that extends {@link Application}.
 * <p>
 * Delegates to {@link Main} which initializes the JavaFX UI.
 */
public class Launcher {
    /**
     * Entry point of the application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
