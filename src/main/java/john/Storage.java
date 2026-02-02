package john;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import john.task.Task;
import john.task.TaskList;

/**
 * Handles loading and saving of tasks to persistent storage.
 */
public class Storage {
    public static final String DEFAULT_DATA_DIR = "./data";
    public static final String DEFAULT_DATA_FILE = DEFAULT_DATA_DIR + "/john.txt";

    private final String dataDir;
    private final String dataFile;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The path to the storage file
     */
    public Storage(String filePath) {
        File file = new File(filePath);
        this.dataDir = file.getParent();
        this.dataFile = filePath;
    }

    /**
     * Saves the list of tasks to the storage file.
     *
     * @param taskList The TaskList to save
     */
    public void saveTasks(TaskList taskList) {
        File dir = new File(dataDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                System.err.println("Warning: Could not create data directory at " + dataDir);
                return;
            }
        }
        try (FileWriter writer = new FileWriter(dataFile)) {
            for (Task task : taskList.getAll()) {
                writer.write(task.toDataString());
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not save tasks to file at " + dataFile);
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return A list of tasks loaded from storage
     */
    public TaskList loadTasks() {
        TaskList tasks = new TaskList();
        File file = new File(dataFile);
        if (!file.exists()) {
            return tasks;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = Task.fromDataString(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not load tasks from file at " + dataFile);
        }
        return tasks;
    }
}
