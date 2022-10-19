package kanban.service;

import kanban.module.EpicTask;
import kanban.module.SubTask;
import kanban.module.Task;
import kanban.module.storage.EpicTaskStorage;
import kanban.module.storage.RegularTaskStorage;
import kanban.module.storage.SubTaskStorage;

import java.util.HashMap;

public class TaskRemover {

    // Методы для RegularTask
    public String removeAllRegularTasks(RegularTaskStorage regularTaskStorage){
        return regularTaskStorage.clearStorage();
    }
    public String removeRegularTask(int regularId, RegularTaskStorage regularTaskStorage){

        HashMap<Integer, Task> storage = regularTaskStorage.getStorage();
        if(storage.containsKey(regularId)){
            storage.remove(regularId);
            return "Задача  с id = '"+ regularId + "' удалена";
        } else {
            return "Задача с id = '"+ regularId + "' отсутствует. Сначала создайте задачу с соответвующим regularId. Удаление невозможно";
        }
    }

    //Методы для EpicTask
    public String removeAllEpicTasks(EpicTaskStorage epicTaskStorage, SubTaskStorage subTaskStorageForTaskManager){
        subTaskStorageForTaskManager.clearStorage();
        return epicTaskStorage.clearStorage();
    }
    public String removeEpicTask(int epicId, EpicTaskStorage EpicTaskStorage, SubTaskStorage subTaskStorage){

        HashMap<Integer, Task> subStorage = subTaskStorage.getStorage();
        HashMap<Integer, Task> epicStorage = EpicTaskStorage.getStorage();
        if(epicStorage.containsKey(epicId)){
            EpicTask task = (EpicTask) epicStorage.get(epicId);
            for(Integer subTaskId : task.getSubTaskStorageForEpic().getStorage().keySet()){
                subStorage.remove(subTaskId);
            }
            epicStorage.remove(epicId);
            return "Эпик задача с id = '"+ epicId + "' удалена";
        } else {
            return "Задача с id = '"+ epicId + "' отсутствует. Сначала создайте задачу с соответвующим epicId. Удаление невозможно";
        }
    }

    // Методы для SubTask
    public String removeAllSubTasks(SubTaskStorage subTaskStorageForTaskManager, EpicTaskStorage epicTaskStorage){
        if(subTaskStorageForTaskManager.getStorage().isEmpty() || epicTaskStorage.getStorage().isEmpty()){
            return "Не было создано подзадач для удаления";
        }
        HashMap<Integer, Task> storage = epicTaskStorage.getStorage();
        for(Integer epicId : storage.keySet()){
            EpicTask epicTask = (EpicTask) storage.get(epicId);
            epicTask.getSubTaskStorageForEpic().clearStorage();
        }
        return subTaskStorageForTaskManager.clearStorage();
    }
    public String removeSubTask(int subId, EpicTaskStorage epicTaskStorage, SubTaskStorage subTaskStorage){

        HashMap<Integer, Task> subStorage = subTaskStorage.getStorage();
        HashMap<Integer, Task> epicStorage = epicTaskStorage.getStorage();
        if(subStorage.containsKey(subId)){
            SubTask subTask = (SubTask) subStorage.get(subId);
            subStorage.remove(subId);
            EpicTask task = (EpicTask) epicStorage.get(subTask.getEpicId());
            HashMap<Integer, Task> subTasksInEpic = task.getSubTaskStorageForEpic().getStorage();
            subTasksInEpic.remove(subId);
            return "Подзадача с id = '"+ subId + "' удалена";
        } else {
            return "Подзадача с id = '"+ subId + "' отсутствует. Сначала создайте задачу с соответвующим Id. Удаление невозможно";
        }
    }
}
