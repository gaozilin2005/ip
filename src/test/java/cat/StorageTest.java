package cat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import cat.task.Task;

public class StorageTest {

    private final String testFilePath = "./data/test.txt";

    @Test
    public void load_validFile_noException() throws IOException {
        FileWriter fw = new FileWriter(testFilePath);
        fw.write("T | X | read book\n");
        fw.write("D | X | submit report | 2025-09-01\n");
        fw.close();

        Storage storage = new Storage(testFilePath);
        ArrayList<Task> tasks = storage.load();

        assertEquals(2, tasks.size());
        assertEquals("read book", tasks.get(0).getDescription());
        assertEquals("submit report", tasks.get(1).getDescription());
    }

    @Test
    public void load_invalidFile_exception() throws IOException {
        FileWriter fw = new FileWriter(testFilePath);
        fw.write("ERROR ERROR\n");
        fw.write("D | X | submit report | 2025-09-01\n");
        fw.close();

        Storage storage = new Storage(testFilePath);
        ArrayList<Task> tasks = storage.load();
        assertEquals(1, tasks.size());
        assertEquals("submit report", tasks.get(0).getDescription());
    }
}
