package john.task;

import java.util.ArrayList;
import java.util.Comparator;
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
        assert tasks != null : "Task list argument must not be null";
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Constructs a TaskList from a variable number of Task arguments.
     *
     * @param initialTasks The tasks to add to the list
     */
    public TaskList(Task... initialTasks) {
        assert initialTasks != null : "Task varargs must not be null";
        this.tasks = new ArrayList<>();
        for (Task task : initialTasks) {
            assert task != null : "Task in varargs must not be null";
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
        assert task != null : "Cannot add null task";
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
        assert task != null : "Cannot remove null task";
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
        assert keyword != null : "Keyword must not be null";
        return new TaskList(
            tasks.stream()
                .filter(task -> task != null)
                .filter(task -> task.description.contains(keyword))
        );
    }

    /**
     * Sorts tasks by grouping: Deadlines first (by deadline), then Events (by start), then Todos.
     * By default, Deadlines and Events are sorted earliest to latest. If isLatestFirst is true, latest to earliest.
     * Todos are always sorted alphabetically. Same dates are sorted alphabetically by description.
     * @param isLatestFirst If true, sorts Deadlines/Events from latest to earliest.
     */
    public void sortByTypeAndDate(boolean isLatestFirst) {
        ArrayList<Task> sorted = this.tasks.stream()
            .sorted(getTaskSortComparator(isLatestFirst))
            .collect(Collectors.toCollection(ArrayList::new));
        this.tasks.clear();
        this.tasks.addAll(sorted);
    }

    /**
     * Returns a comparator for sorting tasks by group and date, with optional latest-first order.
     * Todos are always alphabetical. Same dates are sorted alphabetically by description.
     */
    private Comparator<Task> getTaskSortComparator(boolean isLatestFirst) {
        return (a, b) -> {
            int groupA = getGroupOrder(a);
            int groupB = getGroupOrder(b);
            if (groupA != groupB) {
                return groupA - groupB;
            }
            // Todos are always alphabetical regardless of sort direction
            if (a instanceof Todo) {
                return a.description.compareToIgnoreCase(b.description);
            }
            // For Deadlines and Events, sort by date then alphabetically
            int cmp = a.compareTo(b);
            if (cmp == 0) {
                // Same date, sort alphabetically
                return a.description.compareToIgnoreCase(b.description);
            }
            // Task.compareTo is naturally descending, so negate for default (earliest-first)
            return isLatestFirst ? cmp : -cmp;
        };
    }

    /**
     * Returns the group order for a task type.
     * Deadlines = 0, Events = 1, Todos = 2.
     */
    private int getGroupOrder(Task t) {
        if (t instanceof Deadline) {
            return 0;
        }
        if (t instanceof john.task.Event) {
            return 1;
        }
        return 2; // Todo and others
    }
}
