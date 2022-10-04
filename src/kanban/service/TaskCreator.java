package kanban.service;

import kanban.module.RegularTask;
import kanban.module.Task;

public class TaskCreator {

    private int countId = 0;

    public Task createRegularTask(String name, String description, int statusId){
        RegularTask newTask = new RegularTask(name, description, countId, statusId);
        countId++;
        return newTask;
    }

}
