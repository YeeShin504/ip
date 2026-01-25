package john;

import john.task.TaskList;
import john.task.Task;
import john.task.ToDo;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Arrays;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StorageTest {
    @Test
    void saveAndLoadTasks_emptyList() {
        String testFile = "./data/test_storage_empty.txt";
        Storage storage = new Storage(testFile);
        TaskList list = new TaskList(Collections.emptyList());
        storage.saveTasks(list);
        TaskList loaded = new TaskList(storage.loadTasks());
        assertEquals(0, loaded.size());
        new File(testFile).delete();
    }

    @Test
    void saveAndLoadTasks_withToDo() {
        String testFile = "./data/test_storage_todo.txt";
        Storage storage = new Storage(testFile);
        ToDo todo = new ToDo("Test task");
        TaskList list = new TaskList(Arrays.asList(todo));
        storage.saveTasks(list);
        TaskList loaded = new TaskList(storage.loadTasks());
        assertEquals(1, loaded.size());
        assertEquals("[T] [ ] Test task", loaded.get(0).toString());
        new File(testFile).delete();
    }

    @Test
    void loadTasks_missingFile_returnsEmptyList() {
        String testFile = "./data/nonexistent_storage.txt";
        File file = new File(testFile);
        if (file.exists())
            file.delete();
        Storage storage = new Storage(testFile);
        TaskList loaded = new TaskList(storage.loadTasks());
        assertEquals(0, loaded.size());
    }
}
