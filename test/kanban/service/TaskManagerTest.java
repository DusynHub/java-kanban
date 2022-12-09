package kanban.service;

import kanban.module.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;

    static final ZoneId zone = ZoneId.of("Europe/Moscow");

    static RegularTask rt0;
    static RegularTask rt1;
    static RegularTask rt2;
    static SubTask sb1;
    static SubTask sb2;
    static SubTask sb3;
    static SubTask sb5;

    @BeforeAll
    public static void beforeAll(){
         rt0 = new RegularTask(
                0
                , "0 RegularTask"
                , "RegularTask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 8, 22, 0)
                , zone))
        );
        rt1 = new RegularTask(
                1
                , "1 RegularTask"
                , "RegularTask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(120))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 9, 9, 0)
                , zone))
        );
        rt2 = new RegularTask(
                2
                , "2 RegularTask"
                , "RegularTask with id 2"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(240))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 10, 7, 0)
                , zone))
        );
        sb1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 11, 9, 0)
                , zone))
                , 0
        );

        sb2 = new SubTask(
                2
                , "2 subtask"
                , "subtask with id 2"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 25, 9, 0)
                , zone))
                , 0
        );

        sb3 = new SubTask(
                3
                , "3 subtask"
                , "subtask with id 3"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 11, 12, 0)
                , zone))
                , 2
        );

        sb5 = new SubTask(
                5
                , "5 subtask"
                , "subtask with id 5"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 13, 13, 0)
                , zone))
                , 4
        );
    }
