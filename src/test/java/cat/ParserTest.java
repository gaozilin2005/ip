package cat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import cat.exception.EmptyException;
import cat.exception.InvalidException;
import cat.task.Deadline;
import cat.task.Event;
import cat.task.Task;

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
}
