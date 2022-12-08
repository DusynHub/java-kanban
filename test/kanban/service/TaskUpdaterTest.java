package kanban.service;

import kanban.module.EpicTask;
import kanban.module.StatusName;
import kanban.module.SubTask;
import kanban.module.TaskType;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class TaskUpdaterTest {

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
    public void ShouldSetNewStatusForEpicWithoutSubtasks() {
        EpicTask cookRice = new EpicTask(
                0,
                "Приготовить рис",
                "Нужен гарнир из коричневого риса",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(cookRice);

        Iterator<Integer> it = fileBackedTaskManager.getEpicTaskStorage().keySet().iterator();

        int existingEpicId = -1;
        if (it.hasNext()) {
            existingEpicId = it.next();
        }

        EpicTask resTask = (EpicTask) fileBackedTaskManager.getEpicTaskStorage().get(existingEpicId);

        assertSame(StatusName.NEW, resTask.getStatus());
    }
    @Test
    public void ShouldSetNewStatusForEpicWithTwoNewSubtasks() {
        EpicTask cookRice = new EpicTask(
                0,
                "Приготовить рис",
                "Нужен гарнир из коричневого риса",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(cookRice);

        Iterator<Integer> it = fileBackedTaskManager.getEpicTaskStorage().keySet().iterator();

        int existingEpicId = -1;
        if (it.hasNext()) {
            existingEpicId = it.next();
        }

        SubTask subTaskCookRice1 = new SubTask(
                0, "Промыть рис",
                "Желательно 400 гр",
                StatusName.NEW, TaskType.SUBTASK, existingEpicId);
        SubTask subTaskCookRice2 = new SubTask(
                0, "Варить 10 минут",
                "Не уходить с кухни",
                StatusName.NEW, TaskType.SUBTASK, existingEpicId);

        fileBackedTaskManager.createSubTask(subTaskCookRice1);
        fileBackedTaskManager.createSubTask(subTaskCookRice2);

        EpicTask resTask = (EpicTask) fileBackedTaskManager.getEpicTaskStorage().get(existingEpicId);

        assertSame(StatusName.NEW, resTask.getStatus());
    }
    @Test
    public void ShouldSetInProgressStatusForEpicWithDoneAndNewSubstasks() {
        EpicTask cookRice = new EpicTask(
                0,
                "Приготовить рис",
                "Нужен гарнир из коричневого риса",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(cookRice);

        Iterator<Integer> it = fileBackedTaskManager.getEpicTaskStorage().keySet().iterator();

        int existingEpicId = -1;
        if (it.hasNext()) {
            existingEpicId = it.next();
        }

        SubTask subTaskCookRice1 = new SubTask(
                0, "Промыть рис",
                "Желательно 400 гр",
                StatusName.NEW, TaskType.SUBTASK, existingEpicId);
        SubTask subTaskCookRice2 = new SubTask(
                0, "Варить 10 минут",
                "Не уходить с кухни",
                StatusName.DONE, TaskType.SUBTASK, existingEpicId);

        fileBackedTaskManager.createSubTask(subTaskCookRice1);
        fileBackedTaskManager.createSubTask(subTaskCookRice2);

        EpicTask resTask = (EpicTask) fileBackedTaskManager.getEpicTaskStorage().get(existingEpicId);

        assertSame(StatusName.IN_PROGRESS, resTask.getStatus());
    }
    @Test
    public void ShouldSetInProgressStatusForEpicWithTwoInProgressSubtasks() {
        EpicTask cookRice = new EpicTask(
                0,
                "Приготовить рис",
                "Нужен гарнир из коричневого риса",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(cookRice);

        Iterator<Integer> it = fileBackedTaskManager.getEpicTaskStorage().keySet().iterator();

        int existingEpicId = -1;
        if (it.hasNext()) {
            existingEpicId = it.next();
        }

        SubTask subTaskCookRice1 = new SubTask(
                0, "Промыть рис",
                "Желательно 400 гр",
                StatusName.IN_PROGRESS, TaskType.SUBTASK, existingEpicId);
        SubTask subTaskCookRice2 = new SubTask(
                0, "Варить 10 минут",
                "Не уходить с кухни",
                StatusName.IN_PROGRESS, TaskType.SUBTASK, existingEpicId);

        fileBackedTaskManager.createSubTask(subTaskCookRice1);
        fileBackedTaskManager.createSubTask(subTaskCookRice2);

        EpicTask resTask = (EpicTask) fileBackedTaskManager.getEpicTaskStorage().get(existingEpicId);

        assertSame(StatusName.IN_PROGRESS, resTask.getStatus());
    }
    @Test
    public void ShouldSetDoneStatusForEpicWithTwoSubtasksHavingDoneStatus() {
        EpicTask cookRice = new EpicTask(
                0,
                "Приготовить рис",
                "Нужен гарнир из коричневого риса",
                TaskType.EPIC_TASK);

        fileBackedTaskManager.createEpicTask(cookRice);

        Iterator<Integer> it = fileBackedTaskManager.getEpicTaskStorage().keySet().iterator();

        int existingEpicId = -1;
        if (it.hasNext()) {
            existingEpicId = it.next();
        }

        SubTask subTaskCookRice1 = new SubTask(
                0, "Промыть рис",
                "Желательно 400 гр",
                StatusName.DONE, TaskType.SUBTASK, existingEpicId);
        SubTask subTaskCookRice2 = new SubTask(
                0, "Варить 10 минут",
                "Не уходить с кухни",
                StatusName.DONE, TaskType.SUBTASK, existingEpicId);

        fileBackedTaskManager.createSubTask(subTaskCookRice1);
        fileBackedTaskManager.createSubTask(subTaskCookRice2);

        EpicTask resTask = (EpicTask) fileBackedTaskManager.getEpicTaskStorage().get(existingEpicId);

        assertSame(StatusName.DONE, resTask.getStatus());
    }

}