package kanban.service;

import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.SubTask;
import kanban.module.Task;
import kanban.service.storage.EpicTaskStorage;
import kanban.service.storage.RegularTaskStorage;
import kanban.service.storage.SubTaskStorage;
import kanban.service.storage.TaskStorage;

import java.util.HashMap;

/**
 * Класс TaskManager для управления трекером задач
 */
public class InMemoryTaskManager implements TaskManager{
    private TaskCreator taskCreator = new TaskCreator();
    private TaskRemover taskRemover = new TaskRemover();
    private TaskGetter taskGetter = new TaskGetter();
    private TaskUpdater taskUpdater = new TaskUpdater();
    private RegularTaskStorage regularTaskStorage = new RegularTaskStorage();
    private EpicTaskStorage epicTaskStorage = new EpicTaskStorage();
    private SubTaskStorage subTaskStorageForTaskManager = new SubTaskStorage();

    /**
     * Возвращает список всех обычных задач
     *
     * @return Возвращает список всех обычных задач в виде HashMap<Integer, Task>
     */
// Методы для RegularTask
    @Override
    public HashMap<Integer, Task> getRegularTaskStorage() {
        return taskGetter.getRegularTaskStorage(regularTaskStorage);
    }

    /**
     * Создаёт обычную задачу и сохраняет в список обычных задач.
     * Возвращает статус создания задачи.
     *
     * @param task - обычная задача, которую надо создать в трекере задач
     * @return the string
     */
    @Override
    public String createRegularTask(RegularTask task) {
        Task taskToSave = taskCreator.createRegularTask(task);
        regularTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
        return "Обычная задача c id = " + taskToSave.getId() + "создана";
    }

    /**
     * Распечатать весь список обычных задач
     */
    @Override
    public void printRegularTaskStorage() {
        regularTaskStorage.printStorage();
    }

    /**
     * Удаляет весь список обычных задач
     * Возвращает статус удаления задач
     *
     * @return the string
     */
    @Override
    public String clearRegularTaskStorage() {
        return taskRemover.removeAllRegularTasks(regularTaskStorage);
    }

    /**
     * Возвращает обычную задачу по id задачи, если она создана.
     * Если задачи нет, то возвращает null
     *
     * @param regularId id задачи
     * @return Task обычная задача с нужным id или null
     */
    @Override
    public Task getRegularTask(int regularId){
        return taskGetter.getRegularTask(regularId, regularTaskStorage);
    }

    /**
     * Обновляет обычную задачу
     *
     * @param regularTaskToUpdate задача с новыми данными для замены существующей
     * @return String Информация о статусе обновления обычной задачи
     */
    @Override
    public String updateRegularTask(RegularTask regularTaskToUpdate){
        return taskUpdater.updateRegularTask(regularTaskToUpdate, regularTaskStorage);
    }

    /**
     * Удаляет обычную задачу по id
     *
     * @param regularId id обычной задачи, которую надо удалить
     * @return String Информация о статусе удаления обычной задачи
     */
    @Override
    public String removeRegularTask(int regularId){
        return taskRemover.removeRegularTask(regularId, regularTaskStorage);
    }

    /**
     * Возвращает список всех эпик задач
     *
     * @return HashMap &lt Integer, Task &gt - Список всех эпик задач
     */
//Методы для EpicTask
    @Override
    public HashMap<Integer, Task> getEpicTaskStorage() {
        return taskGetter.getEpicTaskStorage(epicTaskStorage);
    }

    /**
     * Создаёт эпик задачу
     *
     * @param task Эпик задача, которую надо создать в трекере задач
     * @return String Информация о статусе создания задач
     */
    @Override
    public String createEpicTask(EpicTask task) {
        Task taskToSave = taskCreator.createEpicTask(task);
        epicTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
        return "Эпик задача c id = " + taskToSave.getId() + " создана";
    }

    /**
     * Выводит на печать в консоль весь списко эпик задач
     */
    @Override
    public void printEpicTaskStorage() {
        epicTaskStorage.printStorage();
    }

    /**
     * Удаляет весь список эпик задач
     *
     * @return String Информация о статусе удаления списка всех эпик задач
     */
    @Override
    public String clearEpicTaskStorage() {
        return taskRemover.removeAllEpicTasks(epicTaskStorage, subTaskStorageForTaskManager);
    }

    /**
     * Возвращает эпик задачу по её id
     *
     * @param epicId id требуемой задачи
     * @return Task Эпик задача с требуемым id
     */
    @Override
    public Task getEpicTask(int epicId){
        return taskGetter.getEpicTask(epicId, epicTaskStorage);
    }

