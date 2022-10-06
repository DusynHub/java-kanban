package kanban.service;

import kanban.module.Task;
import kanban.service.storage.EpicTaskStorage;
import kanban.service.storage.RegularTaskStorage;
import kanban.service.storage.SubTaskStorage;

import java.util.HashMap;

public class TaskGetter {


    public HashMap<Integer, Task> getRegularTaskStorage(RegularTaskStorage regularTaskStorage) {
        if(regularTaskStorage.getStorage().isEmpty()){
            System.out.println("Ни одной подзадачи не было создано. Возвращено null");
        }
        return regularTaskStorage.getStorage();
    }
    public Task getRegularTask(int id, RegularTaskStorage regularTaskStorage){

        HashMap<Integer, Task> storage = regularTaskStorage.getStorage();
        if(storage.isEmpty()){
            System.out.println("Не создана ни одна обычная задача. Возвращено пустое значение");
            return storage.get(id);
        }
        if(storage.get(id) == null) {
            System.out.println("Задача с указанным id = " + id + " отсутствует. Возвращено пустое значение.");
        }
        return storage.get(id);
    }
    public HashMap<Integer, Task> getEpicTaskStorage(EpicTaskStorage epicTaskStorage) {
        if(epicTaskStorage.getStorage().isEmpty()){
            System.out.println("Ни одной эпик задачи не было создано. Возвращено null");
        }
        return epicTaskStorage.getStorage();
    }
    public Task getEpicTask(int id, EpicTaskStorage epicTaskStorage){

        HashMap<Integer, Task> storage = epicTaskStorage.getStorage();
        if(storage.isEmpty()){
            System.out.println("Не создана ни одна эпик задача. Возвращено пустое значение");
            return storage.get(id);
        }
        if(storage.get(id) == null) {
            System.out.println("Эпик задача с указанным id = " + id + " отсутствует. Возвращено пустое значение.");
        }
        return storage.get(id);
    }
    public HashMap<Integer, Task> getSubTaskStorage(SubTaskStorage subTaskStorage) {
        if(subTaskStorage.getStorage().isEmpty()){
            System.out.println("Ни одной эпик задачи не было создано. Возвращено null");
        }
        return subTaskStorage.getStorage();
    }
    public Task getSubTask(int id, SubTaskStorage subTaskStorage){

        HashMap<Integer, Task> storage = subTaskStorage.getStorage();
        if(storage.isEmpty()){
            System.out.println("Не создана ни одна подзадача. Возвращено пустое значение");
            return storage.get(id);
        }
        if(storage.get(id) == null) {
            System.out.println("Подзадача с указанным id = " + id + " отсутствует. Возвращено пустое значение.");
        }
        return storage.get(id);
    }
}
