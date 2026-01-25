package john.task;

import java.util.ArrayList;
import java.util.List;

import john.JohnException;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "No tasks found.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, tasks.get(i)));
        }
        return sb.toString();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int idx) {
        if (idx < 0 || idx >= tasks.size()) {
            String message = String.format("Invalid task number. Choose a number from 1 to %s inclusive.",
                    tasks.size());
            throw new JohnException(message);
        }
        return tasks.get(idx);
    }

    public void remove(Task task) {
        tasks.remove(task);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAll() {
        return tasks;
    }

    /**
     * Returns a list of tasks whose descriptions contain the given keyword.
     * 
     * @param keyword The keyword to search for.
     * @return List of matching tasks.
     */
    public TaskList findTasksByKeyword(String keyword) {
        TaskList result = new TaskList();
        for (Task task : this.tasks) {
            if (task.getDescription().contains(keyword)) {
                result.add(task);
            }
        }
        return result;
    }
}
