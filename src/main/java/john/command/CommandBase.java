package john.command;

import john.task.TaskList;
import john.Ui;
import john.Storage;

public abstract class CommandBase {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    public boolean isExit() {
        return false;
    }
}
