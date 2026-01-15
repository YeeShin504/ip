import java.util.Objects;
import java.util.Scanner;

public class John {
    public static String linebreak = "_".repeat(100);

    public static void main(String[] args) {
        String logo = "       __      __        \n"
                    + "      / /___  / /_  ____\n"
                    + " __  / / __ \\/ __ \\/ __ \\\n"
                    + "/ /_/ / /_/ / / / / / / / \n"
                    + "\\____/\\____/_/ /_/_/ /_/\n";

        System.out.println(linebreak);
        System.out.println("Hello! I'm\n" + logo);
        System.out.println("What can I do for you?");
        System.out.println(linebreak);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(linebreak);
            System.out.println(input);
            System.out.println(linebreak);
            input = scanner.nextLine();
        }

        scanner.close();
        exit();
    }

    public static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(linebreak);
    }
}
