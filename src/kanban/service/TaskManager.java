package kanban.service;

import kanban.module.Task;
import kanban.service.storage.RegularTaskStorage;

import java.util.HashMap;

public class TaskManager {
    TaskCreator taskCreator = new TaskCreator();
    RegularTaskStorage regularTaskStorage;

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
    public HashMap<Integer, Task> getRegularTaskStorage(){
        regularTaskStorage.printRegularTaskStorage();
        return regularTaskStorage.getStorage();
    }



//    public TaskStorage getRegularStorage(){
//
//    }

}
