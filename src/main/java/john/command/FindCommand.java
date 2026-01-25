package john.command;

import john.Ui;
import john.command.CommandBase;
import john.task.TaskList;

public class FindCommand extends CommandBase {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, john.Storage storage) {
        TaskList matchingTasks = tasks.findTasksByKeyword(keyword);
        ui.showLine();
        System.out.println(" Here are the matching tasks in your list:");
        System.out.print(matchingTasks);
        ui.showLine();
    }
}
