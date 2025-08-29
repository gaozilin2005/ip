package cat;

import cat.task.TaskList;
import cat.task.Task;
import cat.task.Event;
import cat.task.Deadline;
import cat.task.Todo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Handles saving and loading of tasks from a text file.
 * A <code>Storage</code> object corresponds to a file on disk
 * that contains serialized tasks, e.g., <code>./data/duke.txt</code>.
 */
public class Storage {
    private String filePath;

    /**
     * Creates a new storage object that uses the given file path.
     * @param filePath path to the file, e.g., <code>./data/duke.txt</code>
     */
    public Storage(String filePath) {
        this.filePath = filePath;
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
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        //check if file exists, if doesn't exist create one
        if (!file.exists()) {
            file.createNewFile();
            return tasks;
        }

        //open file for reading
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        //read each line until end of file
        while ((line = br.readLine()) != null) {
            try {
                Task task = parseTask(line);
                tasks.add(task);
            } catch (Exception e) {
                System.out.println("Skipping corrupted line: " + line);
            }
        }
        br.close();
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
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (Task task : tasks.getTasks()) {
            bw.write(task.toSaveFormat());
            bw.newLine();
        }
        bw.close();
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
