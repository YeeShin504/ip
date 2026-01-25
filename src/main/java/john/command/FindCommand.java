package john.command;

import john.task.Task;
import john.task.TaskList;
import john.Ui;

import java.util.List;

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
