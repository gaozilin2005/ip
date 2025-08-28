import java.io.*;
import java.util.ArrayList;

public class Storage {
    private String filePath;

    public Storage() {
        this.filePath = "./data/duke.txt";
    }

    //load file when program first opens
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

    public void save(ArrayList<Task> tasks) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (Task task : tasks) {
            bw.write(task.toSaveFormat());
            bw.newLine();
        }
        bw.close();
    }

    //throws unchecked exceptions so no need to declare in method signature
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
                return new Deadline(description, by, isDone);
            case "E":
                String from = parts[3];
                String to = parts[4];
                return new Event(description, from, to, isDone);
            default:
                throw new IllegalArgumentException("Unknown task type: " + line);
        }
    }

}