// тесты методов для RegularTask

    // тесты получения хранилища RegularTask
    @Test
    public void shouldReturnEmptyRegularTaskStorage() {
        assertTrue(taskManager.getRegularTaskStorage().isEmpty()
                , "Возвращено не пустое хранилище");
    }

    @Test
    public void shouldReturnRegularTaskStorageWithOneRegularTask() {

        HashMap<Integer, Task> expected = new HashMap<>(2);
        expected.put(0, rt0);

        taskManager.createRegularTask(rt0);
        assertEquals(expected
                , taskManager.getRegularTaskStorage()
                , "Обычной задачи нет в RegularTaskStorage");
    }

    // тесты создания RegularTask
    @Test
    public void shouldCreateRegularTaskInRegularTaskStorage() {

        taskManager.createRegularTask(rt0);
        RegularTask result = (RegularTask) taskManager.getRegularTaskStorage().get(0);
        assertEquals(rt0, result);
    }

    @Test
    public void shouldCreate3RegularTasksInRegularTaskStorage() {

        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskManager.createRegularTask(rt2);

        HashMap<Integer, Task> expected = new HashMap<>(3);
        expected.put(0, rt0);
        expected.put(1, rt1);
        expected.put(2, rt2);

        assertEquals(expected
                , taskManager.getRegularTaskStorage()
                , "RegularTaskStorages не совпадают");
    }

    // тесты удаления всех RegularTask
    @Test
    public void shouldReturnEmptyRegularTaskStorageAfterClearingAlreadyEmptyStorage() {
        taskManager.clearRegularTaskStorage();
        assertTrue(taskManager.getRegularTaskStorage().isEmpty()
                , "RegularTaskStorage не пустое");
    }

    @Test
    public void shouldReturnEmptyRegularTaskStorageAfterClearing() {

        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskManager.clearRegularTaskStorage();
        assertTrue(taskManager.getRegularTaskStorage().isEmpty());
    }

    // тесты получения RegularTask по id
    @Test
    public void shouldReturnNullAfterCallingNonExistingRegularTask() {
        assertNull(taskManager.getRegularTask(0));
    }

    @Test
    public void shouldReturnRegularTaskWithId0() {
        RegularTask regularWithId0 = new RegularTask(
                0
                , "0 regular"
                , "regular with id 0"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
        );
        taskManager.createRegularTask(regularWithId0);
        assertEquals(regularWithId0, taskManager.getRegularTask(0));
    }

    // тесты обновления RegularTask
    @Test
    public void shouldReturnMessageOnUpdateIfRegularTaskStorageIsEmpty() {

        RegularTask updateRegularWithId0 = new RegularTask(
                121
                , "0 REGULAR"
                , "REGULAR with id 0"
                , StatusName.DONE
                , TaskType.REGULAR_TASK
        );
        String expectedMessage
                = "Задача отсутствует c id = " + updateRegularWithId0.getId()
                + ". Сначала создайте задачу с соответвующим id. Обновление невозможно.";

        String messageIfTaskToUpgradeDoesNotExist
                = taskManager.updateRegularTask(updateRegularWithId0);

        assertEquals(expectedMessage
                    , messageIfTaskToUpgradeDoesNotExist
                    , "Сообщения не совпадают");
    }

    @Test
    public void shouldReturnMessageIfRegularTaskToUpgradeDoNotExist() {

        taskManager.createRegularTask(rt0);

        RegularTask updateRegularWithId0 = new RegularTask(
                121
                , "0 REGULAR"
                , "REGULAR with id 0"
                , StatusName.DONE
                , TaskType.REGULAR_TASK
        );
        String expectedMessage
                = "Задача отсутствует c id = " + updateRegularWithId0.getId()
                + ". Сначала создайте задачу с соответвующим id. Обновление невозможно.";


        String messageIfTaskToUpgradeDoesNotExist
                = taskManager.updateRegularTask(updateRegularWithId0);

        assertEquals(expectedMessage
                , messageIfTaskToUpgradeDoesNotExist);
    }

    @Test
    public void shouldUpgradeRegularTaskWithUpdatedStatus() {

        taskManager.createRegularTask(rt0);

        RegularTask updateRegularWithId0 = new RegularTask(
                0
                , "0 REGULAR"
                , "REGULAR with id 0"
                , StatusName.DONE
                , TaskType.REGULAR_TASK
        );
        taskManager.updateRegularTask(updateRegularWithId0);

        assertEquals(updateRegularWithId0
                    , taskManager.getRegularTask(0)
                    , "Задача не обновлена");
    }

    // тесты удаления RegularTask по id
    @Test
    public void shouldReturnMessageOnDeleteIfRegularTaskStorageIsEmpty() {

        String expected = "Задача с id = '" + 1
                + "' отсутствует. Сначала создайте задачу с соответвующим regularId. "
                + "Удаление невозможно";

        String messageIfRegularTaskToDeleteDoNotExist = taskManager.removeRegularTask(1);
        assertEquals(expected
            , messageIfRegularTaskToDeleteDoNotExist
            ,"Неправильное сообщение при попытке удаления задачи при отсутствии задач");
    }

    @Test
    public void shouldReturnMessageIfRegularTaskToDeleteDoNotExist() {

        taskManager.createRegularTask(rt0);
        String expected = "Задача с id = '" + 1
                + "' отсутствует. Сначала создайте задачу с соответвующим regularId. "
                + "Удаление невозможно";

        String messageIfRegularTaskToDeleteDoNotExist = taskManager.removeRegularTask(1);
        assertEquals(expected
            , messageIfRegularTaskToDeleteDoNotExist
            ,"Неправильное сообщение при попытке удаления несуществующей задачи");
    }

    @Test
    public void shouldRemoveRegularTaskFromStorage() {

        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskManager.createRegularTask(rt2);
        taskManager.removeRegularTask(1);

        HashMap<Integer, Task> expected = new HashMap<>(2);
        expected.put(0, rt0);
        expected.put(2, rt2);

        assertEquals(expected, taskManager.getRegularTaskStorage());
    }


