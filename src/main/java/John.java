import java.util.ArrayList;
import java.util.Scanner;

public class John {
    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final String DATA_DIR = "./data";
    private static final String DATA_FILE = DATA_DIR + "/john.txt";
    private static boolean run;

    public static void main(String[] args) {
        String logo = """
                       __      __       \s
                      / /___  / /_  ____
                 __  / / __ \\/ __ \\/ __ \\
                / /_/ / /_/ / / / / / / /\s
                \\____/\\____/_/ /_/_/ /_/
                """;
        linebreak();
        System.out.println("Hello! I'm\n" + logo);
        System.out.println("What can I do for you?");
        linebreak();

        loadTasksFromFile();

        Scanner scanner = new Scanner(System.in);
        run = true;
        while (run) {
            String message = scanner.nextLine();
            linebreak();
            try {
                eval(message);
            } catch (JohnException e) {
                System.out.println(e.getMessage());
            }
            linebreak();
        }
        scanner.close();
    }

    private static void eval(String message) {
        String[] input = message.split(" ", 2);
        Command command = Command.fromString(input[0]);
        String argument = input.length > 1 ? input[1] : "";
        switch (command) {
            case LIST:
                printList();
                break;
            case BYE:
                run = false;
                exit();
                break;
            case MARK:
                markComplete(argument);
                break;
            case UNMARK:
                markIncomplete(argument);
                break;
            case TODO:
                addToList(ToDo.of(argument));
                break;
            case DELETE:
                removeFromList(argument);
                break;
            case DEADLINE:
                addToList(Deadline.of(argument));
                break;
            case EVENT:
                addToList(Event.of(argument));
                break;
            default:
                throw new JohnException("I'm sorry, but I don't know what that means :-(");
        }
    }

    private static void addToList(Task task) {
        tasks.add(task);
        saveTasksToFile();
        System.out.printf("Got it. I've added this task:\n    %s\n", task);
        System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
    }

    private static Task getTask(String taskNum) {
        int idx = Integer.parseInt(taskNum) - 1;
        if (idx < 0 || idx >= tasks.size()) {
            String message = String.format("Invalid task number. Choose a number from 1 to %s inclusive.",
                    tasks.size());
            throw new JohnException(message);
        }
        return tasks.get(idx);
    }

    private static void removeFromList(String taskNum) {
        Task task = getTask(taskNum);
        tasks.remove(task);
        saveTasksToFile();
        System.out.printf("Noted. I've removed this task:\n    %s\n", task);
        System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
    }

    private static void saveTasksToFile() {
        java.io.File dir = new java.io.File(DATA_DIR);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                System.err.println("Warning: Could not create data directory at " + DATA_DIR);
                return;
            }
        }

        try (java.io.FileWriter writer = new java.io.FileWriter(DATA_FILE)) {
            for (Task task : tasks) {
                writer.write(task.toDataString());
            }
        } catch (java.io.IOException e) {
            System.err.println("Warning: Could not save tasks to file at " + DATA_FILE);
        }
    }

    private static void loadTasksFromFile() {
        java.io.File file = new java.io.File(DATA_FILE);
        if (!file.exists()) {
            // No file to load, nothing to do
            return;
        }

        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = Task.fromDataString(line);
                tasks.add(task);
            }
        } catch (java.io.IOException e) {
            System.err.println("Warning: Could not load tasks from file at " + DATA_FILE);
        }
    }

    private static void printList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%s. %s\n", (i + 1), tasks.get(i));
        }
    }

    private static void markComplete(String taskNum) {
        Task task = getTask(taskNum);
        task.markComplete();
        saveTasksToFile();
    }

    private static void markIncomplete(String taskNum) {
        Task task = getTask(taskNum);
        task.markIncomplete();
        saveTasksToFile();
    }

    private static void exit() {
        saveTasksToFile();
        System.out.println("Bye. Hope to see you again soon!");
    }

    private static void linebreak() {
        System.out.println("_".repeat(100));
    }
}
