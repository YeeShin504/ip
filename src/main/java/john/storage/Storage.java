package john.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Collectors;

import john.JohnException;
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
     * @throws JohnException if there are file I/O errors
     */
    public void saveTasks(TaskList taskList) {
        File dir = new File(dataDir);

        // Check if directory exists or can be created
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new JohnException("Unable to create data directory at: " + dataDir
                        + ". Please check write permissions.");
            }
        }

        // Check if directory is writable
        if (!dir.canWrite()) {
            throw new JohnException("Cannot write to data directory at: " + dataDir
                    + ". Please check directory permissions.");
        }

        File file = new File(dataFile);

        // Check if file exists and is writable
        if (file.exists() && !file.canWrite()) {
            throw new JohnException("Cannot write to data file at: " + dataFile
                    + ". Please check file permissions.");
        }

        try (FileWriter writer = new FileWriter(dataFile)) {
            String allTasks = taskList.getAll().stream()
                .map(Task::toDataString)
                .collect(Collectors.joining("\n"));
            writer.write(allTasks);
        } catch (IOException e) {
            throw new JohnException("Failed to save tasks to file: " + dataFile
                    + ". Error: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return A list of tasks loaded from storage
     * @throws JohnException if there are file I/O or parsing errors
     */
    public TaskList loadTasks() {
        TaskList tasks = new TaskList();
        File file = new File(dataFile);

        // If file doesn't exist, return empty list (normal on first run)
        if (!file.exists()) {
            return tasks;
        }

        // Check if file is readable
        if (!file.canRead()) {
            throw new JohnException("Cannot read data file at: " + dataFile
                    + ". Please check file permissions.");
        }

        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    Task task = Task.fromDataString(line);
                    tasks.add(task);
                } catch (Exception e) {
                    throw new JohnException("Corrupted data file at line " + lineNumber
                            + ": '" + line + "'. Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new JohnException("Failed to load tasks from file: " + dataFile
                    + ". Error: " + e.getMessage());
        }

        return tasks;
    }
}
