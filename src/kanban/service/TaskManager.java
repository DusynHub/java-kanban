package kanban.service;

import kanban.module.RegularTask;
import kanban.module.Task;
import kanban.service.storage.RegularTaskStorage;

import java.util.HashMap;

public class TaskManager {
    TaskCreator taskCreator = new TaskCreator();
    RegularTaskStorage regularTaskStorage = new RegularTaskStorage();

    public String createRegularTask(String name, String description, int statusId){
        Task taskToSave = taskCreator.createRegularTask(name, description, statusId);
//        System.out.println(taskToSave.getClass());
        if(regularTaskStorage == null) {
            regularTaskStorage = new RegularTaskStorage();
            regularTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
        } else {
            regularTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
        }
        return "Обычная задача создана";
    }

    public String createRegularTask(RegularTask task){
        Task taskToSave = taskCreator.createRegularTask(task);
//        System.out.println(taskToSave.getClass());
        if(regularTaskStorage == null) {
            regularTaskStorage = new RegularTaskStorage();
            regularTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
        } else {
            regularTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
        }
        return "Обычная задача создана";
    }

    public HashMap<Integer, Task> getRegularTaskStorage(){
        return regularTaskStorage.getStorage();
    }
    public void printRegularTaskStorage(){
        regularTaskStorage.printRegularTaskStorage();
    }

    public String clearRegularTaskStorage(){
        return regularTaskStorage.clearStorage();
    }
}
