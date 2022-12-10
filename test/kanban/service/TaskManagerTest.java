package kanban.service;

import kanban.module.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;

    static final ZoneId zone = ZoneId.of("Europe/Moscow");

    static RegularTask rt0;
    static RegularTask rt1;
    static RegularTask rt2;
    static RegularTask rt3;
    static RegularTask rt4;
    static RegularTask rt5;
    static RegularTask rt6;
    static RegularTask rt7;
    static RegularTask rt8;
    static RegularTask rt01;
    static SubTask st1;
    static SubTask st2;
    static SubTask st3;
    static SubTask st5;
    static SubTask st02SameStartTimeAsRt0;
    static EpicTask et0;
    static EpicTask et1;
    static EpicTask et2;
    static EpicTask et3;
    static EpicTask et4;

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

        rt3 = new RegularTask(
                3
                , "3 RegularTask"
                , "RegularTask with id 3"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(90))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 18, 11, 0)
                , zone))
        );

        rt4 = new RegularTask(
                4
                , "4 RegularTask"
                , "RegularTask with id 4"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(120))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 19, 13, 0)
                , zone))
        );

        rt5 = new RegularTask(
                5
                , "5 RegularTask"
                , "RegularTask with id 5"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(30))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 20, 8, 0)
                , zone))
        );

        rt6 = new RegularTask(
                6
                , "6 RegularTask"
                , "RegularTask with id 6"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(45))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 21, 15, 0)
                , zone))
        );

        rt7 = new RegularTask(
                7
                , "7 RegularTask"
                , "RegularTask with id 7"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(240))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 22, 16, 0)
                , zone))
        );

        rt8 = new RegularTask(
                8
                , "8 RegularTask"
                , "RegularTask with id 8"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(45))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 11, 23, 8, 0)
                , zone))
        );

        rt01 = new RegularTask(
                0
                , "0 RegularTask"
                , "RegularTask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 8, 22, 1)
                , zone))
        );


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

        et2 = new EpicTask(
                2
                , "2 epic"
                , "epic with id 2"
                , TaskType.EPIC_TASK
        );

        et3 = new EpicTask(
                3
                , "3 epic"
                , "epic with id 3"
                , TaskType.EPIC_TASK
        );

        et4 = new EpicTask(
                4
                , "4 epic"
                , "epic with id 4"
                , TaskType.EPIC_TASK
        );
        st1 = new SubTask(
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
        st2 = new SubTask(
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
        st3 = new SubTask(
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
        st5 = new SubTask(
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

        st02SameStartTimeAsRt0 = new SubTask(
                2
                , "2 subtask"
                , "subtask with id 2"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 8, 21, 59)
                , zone))
                , 1
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
    public void shouldNotCreateRegularTaskWithSameDate() {

        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt01);

        HashMap<Integer, Task> expected = new HashMap<>(1);
        expected.put(0, rt0);

        HashMap<Integer, Task> result = taskManager.getRegularTaskStorage();

        assertEquals(expected
                , result
                , "RegularTaskStorages не совпадают");
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

        HashMap<Integer, Task> result = taskManager.getRegularTaskStorage();

        assertEquals(expected
                , result
                , "RegularTaskStorages не совпадают");
    }

    // тесты удаления всех RegularTask
    @Test
    public void shouldReturnEmptyRegularTaskStorageAfterClearingAlreadyEmptyStorage() {
        taskManager.clearRegularTaskStorage();
        assertTrue(taskManager.getRegularTaskStorage().isEmpty()
                , "RegularTaskStorage не пустое при очистке уже пустого сообщения");
    }

    @Test
    public void shouldReturnEmptyRegularTaskStorageAfterClearing() {

        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskManager.clearRegularTaskStorage();
        assertTrue(taskManager.getRegularTaskStorage().isEmpty()
                , "RegularTaskStorage не пустое после очистке") ;
    }

    // тесты получения RegularTask по id
    @Test
    public void shouldReturnNullAfterCallingNonExistingRegularTask() {
        assertNull(taskManager.getRegularTask(0)
                , "При вызове RegularTask из пустого хранилища возращено не null");
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
        assertEquals(regularWithId0, taskManager.getRegularTask(0)
                , "Возвращённое RegularTask не соответствует ожидаемой RegularTask");
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
    public void shouldReturnMessageIfRegularTaskToUpdateHaveNotFreeStartTime() {

        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        RegularTask updateRegularWithId0 = new RegularTask(
                0
                , "0 REGULAR"
                , "REGULAR with id 0"
                , StatusName.DONE
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(120))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 9, 9, 0)
                , zone))
        );
        String expectedMessage
                = "Время выполнения обновлённого RegularTask пересекается с имеющимися заданиями"
                + "Здача не была обновлена";

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
        taskManager.createRegularTask(rt1);
        taskManager.createEpicTask(et2);
        taskManager.createSubTask(st3);
        taskManager.createRegularTask(rt4);
        taskManager.createRegularTask(rt5);
        taskManager.createRegularTask(rt6);
        taskManager.createRegularTask(rt7);

        RegularTask updateRegularWithId0 = new RegularTask(
                0
                , "0 REGULAR"
                , "REGULAR with id 0"
                , StatusName.DONE
                , TaskType.REGULAR_TASK
        );
        taskManager.updateRegularTask(updateRegularWithId0);

        TreeSet<Task> expected = new TreeSet<>(new Comparator<Task>() {
            @Override
            public int compare(Task obj1, Task obj2) {
                Optional<ZonedDateTime> t1 = obj1.getStartTime();
                Optional<ZonedDateTime> t2 = obj2.getStartTime();

                if (t1.isPresent() && t2.isPresent()) {
                    return t1.get().compareTo(t2.get());
                } else if (t1.isPresent()) {
                    return -1;
                } else if (t2.isPresent()) {
                    return 1;
                } else {
                    return 0;
                }
            }
            });

        expected.add(updateRegularWithId0);
        expected.add(rt1);
        expected.add(st3);
        expected.add(rt4);
        expected.add(rt5);
        expected.add(rt6);
        expected.add(rt7);

        assertEquals(updateRegularWithId0
                    , taskManager.getRegularTask(0)
                    , "Задача не обновлена");

        assertEquals(expected, taskManager.getPrioritized());
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
        assertTrue(taskManager.getEpicTaskStorage().isEmpty()
                , "Возвращено не пустое EpicTaskStorage при вызове пустого EpicTaskStorage");
    }

    @Test
    public void shouldReturnEpicTaskStorageWithOneEpicTask() {

        HashMap<Integer, Task> expected = new HashMap<>(2);
        expected.put(0, et0);

        taskManager.createEpicTask(et0);
        assertEquals(expected, taskManager.getEpicTaskStorage()
                ,"Полученное EpicTaskStorage не соответствует ожидаемому");
    }

    // тесты создания EpicTask
    @Test
    public void shouldCreateEpicTaskInEpicTaskStorage() {

        taskManager.createEpicTask(et0);
        EpicTask result = (EpicTask) taskManager.getEpicTaskStorage().get(0);
        assertEquals(et0, result
                ,"Созданное EpicTaskStorage не соответствует ожидаемому с 1 задачей");
    }

    @Test
    public void shouldCreate3EpicTasksInEpicTaskStorage() {

        taskManager.createEpicTask(et0);
        taskManager.createEpicTask(et1);
        taskManager.createEpicTask(et2);


        HashMap<Integer, Task> expected = new HashMap<>(3);
        expected.put(0, et0);
        expected.put(1, et1);
        expected.put(2, et2);

        assertEquals(expected, taskManager.getEpicTaskStorage()
                , "Созданное EpicTaskStorage не соответствует ожидаемому с 3 задачами");
    }

    // тесты удалеия всех EpicTask
    @Test
    public void shouldReturnEmptyEpicTaskStorageAfterClearingAlreadyEmptyStorage() {
        taskManager.clearEpicTaskStorage();
        assertTrue(taskManager.getEpicTaskStorage().isEmpty()
                , "Возвращено непустое EpicTaskStorage после очистки уже пустого EpicTaskStorage");
    }

    @Test
    public void shouldReturnEmptyEpicTaskStorageAfterClearing() {

        taskManager.createEpicTask(et0);
        taskManager.createEpicTask(et1);
        taskManager.clearEpicTaskStorage();

        assertTrue(taskManager.getEpicTaskStorage().isEmpty()
                , "Возвращено непустое EpicTaskStorage после очистки");
    }

    // тесты получения EpicTask по id
    @Test
    public void shouldReturnNullAfterCallingNonExistingEpicTask() {
        assertNull(taskManager.getEpicTask(0)
                , "Возвращено не Null после вызова несуществующей EpicTask");
    }

    @Test
    public void shouldReturnEpicTaskWithId0() {
        taskManager.createEpicTask(et0);
        assertEquals(et0, taskManager.getEpicTask(0)
                , "Возвращенная EpicTask без SubTask не соответствует ожидаемой");
    }

    @Test
    public void shouldReturnDurationAsSumOfSubTasksDuration() {
        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);
        taskManager.createSubTask(st2);
        assertEquals(Optional.of(Duration.ofMinutes(120))
                , taskManager.getEpicTask(0).getDuration()
                , "Возвращенная EpicTask c двуя SubTask не соответствует ожидаемой");
    }

    @Test
    public void shouldReturnStartTimeAsSubTaskStart() {
        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);
        taskManager.createSubTask(st2);
        Optional<ZonedDateTime> expected = Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 11, 9, 0)
                , zone));
        assertEquals(expected
                , et0.getStartTime()
                ,"Неправльное время начала EpicTask");
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

        taskManager.createEpicTask(et0);

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

        assertEquals(expectedMessage
                , messageIfTaskToUpgradeDoesNotExist
                , "Возращеное сообщение при попытке обновить EpicTask не соответствует"
                        + "ожидаемому");
    }

    @Test
    public void shouldUpgradeEpicTaskWithUpdatedStatus() {
        taskManager.createEpicTask(et0);
        EpicTask updateEpicWithId0 = new EpicTask(
                0
                , "0 epic"
                , "новое описание"
                , TaskType.EPIC_TASK
        );
        taskManager.updateEpicTask(updateEpicWithId0);
        assertEquals(updateEpicWithId0
                , taskManager.getEpicTask(0)
                , "Обновленая EpicTask не соответствует ожидаемой");
    }

    @Test
    public void shouldReturnInProgressStatusForEpicTask() {
        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);
        assertSame(StatusName.IN_PROGRESS
                , taskManager.getEpicTask(0).getStatus()
                , "Статуст EpicTask не соответствует ожидаемому");
    }

    // тесты удаления EpicTask по id
    @Test
    public void shouldReturnMessageOnDeleteIfEpicTaskStorageIsEmpty() {

        String expected = "Задача с id = '" + 1
                + "' отсутствует. Сначала создайте задачу с соответвующим epicId. "
                + "Удаление невозможно";

        String messageIfEpicTaskToDeleteDoNotExist = taskManager.removeEpicTask(1);
        assertEquals(expected
                , messageIfEpicTaskToDeleteDoNotExist
                , "Некорректное сообщение при попытке удалить задачиу при пустом"
                        + "EpicTaskStorage");
    }

    @Test
    public void shouldReturnMessageIfEpicTaskToDeleteDoNotExist() {
        taskManager.createEpicTask(et0);

        String expected = "Задача с id = '" + 1
                + "' отсутствует. Сначала создайте задачу с соответвующим epicId. "
                + "Удаление невозможно";

        String messageIfEpicTaskToDeleteDoNotExist = taskManager.removeEpicTask(1);
        assertEquals(expected
                , messageIfEpicTaskToDeleteDoNotExist
                , "Некорректное сообщение при попытке удалить несуществующую EpicTask при пустом");
    }

    @Test
    public void shouldRemoveEpicTaskFromStorage() {

        taskManager.createEpicTask(et0);
        taskManager.createEpicTask(et1);
        taskManager.createEpicTask(et2);

        taskManager.removeEpicTask(1);

        HashMap<Integer, Task> expected = new HashMap<>(2);
        expected.put(0, et0);
        expected.put(2, et2);

        assertEquals(expected
                , taskManager.getEpicTaskStorage()
                , "Хранилище EpicTaskStorage после удаления EpicTask не соответствует"
                        + "ожидаемому");
    }


