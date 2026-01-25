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
}
