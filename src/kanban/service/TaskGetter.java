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
        if(regularTaskStorage.getStorage().isEmpty()){
            System.out.println("Ни одной подзадачи не было создано. Возвращено null");
        }
        return regularTaskStorage.getStorage();
    }
    public Task getRegularTask(int regularId, RegularTaskStorage regularTaskStorage){

        HashMap<Integer, Task> storage = regularTaskStorage.getStorage();
        if(storage.isEmpty()){
            System.out.println("Не создана ни одна обычная задача. Возвращено пустое значение");
            return storage.get(regularId);
        }
        if(storage.get(regularId) == null) {
            System.out.println("Задача с указанным id = " + regularId + " отсутствует. Возвращено пустое значение.");
        }
        return storage.get(regularId);
    }

    //Методы для EpicTask
    public HashMap<Integer, Task> getEpicTaskStorage(EpicTaskStorage epicTaskStorage) {
        if(epicTaskStorage.getStorage().isEmpty()){
            System.out.println("Ни одной эпик задачи не было создано. Возвращено null");
        }
        return epicTaskStorage.getStorage();
    }
    public Task getEpicTask(int epicId, EpicTaskStorage epicTaskStorage){

        HashMap<Integer, Task> storage = epicTaskStorage.getStorage();
        if(storage.isEmpty()){
            System.out.println("Не создана ни одна эпик задача. Возвращено пустое значение");
            return storage.get(epicId);
        }
        if(storage.get(epicId) == null) {
            System.out.println("Эпик задача с указанным id = " + epicId + " отсутствует. Возвращено пустое значение.");
        }
        return storage.get(epicId);
    }
    public SubTaskStorage getSubTaskFromEpicTask(int epicId, EpicTaskStorage epicTaskStorage){

        HashMap<Integer, Task> storage = epicTaskStorage.getStorage();
        EpicTask epicTask = (EpicTask) storage.get(epicId);
        if(epicTask == null){
            System.out.println("Эпик задача с id = '" + epicId +"' Отсутствует. Возвращено null");
            return null;
        }
        SubTaskStorage subTaskStorageFromEpic = epicTask.getSubTaskStorageForEpic();
        if(subTaskStorageFromEpic == null){
            System.out.println("Не создана ни одна подзадача. Возвращено пустое значение");
            return null;
        }
        return subTaskStorageFromEpic;
    }

    // Методы для SubTask
    public HashMap<Integer, Task> getSubTaskStorage(SubTaskStorage subTaskStorage) {
        if(subTaskStorage.getStorage().isEmpty()){
            System.out.println("Ни одной эпик задачи не было создано. Возвращено null");
        }
        return subTaskStorage.getStorage();
    }
    public Task getSubTask(int subId, SubTaskStorage subTaskStorage){

        HashMap<Integer, Task> storage = subTaskStorage.getStorage();
        if(storage.isEmpty()){
            System.out.println("Не создана ни одна подзадача. Возвращено пустое значение");
            return storage.get(subId);
        }
        if(storage.get(subId) == null) {
            System.out.println("Подзадача с указанным id = " + subId + " отсутствует. Возвращено пустое значение.");
        }
        return storage.get(subId);
    }

}