// тесты методов для EpicTask

    // тесты получения хранилища EpicTask
    @Test
    public void shouldReturnEmptyEpicTaskStorage() {
        assertTrue(taskManager.getEpicTaskStorage().isEmpty());
    }

    @Test
    public void shouldReturnEpicTaskStorageWithOneEpicTask() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );

        HashMap<Integer, Task> expected = new HashMap<>(2);
        expected.put(0, epicWithId0);

        taskManager.createEpicTask(epicWithId0);
        assertEquals(expected, taskManager.getEpicTaskStorage());
    }

    // тесты создания EpicTask
    @Test
    public void shouldCreateEpicTaskInEpicTaskStorage() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );

        taskManager.createEpicTask(epicWithId0);
        EpicTask result = (EpicTask) taskManager.getEpicTaskStorage().get(0);
        assertEquals(epicWithId0, result);
    }

    @Test
    public void shouldCreate3EpicTasksInEpicTaskStorage() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        EpicTask epicWithId1 = new EpicTask(
                1
                , "1 epic"
                , "epic with id 1"
                , TaskType.EPIC_TASK
        );
        EpicTask epicWithId2 = new EpicTask(
                2
                , "2 epic"
                , "epic with id 2"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);
        taskManager.createEpicTask(epicWithId1);
        taskManager.createEpicTask(epicWithId2);


        HashMap<Integer, Task> expected = new HashMap<>(3);
        expected.put(0, epicWithId0);
        expected.put(1, epicWithId1);
        expected.put(2, epicWithId2);

        assertEquals(expected, taskManager.getEpicTaskStorage());
    }

    // тесты удалеия всех EpicTask
    @Test
    public void shouldReturnEmptyEpicTaskStorageAfterClearingAlreadyEmptyStorage() {
        taskManager.clearEpicTaskStorage();
        assertTrue(taskManager.getEpicTaskStorage().isEmpty());
    }

    @Test
    public void shouldReturnEmptyEpicTaskStorageAfterClearing() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        EpicTask epicWithId1 = new EpicTask(
                1
                , "1 epic"
                , "epic with id 1"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);
        taskManager.createEpicTask(epicWithId1);
        taskManager.clearEpicTaskStorage();

        assertTrue(taskManager.getEpicTaskStorage().isEmpty());
    }

    // тесты получения EpicTask по id
    @Test
    public void shouldReturnNullAfterCallingNonExistingEpicTask() {
        assertNull(taskManager.getEpicTask(0));
    }

    @Test
    public void shouldReturnEpicTaskWithId0() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);
        assertEquals(epicWithId0, taskManager.getEpicTask(0));
    }

    @Test
    public void shouldReturnDurationAsSumOfSubTasksDuration() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );


        taskManager.createEpicTask(epicWithId0);
        taskManager.createSubTask(sb1);
        taskManager.createSubTask(sb2);
        assertEquals(Optional.of(Duration.ofMinutes(120)), taskManager.getEpicTask(0).getDuration());
    }

    @Test
    public void shouldReturnStartTimeAsSubTaskStart() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );

        taskManager.createEpicTask(epicWithId0);
        taskManager.createSubTask(sb1);
        taskManager.createSubTask(sb2);
        Optional<ZonedDateTime> expected = Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 11, 9, 0)
                , zone));
        assertEquals(expected
                , epicWithId0.getStartTime()
                ,"Неправльное время начала Epic");
    }

    // тесты обновления EpicTask
    @Test
    public void shouldReturnMessageOnUpdateIfEpicTaskStorageIsEmpty() {


        EpicTask updateEpicWithId0 = new EpicTask(
                121
                , "0 epic"
                , "новое описание"
                , TaskType.EPIC_TASK
        );


        String expectedMessage
                = "Эпик задача отсутствует c id = " + updateEpicWithId0.getId()
                + ". Сначала создайте задачу с соответвующим id. Обновление невозможно.";


        String messageIfTaskToUpgradeDoesNotExist
                = taskManager.updateEpicTask(updateEpicWithId0);

        assertEquals(expectedMessage, messageIfTaskToUpgradeDoesNotExist);
    }

    @Test
    public void shouldReturnMessageIEpicTaskToUpgradeDoNotExist() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        EpicTask updateEpicWithId0 = new EpicTask(
                99999
                , "0 epic"
                , "новое описание"
                , TaskType.EPIC_TASK
        );
        String expectedMessage
                = "Эпик задача отсутствует c id = " + updateEpicWithId0.getId()
                + ". Сначала создайте задачу с соответвующим id. Обновление невозможно.";


        String messageIfTaskToUpgradeDoesNotExist
                = taskManager.updateEpicTask(updateEpicWithId0);

        assertEquals(expectedMessage, messageIfTaskToUpgradeDoesNotExist);
    }

    @Test
    public void shouldUpgradeEpicTaskWithUpdatedStatus() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        EpicTask updateEpicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "новое описание"
                , TaskType.EPIC_TASK
        );
        taskManager.updateEpicTask(updateEpicWithId0);

        assertEquals(updateEpicWithId0, taskManager.getEpicTask(0));
    }

    @Test
    public void shouldReturnInProgressStatusForEpicTask() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );

        SubTask subTaskWithId1 = new SubTask(
                1
                , "0 epic"
                , "epic with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createEpicTask(epicWithId0);
        taskManager.createSubTask(subTaskWithId1);

        assertSame(StatusName.IN_PROGRESS, taskManager.getEpicTask(0).getStatus());
    }

    // тесты удаления EpicTask по id
    @Test
    public void shouldReturnMessageOnDeleteIfEpicTaskStorageIsEmpty() {

        String expected = "Задача с id = '" + 1
                + "' отсутствует. Сначала создайте задачу с соответвующим epicId. "
                + "Удаление невозможно";

        String messageIfEpicTaskToDeleteDoNotExist = taskManager.removeEpicTask(1);
        assertEquals(expected, messageIfEpicTaskToDeleteDoNotExist);
    }

    @Test
    public void shouldReturnMessageIfEpicTaskToDeleteDoNotExist() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );

        taskManager.createEpicTask(epicWithId0);

        String expected = "Задача с id = '" + 1
                + "' отсутствует. Сначала создайте задачу с соответвующим epicId. "
                + "Удаление невозможно";

        String messageIfEpicTaskToDeleteDoNotExist = taskManager.removeEpicTask(1);
        assertEquals(expected, messageIfEpicTaskToDeleteDoNotExist);
    }

    @Test
    public void shouldRemoveEpicTaskFromStorage() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        EpicTask epicWithId1 = new EpicTask(
                1
                , "1 epic"
                , "epic with id 1"
                , TaskType.EPIC_TASK
        );
        EpicTask epicWithId2 = new EpicTask(
                2
                , "2 epic"
                , "epic with id 2"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);
        taskManager.createEpicTask(epicWithId1);
        taskManager.createEpicTask(epicWithId2);

        taskManager.removeEpicTask(1);

        HashMap<Integer, Task> expected = new HashMap<>(2);
        expected.put(0, epicWithId0);
        expected.put(2, epicWithId2);

        assertEquals(expected, taskManager.getEpicTaskStorage());
    }


