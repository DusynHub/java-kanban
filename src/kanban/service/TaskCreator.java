package kanban.service;

import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.SubTask;
import kanban.module.Task;
import kanban.service.storage.EpicTaskStorage;

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
}
