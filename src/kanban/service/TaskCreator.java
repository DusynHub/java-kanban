package kanban.service;

import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.SubTask;
import kanban.module.Task;
import kanban.module.storage.EpicTaskStorage;
// first commit in a new branch
public class TaskCreator {
    private int countId = 0;
    public Task createRegularTask(RegularTask task){
        task.setId(countId);
        countId++;
        return task;
    }
    public Task createEpicTask(EpicTask task){
        task.setId(countId);
        countId++;
        return task;
    }
    public SubTask createSubTask(SubTask task, EpicTaskStorage epicTaskStorage){
        if(!epicTaskStorage.getStorage().containsKey(task.getEpicId())){
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
