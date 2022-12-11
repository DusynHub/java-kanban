package kanban.service;

import kanban.module.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    private Path pathOfFile;
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

        fileBackedTaskManager.createEpicTask(et0);
        fileBackedTaskManager.createSubTask(st1);
        fileBackedTaskManager.createSubTask(st2);
        fileBackedTaskManager.createRegularTask(rt3);
        fileBackedTaskManager.createRegularTask(rtAnyId);

        FileBackedTaskManager  fileBackedTaskManager2 = new FileBackedTaskManager(pathOfFile);

        assertEquals(fileBackedTaskManager
                , fileBackedTaskManager2
                , "Менеджеры не равны");
    }


    @Test
    public void shouldReturnTrueForComparisonOfTwoFileBackedTaskManagersWith3TasksAndHistory(){

        FileBackedTaskManager  fileBackedTaskManager = new FileBackedTaskManager(pathOfFile);

        fileBackedTaskManager.createEpicTask(et0);
        fileBackedTaskManager.createSubTask(st1);
        fileBackedTaskManager.createSubTask(st2);
        fileBackedTaskManager.createRegularTask(rt3);
        fileBackedTaskManager.createRegularTask(rt3);

        fileBackedTaskManager.getRegularTask(3);
        fileBackedTaskManager.getEpicTask(0);
        fileBackedTaskManager.getSubTask(1);

        FileBackedTaskManager  fileBackedTaskManager2 = new FileBackedTaskManager(pathOfFile);

        assertEquals(fileBackedTaskManager
                , fileBackedTaskManager2
                , "Менеджеры не равны");
    }

    @Test
    public void shouldReturnTrueForComparisonOfTwoFileBackedTaskManagersWithOnlyEpics(){
        et0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );

        et1 = new EpicTask(
                1
                , "1 epic"
                , "epic with id 1"
                , TaskType.EPIC_TASK
        );

        FileBackedTaskManager  fileBackedTaskManager = new FileBackedTaskManager(pathOfFile);
        fileBackedTaskManager.createEpicTask(et0);
        fileBackedTaskManager.createEpicTask(et1);
        FileBackedTaskManager  fileBackedTaskManager2 = new FileBackedTaskManager(pathOfFile);

        assertEquals(fileBackedTaskManager
                , fileBackedTaskManager2
                , "Менеджеры не равны");
    }

}
