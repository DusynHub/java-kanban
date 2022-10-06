package kanban.service;

import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.SubTask;
import kanban.module.Task;
import kanban.service.storage.EpicTaskStorage;
import kanban.service.storage.RegularTaskStorage;
import kanban.service.storage.SubTaskStorage;

import java.util.HashMap;

public class TaskManager {
    private TaskCreator taskCreator = new TaskCreator();
    private TaskRemover taskRemover = new TaskRemover();
    private TaskGetter taskGetter = new TaskGetter();
    private TaskUpdater taskUpdater = new TaskUpdater();
    private RegularTaskStorage regularTaskStorage = new RegularTaskStorage();
    private EpicTaskStorage epicTaskStorage = new EpicTaskStorage();
    private SubTaskStorage subTaskStorageForTaskManager = new SubTaskStorage();
    public HashMap<Integer, Task> getRegularTaskStorage() {
        return taskGetter.getRegularTaskStorage(regularTaskStorage);
    }
    public String createRegularTask(RegularTask task) {
        Task taskToSave = taskCreator.createRegularTask(task);
        regularTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
        return "Обычная задача c id = " + taskToSave.getId() + "создана";
    }
    public void printRegularTaskStorage() {
        regularTaskStorage.printStorage();
    }
    public String clearRegularTaskStorage() {
        return taskRemover.removeAllRegularTasks(regularTaskStorage);
    }
    public Task getRegularTask(int id){
        return taskGetter.getRegularTask(id, regularTaskStorage);
    }
    public String updateRegularTask(RegularTask regularTaskToUpdate){
        return taskUpdater.updateRegularTask(regularTaskToUpdate, regularTaskStorage);
    }
    public String removeRegularTask(int id){
        return taskRemover.removeRegularTask(id, regularTaskStorage);
    }
    public HashMap<Integer, Task> getEpicTaskStorage() {
        return taskGetter.getEpicTaskStorage(epicTaskStorage);
    }
    public String createEpicTask(EpicTask task) {
        Task taskToSave = taskCreator.createEpicTask(task);
        epicTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
        return "Эпик задача c id = " + taskToSave.getId() + " создана";
    }
    public void printEpicTaskStorage() {
        epicTaskStorage.printStorage();
    }
    public String clearEpicTaskStorage() {
        return taskRemover.removeAllEpicTasks(epicTaskStorage, subTaskStorageForTaskManager);
    }
    public Task getEpicTask(int id){
        return taskGetter.getEpicTask(id, epicTaskStorage);
    }
    public String updateEpicTask (EpicTask epicTaskToUpdate){
        return taskUpdater.updateEpicTask(epicTaskToUpdate, epicTaskStorage);
    }
    public HashMap<Integer, Task> getSubTaskStorage() {
        return taskGetter.getSubTaskStorage(subTaskStorageForTaskManager);
    }
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
    public void printSubTaskStorage() {
        subTaskStorageForTaskManager.printStorage();
    }
    public String clearSubTaskStorage() {
        HashMap<Integer, Task> storage = epicTaskStorage.getStorage();
        for(Integer epicId : storage.keySet()){
            EpicTask epicTask = (EpicTask) storage.get(epicId);
            epicTask.getSubTaskStorageForEpic().clearStorage();
            taskUpdater.epicStatusUpdater(epicTask);
        }
        return taskRemover.removeAllSubTasks(subTaskStorageForTaskManager);
    }
    public Task getSubTask(int id){
        return taskGetter.getSubTask(id, subTaskStorageForTaskManager);
    }
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
}

