package john;

import john.command.CommandBase;
import john.task.TaskList;

public class John {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public John(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = storage.loadTasks();
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                ui.showLine();
                CommandBase c = ui.readCommand();
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (JohnException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        ui.close();
    }

    public static void main(String[] args) {
        new John("data/john.txt").run();
    }
}
