import java.util.ArrayList;
import java.util.Scanner;

public class John {
    private static final ArrayList<Task> tasks = new ArrayList<>();
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
        String command = input[0].toLowerCase().trim();
        String argument = input.length > 1 ? input[1] : "";
        switch (command) {
            case "list":
                printList();
                break;
            case "bye":
                run = false;
                exit();
                break;
            case "mark":
                markComplete(argument);
                break;
            case "unmark":
                markIncomplete(argument);
                break;
            case "todo":
                addToList(ToDo.of(argument));
                break;
            case "delete":
                removeFromList(argument);
                break;
            case "deadline":
                addToList(Deadline.of(argument));
                break;
            case "event":
                addToList(Event.of(argument));
                break;
            default:
                throw new JohnException("I'm sorry, but I don't know what that means :-(");
        }
    }

    private static void addToList(Task task) {
        tasks.add(task);
        System.out.printf("Got it. I've added this task:\n    %s\n", task);
        System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
    }

    private static Task getTask(String taskNum) {
        int idx = Integer.parseInt(taskNum) - 1;
        if (idx < 0 || idx >= tasks.size()) {
            String message = String.format("Invalid task number. Choose a number from 1 to %s inclusive.", tasks.size());
            throw new JohnException(message);
        }
        return tasks.get(idx);
    }

    private static void removeFromList(String taskNum) {
        Task task = getTask(taskNum);
        tasks.remove(task);
        System.out.printf("Noted. I've removed this task:\n    %s\n", task);
        System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
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
    }

    private static void markIncomplete(String taskNum) {
        Task task = getTask(taskNum);
        task.markIncomplete();
    }

    private static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    private static void linebreak() {
        System.out.println("_".repeat(100));
    }
}
