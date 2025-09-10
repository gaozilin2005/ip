package cat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import cat.exception.EmptyException;
import cat.exception.InvalidException;
import cat.task.Task;
import cat.task.TaskList;
import cat.ui.Ui;

/**
 * Main application class for the Cat task manager.
 * A <code>Cat</code> object coordinates input parsing,
 * task list updates, and saving to storage.
 */
public class Cat {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    /**
     * Creates a Cat application with storage file <code>./data/duke.txt</code>.
     * Loads tasks from storage if available, otherwise starts with an empty list.
     */
    public Cat() {
        ui = new Ui();
        storage = new Storage("./data/duke.txt");
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList(new ArrayList<>());
        }

        assert ui != null : "UI must be initialized";
        assert storage != null : "Storage must be initialized";
        assert tasks != null : "Task list must be initialized";
    }

    /**
     * Returns the greeting shown at application startup.
     *
     * @return Greeting text for display.
     */
    public String greeting() {
        return "Hello :) I'm Cat\nWhat can I do for you?\n";
    }

    /**
     * Returns the goodbye message shown when the user exits.
     *
     * @return Goodbye text for display.
     */
    public String goodbye() {
        return "Bye. Hope to see you again soon!\n";
    }

    /**
     * Processes a single user input line and returns the message to display.
     * <p>
     * This method is UI-agnostic and does not perform any printing. It mutates
     * internal state as needed (e.g., adding/removing tasks) and persists to storage.
     *
     * @param input Raw user input (e.g., {@code "todo read book"}, {@code "list"}).
     * @return A formatted message describing the outcome of the command.
     */
    public String respond(String input) {
        if ("bye".equals(input)) {
            String msg = goodbye();
            assert msg != null : "Goodbye message cannot be null";
            return msg;
        }
        try {
            String output;
            if (input.equals("list")) {
                output = tasks.formatList();
                assert output != null : "List output must not be null";
                storage.save(tasks);
                return output;
            } else if (input.matches("^mark .+$")) {
                String[] parts = input.split(" ");
                int taskNum = Integer.parseInt(parts[1]) - 1;
                output = tasks.markDone(taskNum);
                assert output != null : "Mark output must not be null";
                storage.save(tasks);
                return output;
            } else if (input.matches("^unmark .+$")) {
                String[] parts = input.split(" ");
                int taskNum = Integer.parseInt(parts[1]) - 1;
                output = tasks.unmarkDone(taskNum);
                assert output != null : "Unmark output must not be null";
                storage.save(tasks);
                return output;
            } else if (input.startsWith("delete")) {
                String[] parts = input.split("delete ");
                int taskNum = Integer.parseInt(parts[1]) - 1;
                output = tasks.delete(taskNum);
                assert output != null : "Delete output must not be null";
                storage.save(tasks);
                return output;
            } else if (input.startsWith("due")) {
                String[] parts = input.split("due ");
                LocalDate date = LocalDate.parse(parts[1]);
                output = tasks.dueOnDate(date);
                assert output != null : "Due output must not be null";
                storage.save(tasks);
                return output;
            } else if (input.startsWith("find")) {
                String[] parts = input.split("find ");
                String keyword = parts[1];
                output = tasks.search(keyword);
                assert output != null : "Find output must not be null";
                storage.save(tasks);
                return output;
            } else {
                try {
                    Task task = Parser.parseTask(input);
                    assert task != null : "Parsed task must not be null";
                    output = tasks.add(task);
                    assert output != null : "Task output must not be null";
                    storage.save(tasks);
                    return output;
                } catch (EmptyException | InvalidException e) {
                    return e.getMessage();
                }
            }
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            return "Invalid date format! Please input date in yyyy-mm-dd.";
        } catch (IOException e) {
            return "OOPS!!! Could not save tasks to file: " + e.getMessage();
        }
    }

}
