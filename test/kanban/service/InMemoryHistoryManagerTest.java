package kanban.service;

import kanban.module.EpicTask;
import kanban.module.Task;
import kanban.module.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    FileBackedTaskManager fileBackedTaskManager;
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
        fileBackedTaskManager = FileBackedTaskManager.loadFromFile(pathOfFile);
    }

    @Test
    public void shouldReturnListOfTwoEpicTasksWithId1and2() {

        EpicTask epicWithId0 = new EpicTask(
                0,
                "0 epic",
                "epic with id 0",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId0);

        EpicTask epicWithId1 = new EpicTask(
                1,
                "1 epic",
                "epic with id 1",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId1);

        fileBackedTaskManager.getEpicTask(0);
        fileBackedTaskManager.getEpicTask(1);

        List<Task> expectedTaskList = new ArrayList<>(2);
        expectedTaskList.add(epicWithId0);
        expectedTaskList.add(epicWithId1);

        List<Task> resultHistory= fileBackedTaskManager.getHistoryOfTasks();

        assertIterableEquals(expectedTaskList, resultHistory);
    }

    @Test
    public void shouldReturnListOfOneEpicTaskWithId1AfterTwoSameCalls() {

        EpicTask epicWithId0 = new EpicTask(
                0,
                "0 epic",
                "epic with id 0",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId0);

        EpicTask epicWithId1 = new EpicTask(
                1,
                "1 epic",
                "epic with id 1",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId1);

        fileBackedTaskManager.getEpicTask(1);
        fileBackedTaskManager.getEpicTask(1);

        List<Task> expectedTaskList = new ArrayList<>(1);
        expectedTaskList.add(epicWithId1);

        List<Task> resultHistory= fileBackedTaskManager.getHistoryOfTasks();

        assertIterableEquals(expectedTaskList, resultHistory);
    }

    @Test
    public void shouldReturnListOfTwoEpicTaskWithId0and1AfterDeletingEpicTaskWithId2() {

        EpicTask epicWithId0 = new EpicTask(
                0,
                "0 epic",
                "epic with id 0",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId0);

        EpicTask epicWithId1 = new EpicTask(
                1,
                "1 epic",
                "epic with id 1",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId1);

        EpicTask epicWithId2 = new EpicTask(
                2,
                "2 epic",
                "epic with id 2",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId2);

        fileBackedTaskManager.getEpicTask(0);
        fileBackedTaskManager.getEpicTask(1);
        fileBackedTaskManager.getEpicTask(2);

        fileBackedTaskManager.removeEpicTask(2);

        List<Task> expectedTaskList = new ArrayList<>(2);
        expectedTaskList.add(epicWithId0);
        expectedTaskList.add(epicWithId1);

        List<Task> resultHistory= fileBackedTaskManager.getHistoryOfTasks();

        assertIterableEquals(expectedTaskList, resultHistory);
    }

    @Test
    public void shouldReturnListOfTwoEpicTasksWithId0and2AfterDeletingEpicTaskWithId1() {

        EpicTask epicWithId0 = new EpicTask(
                0,
                "0 epic",
                "epic with id 0",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId0);

        EpicTask epicWithId1 = new EpicTask(
                1,
                "1 epic",
                "epic with id 1",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId1);

        EpicTask epicWithId2 = new EpicTask(
                2,
                "2 epic",
                "epic with id 2",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId2);

        fileBackedTaskManager.getEpicTask(0);
        fileBackedTaskManager.getEpicTask(1);
        fileBackedTaskManager.getEpicTask(2);

        fileBackedTaskManager.removeEpicTask(1);

        List<Task> expectedTaskList = new ArrayList<>(2);
        expectedTaskList.add(epicWithId0);
        expectedTaskList.add(epicWithId2);

        List<Task> resultHistory= fileBackedTaskManager.getHistoryOfTasks();

        assertIterableEquals(expectedTaskList, resultHistory);
    }

    @Test
    public void shouldReturnListOfTwoEpicTasksWithId1and2AfterDeletingEpicTaskWithId0() {

        EpicTask epicWithId0 = new EpicTask(
                0,
                "0 epic",
                "epic with id 0",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId0);

        EpicTask epicWithId1 = new EpicTask(
                1,
                "1 epic",
                "epic with id 1",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId1);

        EpicTask epicWithId2 = new EpicTask(
                2,
                "2 epic",
                "epic with id 2",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(epicWithId2);

        fileBackedTaskManager.getEpicTask(0);
        fileBackedTaskManager.getEpicTask(1);
        fileBackedTaskManager.getEpicTask(2);

        fileBackedTaskManager.removeEpicTask(0);

        List<Task> expectedTaskList = new ArrayList<>(2);
        expectedTaskList.add(epicWithId1);
        expectedTaskList.add(epicWithId2);

        List<Task> resultHistory= fileBackedTaskManager.getHistoryOfTasks();

        assertIterableEquals(expectedTaskList, resultHistory);
    }

}