    /**
     * Обновляет эпик задачу
     *
     * @param epicTaskToUpdate задача с новыми данными для замены существующей
     * @return String Информация о статусе обновления эпик задачи
     */
    @Override
    public String updateEpicTask (EpicTask epicTaskToUpdate){
        return taskUpdater.updateEpicTask(epicTaskToUpdate, epicTaskStorage);
    }

    /**
     * Удаляет эпик задачу по id
     *
     * @param epicId id эпик задачи, которую надо удалить
     * @return String Информация о статусе удаления эпик задачи
     */
    @Override
    public String removeEpicTask(int epicId){
        return taskRemover.removeEpicTask(epicId, epicTaskStorage, subTaskStorageForTaskManager);
    }

    /**
     * Возвращает список всех подзада, конкретной эпик задачи
     *
     * @param epicId id эпик задачи, у которой необходимо получить список подзадач
     * @return SubTaskStorage писок всех подзада, конкретной эпик задачи
     */
    @Override
    public SubTaskStorage getSubTaskStorageFromEpic(int epicId){
        return taskGetter.getSubTaskFromEpicTask(epicId, epicTaskStorage);
    }

    /**
     * Распечатывает список задач
     *
     * @param taskStorage список задач, который надо распечатать
     */
    @Override
    public void printStorage(TaskStorage taskStorage){
        if(taskStorage == null){
            System.out.println("null");
            return;
        }
        taskStorage.printStorage();
    }

    /**
     * Возвращает список всех подзадач
     *
     * @return возвращает  список всех подзадач в виде HashMap
     */
// Методы для SubTask
    @Override
    public HashMap<Integer, Task> getSubTaskStorage() {
        return taskGetter.getSubTaskStorage(subTaskStorageForTaskManager);
    }

    /**
     * Создаёт подзадачу в трекере задач из подзадачи
     *
     * @param task подзадача, которую надо создать в трекере задач
     * @return String  Информация о статусе создания подзадачи
     */
    @Override
    public String createSubTask(SubTask task) {
        SubTask taskToSave = taskCreator.createSubTask(task, epicTaskStorage);
        if(taskToSave == null){
            return "Такой эпик задачи нет. Позадача не была создана. Возвращено null";
        }
        subTaskStorageForTaskManager.saveInStorage(taskToSave.getId(), taskToSave);
        EpicTask epic = (EpicTask) epicTaskStorage.getStorage().get(taskToSave.getEpicId());
        epic.getSubTaskStorageForEpic().saveInStorage(taskToSave.getId(), taskToSave);
        taskUpdater.epicStatusUpdater(epic);
        return "Подзача задача c id = " + taskToSave.getId() + "создана";
    }

    /**
     * Распечатывает весь список подзадач
     */
    @Override
    public void printSubTaskStorage() {
        subTaskStorageForTaskManager.printStorage();
    }

    /**
     * Удаляет весь список подзадач
     *
     * @return String  Информация о статусе удаления списка всех подзадач
     */
    @Override
    public String clearSubTaskStorage() {
        HashMap<Integer, Task> storage = epicTaskStorage.getStorage();
        for(Integer epicId : storage.keySet()){
            EpicTask epicTask = (EpicTask) storage.get(epicId);
            epicTask.getSubTaskStorageForEpic().clearStorage();
            taskUpdater.epicStatusUpdater(epicTask);
        }
        return taskRemover.removeAllSubTasks(subTaskStorageForTaskManager, epicTaskStorage);
    }

    /**
     * Возвращает подзадачу по id
     *
     * @param id  id требуемой подзадачи
     * @return Task  Возвращает требуемую подзадачу
     */
    @Override
    public Task getSubTask(int id){
        return taskGetter.getSubTask(id, subTaskStorageForTaskManager);
    }

    /**
     * Обновляет подзадачу
     *
     * @param subTaskToUpdate  подзадача, которая должна обновить подзадачу
     * @return String Информация о статусе обновления подзадачи
     */
    @Override
    public String updateSubTask (SubTask subTaskToUpdate){
        String result = taskUpdater.updateSubTask(subTaskToUpdate, subTaskStorageForTaskManager, epicTaskStorage);
        EpicTask epic = (EpicTask) epicTaskStorage.getStorage().get(subTaskToUpdate.getEpicId());
        if(epic == null){
            return result;
        }
        epic.getSubTaskStorageForEpic().saveInStorage(subTaskToUpdate.getId(), subTaskToUpdate);
        taskUpdater.epicStatusUpdater(epic);
        return result;
    }

    /**
     * Удаляет подзадачу по id
     *
     * @param subId id подзадачи, которую надо удалить
     * @return String Информация о статусе удаления подзадачи
     */
    @Override
    public String removeSubTask(int subId){
        return taskRemover.removeSubTask(subId, epicTaskStorage, subTaskStorageForTaskManager);
    }
}

