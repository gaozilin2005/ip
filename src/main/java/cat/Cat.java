package cat;

import cat.exception.EmptyException;
import cat.exception.InvalidException;
import cat.task.Task;
import cat.task.TaskList;
import cat.ui.Ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main application class for the Cat task manager.
 * A <code>Cat</code> object coordinates input parsing,
 * task list updates, and saving to storage.
 */
public class Cat {
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

    /**
     * Creates a Cat application with storage file <code>./data/duke.txt</code>.
     * Loads tasks from storage if available, otherwise starts with an empty list.
     */
    public Cat() {
        ui = new Ui();
        storage = new Storage("./data/duke.txt");
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList(new ArrayList<>());
        }
    }

    /**
     * Runs the main application loop.
     * Reads user input and executes commands until <code>bye</code> is entered.
     * Supports commands such as <code>list</code>, <code>mark</code>, <code>unmark</code>,
     * <code>delete</code>, <code>due</code>, and task creation.
     */
    public void run() {
        ui.printGreeting();
        Scanner scanner = new Scanner(System.in);
        String input;

        while (!(input = scanner.nextLine()).equals("bye")) {
            try {
                if (input.equals("list")) {
                    tasks.printList();
                } else if (input.matches("^mark .+$")) {
                    String[] parts = input.split(" ");
                    int taskNum = Integer.parseInt(parts[1]) - 1;
                    tasks.markDone(taskNum);
                } else if (input.matches("^unmark .+$")) {
                    String[] parts = input.split(" ");
                    int taskNum = Integer.parseInt(parts[1]) - 1;
                    tasks.unmarkDone(taskNum);
                } else if (input.startsWith("delete")) {
                    String[] parts = input.split("delete ");
                    int taskNum = Integer.parseInt(parts[1]) - 1;
                    tasks.delete(taskNum);
                } else if (input.startsWith("due")) {
                    String[] parts = input.split("due ");
                    LocalDate date = LocalDate.parse(parts[1]);
                    tasks.printDueOnDate(date);
                } else if (input.startsWith("find")) {
                    String[] parts = input.split("find ");
                    String keyword = parts[1];
                    tasks.search(keyword);
                } else {
                    try {
                        Task task = Parser.parseTask(input);
                        tasks.add(task);
                    } catch (EmptyException | InvalidException e) {
                        this.ui.printLine();
                        System.out.println(e.getMessage());
                        this.ui.printLine();
                    }
                }
                storage.save(tasks);
            }  catch (IOException e) {
                System.out.println("OOPS!!! Could not save tasks to file: " + e.getMessage());
            }
        }
        ui.printGoodbye();
    }

    /**
     * Entry point for the application.
     * @param args command line arguments (not used)
     * @throws IOException if tasks cannot be loaded or saved
     */
    public static void main(String[] args) throws IOException {
        new Cat().run();
    }

}
