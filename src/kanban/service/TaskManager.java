package kanban.service;

import kanban.module.RegularTask;
import kanban.module.Task;
import kanban.service.storage.RegularTaskStorage;

import java.util.HashMap;

public class TaskManager {
    private TaskCreator taskCreator = new TaskCreator();
    private TaskRemover taskRemover = new TaskRemover();
    private TaskGetter taskGetter = new TaskGetter();
    private TaskUpdater taskUpdater = new TaskUpdater();
    private RegularTaskStorage regularTaskStorage = new RegularTaskStorage();

//    public String createRegularTask(String name, String description, int statusId){
//        Task taskToSave = taskCreator.createRegularTask(name, description, statusId);
////        System.out.println(taskToSave.getClass());
//        if(regularTaskStorage == null) {
//            regularTaskStorage = new RegularTaskStorage();
//            regularTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
//        } else {
//            regularTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
//        }
//        return "Обычная задача создана";
//    }

    public String createRegularTask(RegularTask task) {
        Task taskToSave = taskCreator.createRegularTask(task);
//      System.out.println(taskToSave.getClass());
        regularTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);

        return "Обычная задача c id = " + taskToSave.getId();
    }
    public HashMap<Integer, Task> getRegularTaskStorage() {
        return regularTaskStorage.getStorage();
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
}

