package kanban.service;

import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.SubTask;
import kanban.module.Task;
import kanban.module.storage.SubTaskStorage;
import kanban.module.storage.TaskStorage;

import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {

// Методы для RegularTask
    HashMap<Integer, Task> getRegularTaskStorage();
    String createRegularTask(RegularTask task);
    void printRegularTaskStorage();
    String clearRegularTaskStorage();
    Task getRegularTask(int regularId);
    String updateRegularTask(RegularTask regularTaskToUpdate);
    String removeRegularTask(int regularId);
    List<Task> getAllRegularTasks();
//Методы для EpicTask
    HashMap<Integer, Task> getEpicTaskStorage();
    String createEpicTask(EpicTask task);
    void printEpicTaskStorage();
    String clearEpicTaskStorage();
    Task getEpicTask(int epicId);
    String updateEpicTask (EpicTask epicTaskToUpdate);
    String removeEpicTask(int epicId);
    SubTaskStorage getSubTaskStorageFromEpic(int epicId);
    List<Task> getAllEpicTasks();
    void printStorage(TaskStorage taskStorage);
// Методы для SubTask
    HashMap<Integer, Task> getSubTaskStorage();
    String createSubTask(SubTask task);
    void printSubTaskStorage();
    String clearSubTaskStorage();
    Task getSubTask(int id);
    String updateSubTask (SubTask subTaskToUpdate);
    String removeSubTask(int subId);
    List<Task> getAllSubTasks();



    List<Task> getHistoryOfTasks();
    List<Task> getPrioritizedTasks();
    TreeSet<Task> getPrioritized();

}

