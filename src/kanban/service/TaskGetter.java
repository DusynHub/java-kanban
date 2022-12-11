package kanban.service;

import kanban.module.EpicTask;
import kanban.module.Task;
import kanban.module.storage.EpicTaskStorage;
import kanban.module.storage.RegularTaskStorage;
import kanban.module.storage.SubTaskStorage;

import java.util.HashMap;

public class TaskGetter {

    // Методы для RegularTask
    public HashMap<Integer, Task> getRegularTaskStorage(RegularTaskStorage regularTaskStorage) {
        return regularTaskStorage.getStorage();
    }
    public Task getRegularTask(int regularId, RegularTaskStorage regularTaskStorage){

        HashMap<Integer, Task> storage = regularTaskStorage.getStorage();

        if(storage.get(regularId) == null) {
        }
        return storage.get(regularId);
    }

    //Методы для EpicTask
    public HashMap<Integer, Task> getEpicTaskStorage(EpicTaskStorage epicTaskStorage) {
        return epicTaskStorage.getStorage();
    }
    public Task getEpicTask(int epicId, EpicTaskStorage epicTaskStorage){

        HashMap<Integer, Task> storage = epicTaskStorage.getStorage();
        return storage.get(epicId);
    }
    public SubTaskStorage getSubTaskFromEpicTask(int epicId, EpicTaskStorage epicTaskStorage){

        HashMap<Integer, Task> storage = epicTaskStorage.getStorage();
        EpicTask epicTask = (EpicTask) storage.get(epicId);
        if(epicTask == null){
            return null;
        }
        return epicTask.getSubTaskStorageForEpic();
    }

    // Методы для SubTask
    public HashMap<Integer, Task> getSubTaskStorage(SubTaskStorage subTaskStorage) {
        return subTaskStorage.getStorage();
    }
    public Task getSubTask(int subId, SubTaskStorage subTaskStorage){

        HashMap<Integer, Task> storage = subTaskStorage.getStorage();
        return storage.get(subId);
    }

}
