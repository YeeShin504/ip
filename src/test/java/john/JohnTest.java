package john;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Test;

import john.storage.Storage;
import john.task.TaskList;

class JohnTest {
    @Test
    void johnConstructor_loadsTasksSuccessfully() {
        String testFile = "./data/test_john_constructor.txt";
        Storage storage = new Storage(testFile);
        storage.saveTasks(new TaskList());
        John john = new John(testFile);
        assertNotNull(john);
        new File(testFile).delete();
    }

    @Test
    void johnConstructor_handlesMissingFile() {
        String testFile = "./data/nonexistent_file.txt";
        File file = new File(testFile);
        if (file.exists()) {
            file.delete();
        }
        John john = new John(testFile);
        assertNotNull(john);
    }
}