// тесты методов для SubTask

    // тесты получения хранилища SubTask
    @Test
    public void shouldReturnEmptySubTaskStorage() {
        assertTrue(taskManager.getSubTaskStorage().isEmpty());
    }

    @Test
    public void shouldReturnSubTaskStorageWithOneSubTask() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        taskManager.createSubTask(sb1);

        HashMap<Integer, Task> expected = new HashMap<>(2);
        expected.put(1, sb1);

        assertEquals(expected, taskManager.getSubTaskStorage());
    }

    @Test
    public void shouldReturnSubTaskStorageFromEpicWithOneSubTask() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        taskManager.createSubTask(sb1);

        HashMap<Integer, Task> expected = new HashMap<>(2);
        expected.put(1, sb1);

        assertEquals(expected, taskManager.getSubTaskStorageFromEpic(0).getStorage());
    }

    // тесты создания SubTask
    @Test
    public void shouldReturnMessageOnCreatingSubTaskIfEpicTaskDoNotExist() {

        SubTask subTaskWithId1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );


        String expected =
                "Подзадача не может быть создана. "
                        + "Такой эпик задачи нет. Позадача не была создана. Возвращено null";

        String result = taskManager.createSubTask(subTaskWithId1);

        assertEquals(expected, result);
    }

    @Test
    public void shouldCreateSubTaskInSubTaskStorage() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);

        SubTask result = (SubTask) taskManager.getSubTaskStorage().get(1);
        assertEquals(subTaskWithId1, result);
    }

    @Test
    public void shouldCreateSubTaskInEpic() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);

        SubTask result
                = (SubTask) taskManager.getSubTaskStorageFromEpic(0).getStorage().get(1);
        assertEquals(subTaskWithId1, result);
    }

    @Test
    public void shouldCreate3SubTasksInSubTaskStorage() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "0 subtask"
                , "subtask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);

        EpicTask epicWithId2 = new EpicTask(
                2
                , "2 epic"
                , "epic with id 2"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId2);

        SubTask subTaskWithId3 = new SubTask(
                3
                , "3 subtask"
                , "subtask with id 3"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 2
        );
        taskManager.createSubTask(subTaskWithId3);

        EpicTask epicWithId4 = new EpicTask(
                4
                , "4 epic"
                , "epic with id 4"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId4);

        SubTask subTaskWithId5 = new SubTask(
                5
                , "5 subtask"
                , "subtask with id 5"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 4
        );
        taskManager.createSubTask(subTaskWithId5);

        HashMap<Integer, Task> expected = new HashMap<>(3);
        expected.put(1, subTaskWithId1);
        expected.put(3, subTaskWithId3);
        expected.put(5, subTaskWithId5);

        assertEquals(expected, taskManager.getSubTaskStorage());
    }

    // тесты удаления всех SubTask
    @Test
    public void shouldReturnEmptySubTaskStorageAfterClearingAlreadyEmptyStorage() {
        taskManager.clearSubTaskStorage();
        assertTrue(taskManager.getSubTaskStorage().isEmpty());
    }

    @Test
    public void shouldReturnEmptySubTaskStorageAfterClearing() {
        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "0 subtask"
                , "subtask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);

        EpicTask epicWithId2 = new EpicTask(
                2
                , "2 epic"
                , "epic with id 2"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId2);

        SubTask subTaskWithId3 = new SubTask(
                3
                , "3 subtask"
                , "subtask with id 3"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 2
        );
        taskManager.createSubTask(subTaskWithId3);

        EpicTask epicWithId4 = new EpicTask(
                4
                , "4 epic"
                , "epic with id 4"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId4);

        SubTask subTaskWithId5 = new SubTask(
                5
                , "5 subtask"
                , "subtask with id 5"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 4
        );
        taskManager.createSubTask(subTaskWithId5);
        taskManager.clearSubTaskStorage();
        assertTrue(taskManager.getSubTaskStorage().isEmpty());
    }

    @Test
    public void shouldReturnEmptySubTaskStorageInEpicAfterClearing() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);

        EpicTask epicWithId2 = new EpicTask(
                2
                , "2 epic"
                , "epic with id 2"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId2);

        SubTask subTaskWithId3 = new SubTask(
                3
                , "3 subtask"
                , "subtask with id 3"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 2
        );
        taskManager.createSubTask(subTaskWithId3);

        EpicTask epicWithId4 = new EpicTask(
                4
                , "4 epic"
                , "epic with id 4"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId4);

        SubTask subTaskWithId5 = new SubTask(
                5
                , "5 subtask"
                , "subtask with id 5"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 4
        );
        taskManager.createSubTask(subTaskWithId5);
        taskManager.clearSubTaskStorage();

        assertTrue(taskManager.getSubTaskStorageFromEpic(4).getStorage().isEmpty());
    }

    // тесты получения SubTask по id
    @Test
    public void shouldReturnNullAfterCallingNonExistingSubTask() {
        assertNull(taskManager.getSubTask(0));
    }

    @Test
    public void shouldReturnSubTaskWithId1FromManagersSubTaskStorage() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);
        assertEquals(subTaskWithId1, taskManager.getSubTask(1));
    }

    @Test
    public void shouldReturnSubTaskWithId1FromEpic() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);

        assertEquals(
                subTaskWithId1
                , taskManager.getSubTaskStorageFromEpic(0).getStorage().get(1)
        );
    }

    // тесты обновления SubTask
    @Test
    public void shouldReturnMessageOnUpdateIfSubTaskStorageIsEmpty() {

        SubTask updateSubTaskWithId1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );

        String expectedMessage
                = "Подзадача отсутствует c id = " + updateSubTaskWithId1.getId()
                + ". Сначала создайте подзадачу с соответвующим id. Обновление невозможно.";


        String messageIfTaskToUpgradeDoesNotExist
                = taskManager.updateSubTask(updateSubTaskWithId1);

        assertEquals(expectedMessage, messageIfTaskToUpgradeDoesNotExist);
    }

    @Test
    public void shouldReturnMessageIfSubTaskToUpgradeDoNotExist() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "0 subtask"
                , "subtask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);

        SubTask updateSubTaskWithId1 = new SubTask(
                124
                , "124 subtask"
                , "subtask with id 124"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );

        String expectedMessage
                = "Подзадача отсутствует c id = " + updateSubTaskWithId1.getId()
                + ". Сначала создайте подзадачу с соответвующим id. Обновление невозможно.";


        String messageIfTaskToUpgradeDoesNotExist
                = taskManager.updateSubTask(updateSubTaskWithId1);

        assertEquals(expectedMessage, messageIfTaskToUpgradeDoesNotExist);
    }

    @Test
    public void shouldUpgradeSubTaskWithUpdatedStatus() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);

        SubTask updateSubTaskWithId1 = new SubTask(
                1
                , "1 UPDATED subtask"
                , "subtask with id 1"
                , StatusName.DONE
                , TaskType.SUBTASK
                , 0
        );

        taskManager.updateSubTask(updateSubTaskWithId1);

        assertEquals(updateSubTaskWithId1, taskManager.getSubTask(1));
    }

    // тесты удаления SubTask по id
    @Test
    public void shouldReturnMessageOnDeleteIfSubTaskStorageIsEmpty() {

        String expected = "Подзадача с id = '" + 1
                + "' отсутствует. Сначала создайте задачу с соответвующим Id. Удаление невозможно";

        String messageIfEpicTaskToDeleteDoNotExist = taskManager.removeSubTask(1);
        assertEquals(expected, messageIfEpicTaskToDeleteDoNotExist);
    }

    @Test
    public void shouldReturnMessageIfSubTaskToDeleteDoNotExist() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);

        String expected = "Подзадача с id = '" + 2
                + "' отсутствует. Сначала создайте задачу с соответвующим Id. Удаление невозможно";

        String messageIfEpicTaskToDeleteDoNotExist = taskManager.removeSubTask(2);
        assertEquals(expected, messageIfEpicTaskToDeleteDoNotExist);
    }

    @Test
    public void shouldRemoveSubTaskFromStorageInManager() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "0 subtask"
                , "subtask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);

        EpicTask epicWithId2 = new EpicTask(
                2
                , "2 epic"
                , "epic with id 2"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId2);

        SubTask subTaskWithId3 = new SubTask(
                3
                , "3 subtask"
                , "subtask with id 3"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 2
        );
        taskManager.createSubTask(subTaskWithId3);

        EpicTask epicWithId4 = new EpicTask(
                4
                , "4 epic"
                , "epic with id 4"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId4);

        SubTask subTaskWithId5 = new SubTask(
                5
                , "5 subtask"
                , "subtask with id 5"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 4
        );
        taskManager.createSubTask(subTaskWithId5);
        taskManager.removeSubTask(3);

        HashMap<Integer, Task> expected = new HashMap<>(3);
        expected.put(1, subTaskWithId1);
        expected.put(5, subTaskWithId5);

        assertEquals(expected, taskManager.getSubTaskStorage());
    }

    @Test
    public void shouldRemoveSubTaskFromStorageInEpic() {

        EpicTask epicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId0);

        SubTask subTaskWithId1 = new SubTask(
                1
                , "0 subtask"
                , "subtask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 0
        );
        taskManager.createSubTask(subTaskWithId1);

        EpicTask epicWithId2 = new EpicTask(
                2
                , "2 epic"
                , "epic with id 2"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId2);

        SubTask subTaskWithId3 = new SubTask(
                3
                , "3 subtask"
                , "subtask with id 3"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 2
        );
        taskManager.createSubTask(subTaskWithId3);

        EpicTask epicWithId4 = new EpicTask(
                4
                , "4 epic"
                , "epic with id 4"
                , TaskType.EPIC_TASK
        );
        taskManager.createEpicTask(epicWithId4);

        SubTask subTaskWithId5 = new SubTask(
                5
                , "5 subtask"
                , "subtask with id 5"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 4
        );
        taskManager.createSubTask(subTaskWithId5);
        taskManager.removeSubTask(3);

        assertTrue(taskManager.getSubTaskStorageFromEpic(2).getStorage().isEmpty());
    }

}