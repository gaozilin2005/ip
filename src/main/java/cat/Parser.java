package cat;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

import cat.exception.EmptyException;
import cat.exception.InvalidException;
import cat.task.Deadline;
import cat.task.Event;
import cat.task.Task;
import cat.task.Todo;

/**
 * Parses user input strings into {@link Task} objects.
 * A <code>Parser</code> can recognize commands such as
 * <code>todo homework</code>, <code>deadline return book /by 2025-03-24</code>,
 * or <code>event project /from 2025-03-01 /to 2025-03-05</code>.
 */
public class Parser {
    public Parser() {
        /* Utility class; do not instantiate. */
    }

    private static final Map<String, String> ALIASES = new HashMap<>();
    static {
        //Define aliases for each command
        ALIASES.put("dl", "deadline");
        ALIASES.put("due", "deadline");
        ALIASES.put("d", "deadline");

        ALIASES.put("td", "todo");
        ALIASES.put("t", "todo");

        ALIASES.put("ev", "event");
        ALIASES.put("e", "event");
    }

    /**
     * Parses a task from a user input string.
     * @param input user input, e.g. <code>todo homework</code>
     * @return the parsed {@link Task}
     * @throws EmptyException if the task description is empty
     * @throws InvalidException if the input does not match any known task type
     */
    public static Task parseTask(String input) throws EmptyException, InvalidException {
        assert input != null : "input must not be null";
        Task task = null;

        String[] parts = input.split(" ", 2);
        String command = normalizeAlias(parts[0]);

        if (!(input.startsWith("deadline") | input.startsWith("todo") | input.startsWith("event"))) {
            throw new InvalidException("oops i don't know what that means :(");
        }

        if (parts.length == 1) {
            throw new EmptyException(
                    "OOPS!!! The description of a task cannot be empty.");
        }

        if (command.equals("deadline")) {
            task = parseDeadline(parts[1]);
        } else if (command.equals("todo")) {
            task = parseTodo(parts[1]);
        } else if (command.equals("event")) {
            task = parseEvent(parts[1]);
        } else {
            throw new InvalidException(
                    "OOPS!!! I'm sorry, but I don't know what that means :-( \n");
        }
        return task;
    }

    /**
     * Adds a new alias mapping for a command keyword.
     * <p>
     * Expected input format: {@code "alias <alias> <canonical>"}.
     * For example, {@code "alias dl deadline"} will add {@code dl}
     * as an alias for the {@code deadline} command.
     * </p>
     *
     * @param both the full user input string containing the alias
     *             definition in the format {@code alias <alias> <canonical>}
     * @return confirmation message showing the alias and its canonical command
     * @throws ArrayIndexOutOfBoundsException if the input does not contain
     *                                        enough parts
     */
    public static String addAlias(String both) {
        String[] parts = both.split(" ");
        String canon = parts[2];
        String alias = parts[1];
        ALIASES.put(alias, canon);
        return "Nice! " + alias + " added as an alias for " + canon;
    }

    /**
     * Normalizes an input command keyword to its canonical form.
     * If the keyword is an alias, return the mapped canonical command.
     * Otherwise, return the original word.
     *
     * @param keyword the first word of the input
     * @return canonical command word (e.g., "deadline", "todo", "event")
     */
    public static String normalizeAlias(String keyword) {
        return ALIASES.getOrDefault(keyword.toLowerCase(), keyword.toLowerCase());
    }

    /**
     * Parses a deadline task from input.
     * Format: <code>deadline [description] /by yyyy-mm-dd</code>
     * @param input user input string
     * @return a {@link Deadline} task
     * @throws EmptyException if the description or date is missing
     */
    public static Task parseDeadline(String input) throws EmptyException {
        String[] parts = input.split(" /by ");
        if (parts.length == 1) {
            throw new EmptyException(
                    "OOPS!!! A deadline should follow the format \"deadline [task] /by [date in yyyy-mm-dd]");
        }

        LocalDate by = null;
        while (by == null) {
            by = LocalDate.parse(parts[1]);
        }
        return new Deadline(parts[0], by, false);
    }

    /**
     * Parses a todo task from input.
     * Format: <code>todo [description]</code>
     * @param input user input string
     * @return a {@link Todo} task
     * @throws EmptyException if the description is missing
     */
    public static Task parseTodo(String input) throws EmptyException {
        return new Todo(input, false);
    }

    /**
     * Parses an event task from input.
     * Format: <code>event [description] /from [start] /to [end]</code>
     * @param input user input string
     * @return an {@link Event} task
     * @throws EmptyException if the description, start, or end is missing
     */
    public static Task parseEvent(String input) throws EmptyException {
        String[] parts = input.split(" /from ");
        if (parts.length == 1) {
            throw new EmptyException(
                    "OOPS!!! A event should follow the format \"event [event] /from [date] /to [date]");
        }
        String[] parts2 = parts[1].split(" /to ");
        if (parts2.length == 1) {
            throw new EmptyException(
                    "OOPS!!! A event should follow the format \"event [event] /from [date] /to [date]");
        }
        return new Event(parts[0], parts2[0], parts2[1], false);
    }
}