// тесты методов для SubTask

    // тесты получения хранилища SubTask
    @Test
    public void shouldReturnEmptySubTaskStorage() {
        assertTrue(taskManager.getSubTaskStorage().isEmpty()
                , "Возвращено не пустое SubTaskStorage");
    }

    @Test
    public void shouldReturnSubTaskStorageWithOneSubTask() {

        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);
        HashMap<Integer, Task> expected = new HashMap<>(2);
        expected.put(1, st1);
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

        taskManager.createSubTask(st1);

        HashMap<Integer, Task> expected = new HashMap<>(2);
        expected.put(1, st1);

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
                "Подзадача c id" + subTaskWithId1.getId() + " не может быть создана. "
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

    @Test
    public void shouldNotCreateInSubTaskWithNearDateWithRt0() {

        taskManager.createEpicTask(et0);
        taskManager.createRegularTask(rt1);
        taskManager.createSubTask(st02SameStartTimeAsRt0);
        taskManager.createSubTask(st2);

        HashMap<Integer, Task> expected = new HashMap<>(1);
        expected.put(2, st2);

        HashMap<Integer, Task> result = taskManager.getSubTaskStorage();

        assertEquals(expected
                , result
                , "SubTaskStorages не совпадают после попытки создания подзадачи"
                        + ", со временем выполнения пересекающейся с обычной задачей");
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


        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);

        assertEquals(
                st1
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

        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);

        SubTask updateSubTaskWithId1 = new SubTask(
                124
                , "124 subtask"
                , "subtask with id 124"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , 124
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


        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);

        SubTask updateSubTaskWithId1 = new SubTask(
                1
                , "1 UPDATED subtask"
                , "subtask with id 1"
                , StatusName.DONE
                , TaskType.SUBTASK
                , 0
        );
        taskManager.updateSubTask(updateSubTaskWithId1);

        assertEquals(updateSubTaskWithId1, taskManager.getSubTask(1)
                , "Обновлянная подзадача не равна");
    }

    // тесты удаления SubTask по id
    @Test
    public void shouldReturnMessageOnDeleteIfSubTaskStorageIsEmpty() {

        String expected = "Подзадача с id = '" + 1
                + "' отсутствует. Сначала создайте задачу с соответвующим Id. Удаление невозможно";

        String messageIfEpicTaskToDeleteDoNotExist = taskManager.removeSubTask(1);
        assertEquals(expected, messageIfEpicTaskToDeleteDoNotExist
                , "При удалении подзадачи из пустого хранилища получено некорреткное сообщение");
    }

    @Test
    public void shouldReturnMessageIfSubTaskToDeleteDoNotExist() {

        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);

        String expected = "Подзадача с id = '" + 2
                + "' отсутствует. Сначала создайте задачу с соответвующим Id. Удаление невозможно";

        String messageIfEpicTaskToDeleteDoNotExist = taskManager.removeSubTask(2);
        assertEquals(expected, messageIfEpicTaskToDeleteDoNotExist
                ,"При удалении несуществующей подзадачи удалена получено некорректное сообщение");
    }

    @Test
    public void shouldRemoveSubTaskFromStorageInManager() {

        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);

        taskManager.createEpicTask(et2);
        taskManager.createSubTask(st3);

        taskManager.createEpicTask(et4);
        taskManager.createSubTask(st5);

        taskManager.removeSubTask(3);

        HashMap<Integer, Task> expected = new HashMap<>(3);
        expected.put(1, st1);
        expected.put(5, st5);

        assertEquals(expected, taskManager.getSubTaskStorage()
                ,"Хранилище подзадач в менеджене после удаления не пустое");
    }

    @Test
    public void shouldRemoveSubTaskFromStorageInEpic() {

        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);

        taskManager.createEpicTask(et2);
        taskManager.createSubTask(st3);


        taskManager.createEpicTask(et4);
        taskManager.createSubTask(st5);

        taskManager.removeSubTask(3);

        assertTrue(taskManager.getSubTaskStorageFromEpic(2).getStorage().isEmpty()
                ,"Хранилище подзадач в эпике после удаленич не пустое");
    }

