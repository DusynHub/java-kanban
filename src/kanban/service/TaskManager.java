package kanban.service;

import kanban.module.Task;
import kanban.service.storage.RegularTaskStorage;

public class TaskManager {
    TaskCreator taskCreator = new TaskCreator();
    RegularTaskStorage regularTaskStorage;

    public Task createRegularTask(String name, String description, int statusId){
        Task taskToSave = taskCreator.createTask(name, description, statusId);
//        System.out.println(taskToSave.getClass());
        if(regularTaskStorage == null) {
            regularTaskStorage = new RegularTaskStorage();
            regularTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
        } else {
            regularTaskStorage.saveInStorage(taskToSave.getId(), taskToSave);
        }
        return taskToSave;
    }

//    public TaskStorage getRegularStorage(){
//
//    }

}
