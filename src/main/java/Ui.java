public class Ui {
    private final java.util.Scanner sc = new java.util.Scanner(System.in);

    public void showWelcome() {
        String logo = """
                       __      __       \s
                      / /___  / /_  ____
                 __  / / __ \\/ __ \\/ __ \\
                / /_/ / /_/ / / / / / / /\s
                \\____/\\____/_/ /_/_/ /_/
                """;
        showLine();
        System.out.println("Hello! I'm\n" + logo);
        System.out.println("What can I do for you?");
        showLine();
    }

    public void showLine() {
        System.out.println("_".repeat(100));
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks from file.");
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public CommandBase readCommand() {
        String input = sc.nextLine();
        return Parser.parse(input);
    }

    public void close() {
        sc.close();
    }
}
