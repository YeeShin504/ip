import java.util.ArrayList;
import java.util.Scanner;

public class John {
    private static final ArrayList<String> userList = new ArrayList<>();

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
            String input = scanner.nextLine().toLowerCase();
            linebreak();
            switch (input) {
                case "list":
                    printList();
                    break;
                case "bye":
                    run = false;
                    scanner.close();
                    exit();
                    break;
                default:
                    addToList(input);
            }
            linebreak();
        }
    }

    private static void addToList(String input) {
        userList.add(input);
        System.out.printf("added: %s\n", input);
    }

    private static void printList() {
        for (int i = 0; i < userList.size(); i++) {
            System.out.printf("%s. %s\n", (i + 1), userList.get(i));
        }
    }

    private static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    private static void linebreak() {
        System.out.println("_".repeat(100));
    }
}
