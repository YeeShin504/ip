package john;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

import john.task.TaskList;
import john.task.ToDo;

class StorageTest {
    @Test
    void saveAndLoadTasks_emptyList() {
        String testFile = "./data/test_storage_empty.txt";
        Storage storage = new Storage(testFile);
        TaskList list = new TaskList();
        storage.saveTasks(list);
        TaskList loaded = storage.loadTasks();
        assertEquals(0, loaded.size());
        new File(testFile).delete();
    }

    @Test
    void saveAndLoadTasks_withToDo() {
        String testFile = "./data/test_storage_todo.txt";
        Storage storage = new Storage(testFile);
        ToDo todo = new ToDo("Test task");
        TaskList list = new TaskList();
        list.add(todo);
        storage.saveTasks(list);
        TaskList loaded = storage.loadTasks();
        assertEquals(1, loaded.size());
        assertEquals("[T] [ ] Test task", loaded.get(0).toString());
        new File(testFile).delete();
    }

    @Test
    void loadTasks_missingFile_returnsEmptyList() {
        String testFile = "./data/nonexistent_storage.txt";
        File file = new File(testFile);
        if (file.exists()) {
            file.delete();
        }
        Storage storage = new Storage(testFile);
        TaskList loaded = storage.loadTasks();
        assertEquals(0, loaded.size());
    }
}
