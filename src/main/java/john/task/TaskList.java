package john.task;

import java.util.ArrayList;
import java.util.List;

import john.JohnException;

/**
 * Represents a list of tasks and provides operations on them.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList from an existing list of tasks.
     * 
     * @param tasks The list of tasks
     */
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

    /**
     * Adds a task to the list.
     *
     * @param task The task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Gets the task at the specified index.
     *
     * @param idx The index of the task
     * @return The task at the given index
     * @throws JohnException if the index is invalid
     */
    public Task get(int idx) {
        if (idx < 0 || idx >= tasks.size()) {
            String message = String.format("Invalid task number. Choose a number from 1 to %s inclusive.",
                    tasks.size());
            throw new JohnException(message);
        }
        return tasks.get(idx);
    }

    /**
     * Removes the specified task from the list.
     *
     * @param task The task to remove
     */
    public void remove(Task task) {
        tasks.remove(task);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The size of the list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns all tasks in the list.
     *
     * @return The list of tasks
     */
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
            if (task.description.contains(keyword)) {
                result.add(task);
            }
        }
        return result;
    }
}
