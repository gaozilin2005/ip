package cat;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Parser() {
        /* Utility class; do not instantiate. */
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
        // Trim and split into [command, rest]
        String[] parts = input.trim().split("\\s+", 2);
        String command = normalizeAlias(parts[0]); // map aliases (dl→deadline, etc.)

        switch (command) {
        case "deadline":
        case "todo":
        case "event":
            break;
        default:
            throw new InvalidException("oops i don't know what that means :(");
        }

        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new EmptyException("oops the description of a task cannot be empty.");
        }
        String rest = parts[1].trim();

        switch (command) {
        case "deadline":
            return parseDeadline(rest);
        case "todo":
            return parseTodo(rest);
        case "event":
            return parseEvent(rest);
        default:
            throw new InvalidException("oops i don't know what that means :(");
        }
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
    public static Task parseDeadline(String input) throws EmptyException, InvalidException {
        if (input == null || input.isBlank()) {
            throw new EmptyException("OOPS!!! Provide a description and /by <yyyy-mm-dd>.");
        }
        // Split by "/by" once; allow extra spaces around it
        String[] split = input.split("\\s*/by\\s*", -1);
        if (split.length != 2) {
            throw new EmptyException("OOPS!!! Use: deadline <desc> /by <yyyy-mm-dd>.");
        }
        String desc = split[0].trim();
        String dateStr = split[1].trim();

        if (desc.isEmpty()) {
            throw new EmptyException("OOPS!!! Deadline description is empty.");
        }
        guardNoControlChars(desc);

        LocalDate by = parseIsoDate(dateStr, "OOPS!!! Invalid date for /by. Use yyyy-mm-dd.");
        return new Deadline(desc, by, false);
    }

    /**
     * Parses a todo task from input.
     * Format: <code>todo [description]</code>
     * @param input user input string
     * @return a {@link Todo} task
     * @throws EmptyException if the description is missing
     */
    public static Task parseTodo(String input) throws EmptyException {
        String desc = input.trim();
        if (desc.isEmpty()) {
            throw new EmptyException("OOPS!!! The description of a todo cannot be empty.");
        }
        return new Todo(desc, false);
    }

    /**
     * Parses an event task from input.
     * Format: <code>event [description] /from [start] /to [end]</code>
     * @param input user input string
     * @return an {@link Event} task
     * @throws EmptyException if the description, start, or end is missing
     */
    public static Task parseEvent(String input) throws EmptyException, InvalidException {
        if (input == null || input.isBlank()) {
            throw new EmptyException("OOPS!!! Provide a description, /from <date>, and /to <date>.");
        }

        // Robust flag extraction to detect duplicates like "/from ... /from ..."
        // Pattern: capture segments for /from and /to; allow surrounding spaces.
        Pattern pFrom = Pattern.compile("(?i)\\s*/from\\s+([^/]+?)\\s*(?=/to|$)");
        Pattern pTo = Pattern.compile("(?i)\\s*/to\\s+(.+)$");

        Matcher mFrom = pFrom.matcher(input);
        Matcher mTo = pTo.matcher(input);

        if (!mFrom.find() || !mTo.find()) {
            throw new EmptyException("OOPS!!! Use: event <desc> /from <yyyy-mm-dd> /to <yyyy-mm-dd>.");
        }

        // Description is everything before first /from
        String desc = input.substring(0, mFrom.start()).trim();
        if (desc.isEmpty()) {
            throw new EmptyException("OOPS!!! Event description is empty.");
        }
        guardNoControlChars(desc);

        String fromStr = mFrom.group(1).trim();
        String toStr = mTo.group(1).trim();

        // Validate duplicates: ensure only one /from and one /to appear
        if (mFrom.find()) {
            throw new InvalidException("Multiple /from parameters detected.");
        }
        if (pTo.matcher(input).results().count() > 1) {
            throw new InvalidException("Multiple /to parameters detected.");
        }

        LocalDate from = parseIsoDate(fromStr, "OOPS!!! Invalid /from date. Use yyyy-mm-dd.");
        LocalDate to = parseIsoDate(toStr, "OOPS!!! Invalid /to date. Use yyyy-mm-dd.");

        if (to.isBefore(from)) {
            throw new InvalidException("End date must be on or after start date.");
        }
        return new Event(desc, fromStr, toStr, false);
    }

    private static LocalDate parseIsoDate(String s, String messageIfFail) throws InvalidException, EmptyException {
        if (s == null || s.isBlank()) {
            throw new EmptyException(messageIfFail);
        }
        try {
            return LocalDate.parse(s);
        } catch (DateTimeParseException ex) {
            throw new InvalidException(messageIfFail);
        }
    }

    /** Disallow control characters that could corrupt save files or UI. */
    private static void guardNoControlChars(String s) throws InvalidException {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isISOControl(s.charAt(i))) {
                throw new InvalidException("Description contains invalid control characters.");
            }
        }
    }
}
