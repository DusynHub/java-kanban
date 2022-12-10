package kanban.service;

import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.SubTask;
import kanban.module.Task;
import kanban.module.storage.EpicTaskStorage;
import kanban.util.StartDateValidator;

import java.util.TreeSet;

// first commit in a new branch
public class TaskCreator {
    private int countId = 0;
    public Task createRegularTask(RegularTask task, TreeSet<Task> prioritized){
        if(!StartDateValidator.validateStartDate(task, prioritized)){
            return null;
        }
        task.setId(countId);
        countId++;
        return task;
    }
    public Task createEpicTask(EpicTask task){
        task.setId(countId);
        countId++;
        return task;
    }
    public SubTask createSubTask(SubTask task, EpicTaskStorage epicTaskStorage, TreeSet<Task> prioritized){
        if(!epicTaskStorage.getStorage().containsKey(task.getEpicId())){
            return null;
        }
        if(!StartDateValidator.validateStartDate(task, prioritized)){
            return null;
        }
        task.setId(countId);
        countId++;
        return task;
    }
    public int getCountId() {
        return countId;
    }
    public void setCountId(int countId) {
        this.countId = countId;
    }

}
