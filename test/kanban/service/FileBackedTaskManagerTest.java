package kanban.service;

import kanban.module.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {


    Path pathOfFile;
    @BeforeEach
    public void beforeEach(){

        Path pathOfStorage = Paths.get("src/kanban/taskManagerStorageInFile");
        pathOfFile = Paths.get(pathOfStorage.toAbsolutePath() + "/TaskStorage.csv");

        try {
            Files.deleteIfExists(pathOfFile);
            Files.deleteIfExists(pathOfStorage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            if (!Files.exists(pathOfStorage)) {
                Files.createDirectory(pathOfStorage);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании директории: ");
            e.printStackTrace();
        }

        pathOfFile = Paths.get(pathOfStorage.toAbsolutePath()
                + "/TaskStorage.csv");
        try {

            if (!Files.exists(pathOfFile)) {
                Files.createFile(pathOfFile);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла хранения FileBackedTaskManager: ");
            e.printStackTrace();
        }

        taskManager = new FileBackedTaskManager(pathOfFile);
    }
    @Test
    public void shouldReturnTrueForComparisonOfTwoEmptyFileBackedTaskManagers(){

        FileBackedTaskManager  fileBackedTaskManager = new FileBackedTaskManager(pathOfFile);
        FileBackedTaskManager  fileBackedTaskManager2 = new FileBackedTaskManager(pathOfFile);

        assertEquals(fileBackedTaskManager, fileBackedTaskManager2);
    }
    @Test
    public void shouldReturnTrueForComparisonOfTwoFileBackedTaskManagersWith3TasksAndWithoutHistory(){

        FileBackedTaskManager  fileBackedTaskManager = new FileBackedTaskManager(pathOfFile);

        RegularTask regularWithId0 = new RegularTask(
                0
                ,"0 regular"
                ,"epic with id 0"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
        );
        fileBackedTaskManager.createRegularTask(regularWithId0);

        EpicTask epicWithId1 = new EpicTask(
                1
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );

        fileBackedTaskManager.createEpicTask(epicWithId1);

        Iterator<Integer> it = fileBackedTaskManager.getEpicTaskStorage().keySet().iterator();

        int existingEpicId = -1;
        if (it.hasNext()) {
            existingEpicId = it.next();
        }

        SubTask subTaskWithId2 = new SubTask(
                2
                , "Промыть рис"
                , "Желательно 400 гр"
                , StatusName.DONE
                , TaskType.SUBTASK
                , existingEpicId
        );

        fileBackedTaskManager.createSubTask(subTaskWithId2);
        FileBackedTaskManager  fileBackedTaskManager2 = new FileBackedTaskManager(pathOfFile);

        assertEquals(fileBackedTaskManager, fileBackedTaskManager2);
    }
    @Test
    public void shouldReturnTrueForComparisonOfTwoFileBackedTaskManagersWith3TasksAndHistory(){

        FileBackedTaskManager  fileBackedTaskManager = new FileBackedTaskManager(pathOfFile);

        RegularTask regularWithId0 = new RegularTask(
                0
                ,"0 regular"
                ,"regular with id 0"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
        );
        fileBackedTaskManager.createRegularTask(regularWithId0);

        EpicTask epicWithId1 = new EpicTask(
                1
                , "1 epic"
                , "epic with id 1"
                , TaskType.EPIC_TASK
        );

        fileBackedTaskManager.createEpicTask(epicWithId1);

        Iterator<Integer> it = fileBackedTaskManager.getEpicTaskStorage().keySet().iterator();

        int existingEpicId = -1;
        if (it.hasNext()) {
            existingEpicId = it.next();
        }

        SubTask subTaskWithId2 = new SubTask(
                2
                , "2 subtask"
                , "subtask with id 2"
                , StatusName.NEW
                , TaskType.SUBTASK
                , existingEpicId
        );

        fileBackedTaskManager.createSubTask(subTaskWithId2);


        fileBackedTaskManager.getRegularTask(0);
        fileBackedTaskManager.getEpicTask(1);
        fileBackedTaskManager.getSubTask(2);

        FileBackedTaskManager  fileBackedTaskManager2 = new FileBackedTaskManager(pathOfFile);

        assertEquals(fileBackedTaskManager, fileBackedTaskManager2);
    }
    @Test
    public void shouldReturnTrueForComparisonOfTwoFileBackedTaskManagersWithOnlyEpics(){

        FileBackedTaskManager  fileBackedTaskManager = new FileBackedTaskManager(pathOfFile);

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );

        fileBackedTaskManager.createEpicTask(epicWithId0);

        EpicTask epicWithId1 = new EpicTask(
                1
                , "1 epic"
                , "epic with id 1"
                , TaskType.EPIC_TASK
        );

        fileBackedTaskManager.createEpicTask(epicWithId1);


        FileBackedTaskManager  fileBackedTaskManager2 = new FileBackedTaskManager(pathOfFile);

        assertEquals(fileBackedTaskManager, fileBackedTaskManager2);
    }

}
