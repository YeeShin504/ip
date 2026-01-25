package john.command;

import john.Storage;
import john.Ui;
import john.task.TaskList;

public abstract class CommandBase {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    public boolean isExit() {
        return false;
    }
}
