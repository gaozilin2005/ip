package cat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.stream.Stream;

import cat.task.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cat.exception.EmptyException;
import cat.exception.InvalidException;
import cat.task.Deadline;
import cat.task.Event;
import cat.task.Task;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ParserTest {

    @Test
    public void parseTask_correctInput_noException() throws EmptyException, InvalidException {
        Task task1 = Parser.parseTask("deadline read book /by 2025-03-24");
        Task task2 = Parser.parseTask("event book club /from 2024-03-24 /to 2024-03-25");
        assertEquals(new Deadline("read book", LocalDate.of(2025, 03, 24), false), task1);
        assertEquals(new Event("book club", "2024-03-24",
                "2024-03-25", false), task2);
    }

    @Test
    public void parseTask_incorrectInput_throwsEmptyException() throws EmptyException, InvalidException {
        try {
            Task task1 = Parser.parseTask("deadline read book");
        } catch (Exception e) {
            assertTrue(e instanceof EmptyException);
        }
    }

    @Test
    void parseDeadline_ok() throws Exception {
        Task t = Parser.parseTask("deadline read book /by 2025-03-24");
        assertTrue(t instanceof Deadline);
        Deadline d = (Deadline) t;
        assertEquals("read book", d.getDescription());
        assertEquals(LocalDate.of(2025, 3, 24), d.getBy());
    }

    @Test
    void parseEvent_ok() throws Exception {
        Task t = Parser.parseTask("event book club /from 2024-03-24 /to 2024-03-25");
        assertTrue(t instanceof Event);
        Event e = (Event) t;
        assertEquals("book club", e.getDescription());
        assertEquals("2024-03-24", e.getFrom());
        assertEquals("2024-03-25", e.getTo());
    }

    @Test
    void parseTodo_ok() throws Exception {
        Task t = Parser.parseTask("todo read");
        assertTrue(t instanceof Todo);
        assertEquals("read", t.getDescription());
    }

    static Stream<String> unknownCommands() {
        return Stream.of("bogus hi", "remind me", "whatever", "alias"); // last one missing args
    }

    @ParameterizedTest
    @MethodSource("unknownCommands")
    @DisplayName("unknown command -> InvalidException")
    void invalidCommand_throws(String input) {
        assertThrows(InvalidException.class, () -> Parser.parseTask(input));
    }

    @Test
    @DisplayName("deadline missing /by -> EmptyException")
    void deadline_missingBy_throws() {
        assertThrows(EmptyException.class, () -> Parser.parseTask("deadline read"));
    }

    @Test
    @DisplayName("event missing /from or /to -> EmptyException")
    void event_missingPieces_throws() {
        assertThrows(EmptyException.class,
                () -> Parser.parseTask("event party"));
        assertThrows(EmptyException.class,
                () -> Parser.parseTask("event party /from 2024-01-01"));
    }

    @Test
    @DisplayName("task with no description -> EmptyException")
    void emptyDescription_throws() {
        assertThrows(EmptyException.class, () -> Parser.parseTask("todo "));
        assertThrows(EmptyException.class, () -> Parser.parseTask("todo"));
    }

    @Test
    @DisplayName("extra spaces are handled")
    void extraSpaces_ok() throws Exception {
        Task t = Parser.parseTask("  deadline   read  /by   2025-03-24 ");
        assertTrue(t instanceof Deadline);
        assertEquals("read", ((Deadline) t).getDescription());
    }
}
