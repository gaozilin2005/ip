package cat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

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
    private static final String DEFAULT_STORAGE_PATH = "./data/duke.txt";
    private static final String DATE_ERROR_MESSAGE = "Invalid date format! Please input date in yyyy-mm-dd.";
    private static final String SAVE_ERROR_PREFIX = "OOPS!!! Could not save tasks to file: ";
    private static final int USER_INDEX_OFFSET = 1;

    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    /**
     * Creates a Cat application with storage file <code>./data/duke.txt</code>.
     * Loads tasks from storage if available, otherwise starts with an empty list.
     */
    public Cat() {
        ui = new Ui();
        storage = new Storage(DEFAULT_STORAGE_PATH);
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
            if (input.equals("list")) {
                return handleList(input);
            } else if (input.startsWith("mark")) {
                return handleMark(input);
            } else if (input.startsWith("unmark")) {
                return handleUnmark(input);
            } else if (input.startsWith("delete")) {
                return handleDelete(input);
            } else if (input.startsWith("due")) {
                return handleDue(input);
            } else if (input.startsWith("find")) {
                return handleFind(input);
            } else {
                try {
                   return handleTask(input);
                } catch (EmptyException | InvalidException e) {
                    return e.getMessage();
                }
            }
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            return DATE_ERROR_MESSAGE;
        } catch (IOException e) {
            return SAVE_ERROR_PREFIX + e.getMessage();
        }
    }

    private String handleList(String input) throws IOException {
        String output = tasks.formatList();
        assert output != null : "List output must not be empty";
        storage.save(tasks);
        return output;
    }

    private String handleMark(String input) throws IOException {
        int taskNum = getTaskNum(input);
        String output = tasks.markDone(taskNum);
        assert output != null : "Mark output must not be empty";
        storage.save(tasks);
        return output;
    }

    private String handleUnmark(String input) throws IOException {
        int taskNum = getTaskNum(input);
        String output = tasks.unmarkDone(taskNum);
        assert output != null : "Unmark output must not be empty";
        storage.save(tasks);
        return output;
    }

    private int getTaskNum(String input) {
        String[] parts = input.split(" ");
        return Integer.parseInt(parts[1]) - USER_INDEX_OFFSET;
    }

    private String handleDelete(String input) throws IOException {
        int taskNum = getTaskNum(input);
        String output = tasks.delete(taskNum);
        assert output != null : "Delete output must not be empty";
        storage.save(tasks);
        return output;
    }

    private String handleDue(String input) throws IOException {
        String[] parts = input.split("due ");
        LocalDate date = LocalDate.parse(parts[1]);
        String output = tasks.dueOnDate(date);
        assert output != null : "Due output must not be empty";
        storage.save(tasks);
        return output;
    }

    private String handleFind(String input) throws IOException {
        String[] parts = input.split("find ");
        String keyword = parts[1];
        String output = tasks.search(keyword);
        assert output != null : "Find output must not be empty";
        storage.save(tasks);
        return output;
    }

    private String handleTask(String input)
            throws EmptyException, InvalidException, IOException {
        Task task = Parser.parseTask(input);
        assert task != null : "Parsed task must not be null";
        String output = tasks.add(task);
        assert output != null : "Task output must not be empty";
        storage.save(tasks);
        return output;
    }
}
