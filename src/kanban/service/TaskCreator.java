package kanban.service;

import kanban.module.RegularTask;
import kanban.module.Task;

public class TaskCreator {

    private int countId = 0;

    public Task createTask(String name, String description, int statusId){
        countId++;
        return new RegularTask(name, description, countId, statusId);
    }

}
