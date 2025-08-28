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
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("X");
            String description = parts[2];

            switch (type) {
            case "T":
                tasks.add(new Todo(description, isDone));
                break;
            case "D":
                tasks.add(new Deadline(description, parts[3], isDone));
                break;
            case "E":
                tasks.add(new Event(description, parts[3], parts[4], isDone));
                break;
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

}
