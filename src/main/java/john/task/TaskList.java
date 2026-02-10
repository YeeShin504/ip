package john.task;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
     * Constructs a TaskList from an existing ArrayList of tasks.
     *
     * @param tasks The list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Constructs a TaskList from a variable number of Task arguments.
     *
     * @param initialTasks The tasks to add to the list
     */
    public TaskList(Task... initialTasks) {
        this.tasks = new ArrayList<>();
        for (Task task : initialTasks) {
            this.tasks.add(task);
        }
    }

    /**
     * Constructs a TaskList from a Stream of tasks.
     *
     * @param taskStream The stream of tasks
     */
    public TaskList(Stream<Task> taskStream) {
        this.tasks = taskStream.collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "No tasks found.";
        }
        return IntStream.range(0, tasks.size())
                .mapToObj(i -> String.format("%d. %s\n", i + 1, tasks.get(i)))
                .collect(Collectors.joining());
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
        return this.tasks;
    }

    /**
     * Returns a list of tasks whose descriptions contain the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return List of matching tasks.
     */
    public TaskList findTasksByKeyword(String keyword) {
        return new TaskList(
            tasks.stream()
                .filter(task -> task.description.contains(keyword))
        );
    }
}
