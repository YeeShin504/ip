public class UnmarkCommand extends CommandBase {
    private final String taskNum;
    public UnmarkCommand(String taskNum) {
        this.taskNum = taskNum;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = tasks.get(Integer.parseInt(taskNum) - 1);
        task.markIncomplete();
        storage.saveTasks(tasks);
    }
}
