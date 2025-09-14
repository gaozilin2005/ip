package cat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cat.task.Deadline;
import cat.task.Event;
import cat.task.Task;
import cat.task.TaskList;
import cat.task.Todo;

/**
 * Handles saving and loading of tasks from a text file.
 * A <code>Storage</code> object corresponds to a file on disk
 * that contains serialized tasks, e.g., <code>./data/duke.txt</code>.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a new storage object that uses the given file path.
     * @param filePath path to the file, e.g., <code>./data/duke.txt</code>
     */
    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    /**
     * Loads tasks from the storage file.
     * If the file does not exist, it is created.
     * Corrupted lines are skipped.
     * @return list of tasks read from file
     * @throws IOException if the file cannot be read or created
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        // Ensure parent directory exists
        Path dir = filePath.getParent();
        if (dir != null) {
            Files.createDirectories(dir);
        }
        // Ensure file exists
        if (Files.notExists(filePath)) {
            Files.createFile(filePath);
            return new ArrayList<>();
        }

        //read each line until end of file
        try (var lines = Files.lines(filePath)) {
            lines.forEach(raw -> {
                String line = raw == null ? "" : raw.trim();
                if (line.isEmpty()) return; // skip blanks
                try {
                    tasks.add(parseTask(line));
                } catch (Exception ex) {
                    // Skip corrupted line, optionally log:
                    System.err.println("Skipping corrupted line: \"" + line + "\" (" + ex.getMessage() + ")");
                }
            });
        }
        return tasks;
    }

    /**
     * Saves the given tasks to the storage file.
     * Each task is written in its save format, e.g.,
     * <code>T | X | read book</code>.
     * @param tasks list of tasks to save
     * @throws IOException if the file cannot be written
     */
    public void save(TaskList tasks) throws IOException {
        Path dir = filePath.getParent();
        if (dir != null) {
            Files.createDirectories(dir);
        }
        List<String> lines = new ArrayList<>();
        for (Task t : tasks.getTasks()) {
            lines.add(t.toSaveFormat());
        }
        Files.write(
                filePath,
                lines,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE
        );
    }

    /**
     * Parses one line of text into a task object.
     * @param line task data string, e.g., <code>D | 0 | return book | 2025-03-24</code>
            * @return task parsed from the line
     * @throws IllegalArgumentException if the task type is unknown
     */
    public Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("X");
        String description = parts[2];
        //will automatically throw exception if too few words

        switch (type) {
        case "T":
            return new Todo(description, isDone);
        case "D":
            String by = parts[3];
            return new Deadline(description, LocalDate.parse(by), isDone);
        case "E":
            String from = parts[3];
            String to = parts[4];
            return new Event(description, from, to, isDone);
        default:
            throw new IllegalArgumentException("Unknown task type: " + line);
        }
    }

}
