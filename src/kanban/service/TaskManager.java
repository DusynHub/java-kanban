package kanban.service;

import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.SubTask;
import kanban.module.Task;
import kanban.service.storage.SubTaskStorage;
import kanban.service.storage.TaskStorage;

import java.util.HashMap;

public interface TaskManager {

// Методы для RegularTask
    public HashMap<Integer, Task> getRegularTaskStorage();
    public String createRegularTask(RegularTask task);
    public void printRegularTaskStorage();
    public String clearRegularTaskStorage();
    public Task getRegularTask(int regularId);
    public String updateRegularTask(RegularTask regularTaskToUpdate);
    public String removeRegularTask(int regularId);
//Методы для EpicTask
    public HashMap<Integer, Task> getEpicTaskStorage();
    public String createEpicTask(EpicTask task);
    public void printEpicTaskStorage();
    public String clearEpicTaskStorage();
    public Task getEpicTask(int epicId);
    public String updateEpicTask (EpicTask epicTaskToUpdate);
    public String removeEpicTask(int epicId);
    public SubTaskStorage getSubTaskStorageFromEpic(int epicId);
    public void printStorage(TaskStorage taskStorage);
// Методы для SubTask
    public HashMap<Integer, Task> getSubTaskStorage();
    public String createSubTask(SubTask task);
    public void printSubTaskStorage();
    public String clearSubTaskStorage();
    public Task getSubTask(int id);
    public String updateSubTask (SubTask subTaskToUpdate);
    public String removeSubTask(int subId);
}

