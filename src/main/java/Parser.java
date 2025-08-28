import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Parser {
    public Parser() {
    }

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

    public static Task parseDeadline(String input) throws EmptyException{
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

    public static Task parseTodo(String input) throws EmptyException {
        String[] parts = input.split("todo ");
        if (parts.length == 1) {
            throw new EmptyException(
                    "OOPS!!! The description of a todo cannot be empty.");
        }
        return new Todo(parts[1], false);
    }

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
