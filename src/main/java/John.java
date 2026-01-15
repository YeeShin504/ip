import java.util.ArrayList;
import java.util.Scanner;

public class John {
    private static final ArrayList<Task> tasks = new ArrayList<>();

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
        boolean run = true;
        while (run) {
            String[] input = scanner.nextLine().toLowerCase().split(" ");
            linebreak();
            switch (input[0]) {
                case "list":
                    printList();
                    break;
                case "bye":
                    run = false;
                    scanner.close();
                    exit();
                    break;
                case "mark":
                    markComplete(input[1]);
                    break;
                case "unmark":
                    markIncomplete(input[1]);
                    break;
                default:
                    addToList(String.join(" ", input));
            }
            linebreak();
        }
    }

    private static void addToList(String input) {
        tasks.add(new Task(input));
        System.out.printf("added: %s\n", input);
    }

    private static void printList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%s. %s\n", (i + 1), tasks.get(i));
        }
    }

    private static void markComplete(String taskNum) {
        int idx = Integer.parseInt(taskNum) - 1;
        if (idx < 0 || idx >= tasks.size()) {
            System.out.println("Error: Invalid task number");
            return;
        }
        Task task = tasks.get(idx);
        task.markComplete();
    }

    private static void markIncomplete(String taskNum) {
        int idx = Integer.parseInt(taskNum) - 1;
        if (idx < 0 || idx >= tasks.size()) {
            System.out.println("Error: Invalid task number");
            return;
        }
        Task task = tasks.get(idx);
        task.markIncomplete();
    }

    private static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    private static void linebreak() {
        System.out.println("_".repeat(100));
    }
}