// тесты приоритета
    @Test
    public void shouldCreate4RegularTasksInPrioritized() {

        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);

        taskManager.createEpicTask(et2);
        taskManager.createSubTask(st3);

        taskManager.createEpicTask(et4);
        taskManager.createSubTask(st5);

        taskManager.createRegularTask(rt6);
        taskManager.createRegularTask(rt7);
        taskManager.createRegularTask(rt8);

        TreeSet<Task> expected = new TreeSet<>(new Comparator<Task>() {
            @Override
            public int compare(Task obj1, Task obj2) {
                Optional<ZonedDateTime> t1 = obj1.getStartTime();
                Optional<ZonedDateTime> t2 = obj2.getStartTime();

                if (t1.isPresent() && t2.isPresent()) {
                    return t1.get().compareTo(t2.get());
                } else if (t1.isPresent()) {
                    return -1;
                } else if (t2.isPresent()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        expected.add(st1);
        expected.add(st3);
        expected.add(st5);
        expected.add(rt6);
        expected.add(rt7);
        expected.add(rt8);

        assertEquals(expected, taskManager.getPrioritized());
    }
    @Test
    public void shouldDeleteAllRegularTasksFromPrioritized() {

        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskManager.createEpicTask(et2);
        taskManager.createSubTask(st3);
        taskManager.createRegularTask(rt4);
        taskManager.createRegularTask(rt5);
        taskManager.createRegularTask(rt6);
        taskManager.createRegularTask(rt7);
        taskManager.clearRegularTaskStorage();
        TreeSet<Task> expected = new TreeSet<>(new Comparator<Task>() {
            @Override
            public int compare(Task obj1, Task obj2) {
                Optional<ZonedDateTime> t1 = obj1.getStartTime();
                Optional<ZonedDateTime> t2 = obj2.getStartTime();

                if (t1.isPresent() && t2.isPresent()) {
                    return t1.get().compareTo(t2.get());
                } else if (t1.isPresent()) {
                    return -1;
                } else if (t2.isPresent()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });


        expected.add(st3);

        assertEquals(expected, taskManager.getPrioritized());
    }
    @Test
    public void shouldDeleteSubTasksOnEpicByIdDeletionFromPrioritized() {

        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskManager.createEpicTask(et2);
        taskManager.createSubTask(st3);
        taskManager.createRegularTask(rt4);
        taskManager.createRegularTask(rt5);
        taskManager.createRegularTask(rt6);
        taskManager.createRegularTask(rt7);
        taskManager.removeEpicTask(2);
        TreeSet<Task> expected = new TreeSet<>(new Comparator<Task>() {
            @Override
            public int compare(Task obj1, Task obj2) {
                Optional<ZonedDateTime> t1 = obj1.getStartTime();
                Optional<ZonedDateTime> t2 = obj2.getStartTime();

                if (t1.isPresent() && t2.isPresent()) {
                    return t1.get().compareTo(t2.get());
                } else if (t1.isPresent()) {
                    return -1;
                } else if (t2.isPresent()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });


        expected.add(rt0);
        expected.add(rt1);
        expected.add(rt4);
        expected.add(rt5);
        expected.add(rt6);
        expected.add(rt7);
        assertEquals(expected, taskManager.getPrioritized());
    }
    @Test
    public void shouldDeleteSubTasksOnAllEpicDeletionFromPrioritized() {

        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskManager.createEpicTask(et2);
        taskManager.createSubTask(st3);
        taskManager.createRegularTask(rt4);
        taskManager.createRegularTask(rt5);
        taskManager.createRegularTask(rt6);
        taskManager.createRegularTask(rt7);
        taskManager.clearEpicTaskStorage();
        TreeSet<Task> expected = new TreeSet<>(new Comparator<Task>() {
            @Override
            public int compare(Task obj1, Task obj2) {
                Optional<ZonedDateTime> t1 = obj1.getStartTime();
                Optional<ZonedDateTime> t2 = obj2.getStartTime();

                if (t1.isPresent() && t2.isPresent()) {
                    return t1.get().compareTo(t2.get());
                } else if (t1.isPresent()) {
                    return -1;
                } else if (t2.isPresent()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });


        expected.add(rt0);
        expected.add(rt1);
        expected.add(rt4);
        expected.add(rt5);
        expected.add(rt6);
        expected.add(rt7);
        assertEquals(expected, taskManager.getPrioritized());
    }
    @Test
    public void shouldDeleteSubTaskOnDeleteSubTaskByIdFromPrioritized() {

        taskManager.createRegularTask(rt0);
        taskManager.createRegularTask(rt1);
        taskManager.createEpicTask(et2);
        taskManager.createSubTask(st3);
        taskManager.createRegularTask(rt4);
        taskManager.createRegularTask(rt5);
        taskManager.createRegularTask(rt6);
        taskManager.createRegularTask(rt7);
        taskManager.removeSubTask(3);
        TreeSet<Task> expected = new TreeSet<>(new Comparator<Task>() {
            @Override
            public int compare(Task obj1, Task obj2) {
                Optional<ZonedDateTime> t1 = obj1.getStartTime();
                Optional<ZonedDateTime> t2 = obj2.getStartTime();

                if (t1.isPresent() && t2.isPresent()) {
                    return t1.get().compareTo(t2.get());
                } else if (t1.isPresent()) {
                    return -1;
                } else if (t2.isPresent()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });


        expected.add(rt0);
        expected.add(rt1);
        expected.add(rt4);
        expected.add(rt5);
        expected.add(rt6);
        expected.add(rt7);
        assertEquals(expected, taskManager.getPrioritized());
    }
    @Test
    public void shouldUpgradeSubTaskWithUpdatedStatusInPrioritized() {

        taskManager.createEpicTask(et0);
        taskManager.createSubTask(st1);

        SubTask updateSubTaskWithId1 = new SubTask(
                1
                , "1 UPDATED subtask"
                , "subtask with id 1"
                , StatusName.DONE
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 11, 9, 0)
                , zone))
                , 0
        );
        taskManager.updateSubTask(updateSubTaskWithId1);

        TreeSet<Task> expected = new TreeSet<>(new Comparator<Task>() {
            @Override
            public int compare(Task obj1, Task obj2) {
                Optional<ZonedDateTime> t1 = obj1.getStartTime();
                Optional<ZonedDateTime> t2 = obj2.getStartTime();

                if (t1.isPresent() && t2.isPresent()) {
                    return t1.get().compareTo(t2.get());
                } else if (t1.isPresent()) {
                    return -1;
                } else if (t2.isPresent()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        expected.add(updateSubTaskWithId1);

        assertEquals(expected, taskManager.getPrioritized()
                , "Обновлянная подзадача не равна в prioritized не равна");
    }

}