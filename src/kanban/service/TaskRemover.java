package kanban.service;

import kanban.module.EpicTask;
import kanban.module.SubTask;
import kanban.module.Task;
import kanban.module.storage.EpicTaskStorage;
import kanban.module.storage.RegularTaskStorage;
import kanban.module.storage.SubTaskStorage;

import java.util.HashMap;
import java.util.TreeSet;

public class TaskRemover {

    // Методы для RegularTask
    public String removeAllRegularTasks(RegularTaskStorage regularTaskStorage
            , HistoryManager historyManager
            , TreeSet<Task> prioritized) {
        for (Integer regularId : regularTaskStorage.getStorage().keySet()) {
            historyManager.remove(regularTaskStorage.getStorage().get(regularId));
        }

        prioritized.removeAll(regularTaskStorage.getStorage().values());

        return regularTaskStorage.clearStorage();
    }

    public String removeRegularTask(int regularId
            , RegularTaskStorage regularTaskStorage
            , HistoryManager historyManager
            , TreeSet<Task> prioritized) {

        HashMap<Integer, Task> storage = regularTaskStorage.getStorage();
        if (storage.containsKey(regularId)) {
            prioritized.remove(regularTaskStorage.getStorage().get(regularId));
            historyManager.remove(storage.get(regularId));
            storage.remove(regularId);
            return "Задача  с id = " + regularId + " удалена";
        } else {
            return "Задача с id = " + regularId + " отсутствует. Сначала создайте задачу с соответвующим regularId. " +
                    "Удаление невозможно";
        }
    }

    //Методы для EpicTask
    public String removeAllEpicTasks(EpicTaskStorage epicTaskStorage
            , SubTaskStorage subTaskStorageForTaskManager
            , HistoryManager historyManager
            , TreeSet<Task> prioritized) {
        for (Integer id : subTaskStorageForTaskManager.getStorage().keySet()) {
            historyManager.remove(subTaskStorageForTaskManager.getStorage().get(id));
        }
        prioritized.removeAll(subTaskStorageForTaskManager.getStorage().values());
        subTaskStorageForTaskManager.clearStorage();

        for (Integer id : epicTaskStorage.getStorage().keySet()) {
            historyManager.remove(epicTaskStorage.getStorage().get(id));
        }

        return epicTaskStorage.clearStorage();
    }

    public String removeEpicTask(int epicId
            , EpicTaskStorage EpicTaskStorage
            , SubTaskStorage subTaskStorage
            , HistoryManager historyManager
            , TreeSet<Task> prioritized) {

        HashMap<Integer, Task> subStorage = subTaskStorage.getStorage();
        HashMap<Integer, Task> epicStorage = EpicTaskStorage.getStorage();
        if (epicStorage.containsKey(epicId)) {
            EpicTask task = (EpicTask) epicStorage.get(epicId);
            for (Integer subTaskId : task.getSubTaskStorageForEpic().getStorage().keySet()) {
                historyManager.remove(subStorage.get(subTaskId));
                subStorage.remove(subTaskId);
            }
            historyManager.remove(epicStorage.get(epicId));
            prioritized.removeAll(((EpicTask) epicStorage.get(epicId))
                    .getSubTaskStorageForEpic()
                    .getStorage().values());
            epicStorage.remove(epicId);
            return "Эпик задача с id = '" + epicId + "' удалена";
        } else {
            return "Задача с id = '" + epicId + "' отсутствует. Сначала создайте задачу с соответвующим epicId. Удаление невозможно";
        }
    }

    // Методы для SubTask
    public String removeAllSubTasks(SubTaskStorage subTaskStorageForTaskManager,
                                    EpicTaskStorage epicTaskStorage,
                                    HistoryManager historyManager
    ) {
        if (subTaskStorageForTaskManager.getStorage().isEmpty() || epicTaskStorage.getStorage().isEmpty()) {
            return "Не было создано подзадач для удаления";
        }
        HashMap<Integer, Task> storage = epicTaskStorage.getStorage();
        for (Integer epicId : storage.keySet()) {
            EpicTask epicTask = (EpicTask) storage.get(epicId);
            epicTask.getSubTaskStorageForEpic().clearStorage();
        }
        for (Integer id : subTaskStorageForTaskManager.getStorage().keySet()) {
            historyManager.remove(subTaskStorageForTaskManager.getStorage().get(id));
        }

        return subTaskStorageForTaskManager.clearStorage();
    }

    public String removeSubTask(int subId,
                                EpicTaskStorage epicTaskStorage
            , SubTaskStorage subTaskStorage
            , HistoryManager historyManager
            , TreeSet<Task> prioritized) {

        HashMap<Integer, Task> subStorage = subTaskStorage.getStorage();
        HashMap<Integer, Task> epicStorage = epicTaskStorage.getStorage();
        if (subStorage.containsKey(subId)) {
            SubTask subTask = (SubTask) subStorage.get(subId);
            prioritized.remove(subTask);
            historyManager.remove(subStorage.get(subId));
            subStorage.remove(subId);
            EpicTask task = (EpicTask) epicStorage.get(subTask.getEpicId());
            HashMap<Integer, Task> subTasksInEpic = task.getSubTaskStorageForEpic().getStorage();
            subTasksInEpic.remove(subId);
            return "Подзадача с id = '" + subId + "' удалена";
        } else {
            return "Подзадача с id = '" + subId
                    + "' отсутствует. Сначала создайте задачу с соответвующим Id. Удаление невозможно";
        }
    }
}
