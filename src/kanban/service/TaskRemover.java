package kanban.service;

import kanban.module.Task;
import kanban.service.storage.EpicTaskStorage;
import kanban.service.storage.RegularTaskStorage;
import kanban.service.storage.SubTaskStorage;

import java.util.HashMap;

public class TaskRemover {
    public String removeAllRegularTasks(RegularTaskStorage regularTaskStorage){
        return regularTaskStorage.clearStorage();
    }
    public String removeRegularTask(int id, RegularTaskStorage regularTaskStorage){

        HashMap<Integer, Task> storage = regularTaskStorage.getStorage();
        if(storage.containsKey(id)){
            storage.remove(id);
            return "Задача удалена";
        } else {
            return "Задача отсутствует. Сначала создайте задачу с соответвующим id. Удаление невозможно";
        }
    }
    public String removeAllEpicTasks(EpicTaskStorage epicTaskStorage, SubTaskStorage subTaskStorageForTaskManager){
        subTaskStorageForTaskManager.clearStorage();
        return epicTaskStorage.clearStorage();
    }

    public String removeEpicTask(int id, EpicTaskStorage EpicTaskStorage){

        HashMap<Integer, Task> storage = EpicTaskStorage.getStorage();
        if(storage.containsKey(id)){
            storage.remove(id);
            return "Задача удалена";
        } else {
            return "Задача отсутствует. Сначала создайте задачу с соответвующим id. Удаление невозможно";
        }
    }

    public String removeAllSubTasks(SubTaskStorage subTaskStorageForTaskManager){

        return subTaskStorageForTaskManager.clearStorage();
    }
}
