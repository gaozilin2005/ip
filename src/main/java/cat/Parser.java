package cat;

import cat.exception.EmptyException;
import cat.exception.InvalidException;
import cat.task.Deadline;
import cat.task.Event;
import cat.task.Task;
import cat.task.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Parses user input strings into {@link Task} objects.
 * A <code>Parser</code> can recognize commands such as
 * <code>todo homework</code>, <code>deadline return book /by 2025-03-24</code>,
 * or <code>event project /from 2025-03-01 /to 2025-03-05</code>.
 */
public class Parser {
    public Parser() {
    }

    /**
     * Parses a task from a user input string.
     * @param input user input, e.g. <code>todo homework</code>
     * @return the parsed {@link Task}
     * @throws EmptyException if the task description is empty
     * @throws InvalidException if the input does not match any known task type
     */
    public static Task parseTask(String input) throws EmptyException, InvalidException {
        Task task = null;
        if (input.startsWith("deadline")) {
            task = parseDeadline(input);
        } else if (input.startsWith("todo")) {
            task = parseTodo(input);
        } else if (input.startsWith("event")) {
            task = parseEvent(input);
        } else {
            throw new InvalidException(
                    "OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
        return task;
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
        String[] parts2 = parts[0].split("deadline ");

        LocalDate by = null;
        while (by == null) {
            try {
                by = LocalDate.parse(parts[1]);
            } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid date format! Please input date in yyyy-mm-dd.");
                Scanner scanner = new Scanner(System.in);
                parts[1] = scanner.nextLine();
            }
        }
        return new Deadline(parts2[1], by, false);
    }

    /**
     * Parses a todo task from input.
     * Format: <code>todo [description]</code>
     * @param input user input string
     * @return a {@link Todo} task
     * @throws EmptyException if the description is missing
     */
    public static Task parseTodo(String input) throws EmptyException {
        String[] parts = input.split("todo ");
        if (parts.length == 1) {
            throw new EmptyException(
                    "OOPS!!! The description of a todo cannot be empty.");
        }
        return new Todo(parts[1], false);
    }

    /**
     * Parses an event task from input.
     * Format: <code>event [description] /from [start] /to [end]</code>
     * @param input user input string
     * @return an {@link Event} task
     * @throws EmptyException if the description, start, or end is missing
     */
    public static Task parseEvent(String input) throws EmptyException {
        String[] parts = input.split("event ");
        if (parts.length == 1) {
            throw new EmptyException(
                    "OOPS!!! A event should follow the format \"event [event] /from [date] /to [date]");
        }
        String[] parts2 = parts[1].split(" /from ");
        if (parts2.length == 1) {
            throw new EmptyException(
                    "OOPS!!! A event should follow the format \"event [event] /from [date] /to [date]");
        }
        String[] parts3 = parts2[1].split(" /to ");
        if (parts3.length == 1) {
            throw new EmptyException(
                    "OOPS!!! A event should follow the format \"event [event] /from [date] /to [date]");
        }
        return new Event(parts2[0], parts3[0], parts3[1], false);
    }
}
