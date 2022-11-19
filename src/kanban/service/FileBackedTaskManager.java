package kanban.service;

import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.SubTask;
import kanban.module.Task;
import kanban.module.storage.EpicTaskStorage;
import kanban.module.storage.RegularTaskStorage;
import kanban.module.storage.SubTaskStorage;
import kanban.module.storage.TaskStorage;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {

    Path FileToSaveCondition;
    private final String HEADERS = "ID | TYPE | TASK NAME | DESCRIPTION | STATUS | EPICID FOR SUBTASK";

    public FileBackedTaskManager(Path pathOfFile) {
        this.FileToSaveCondition = pathOfFile;
    }

    private void save() {
        writeCondition(regularTaskStorage
                , epicTaskStorage
                , subTaskStorageForTaskManager
                , inMemoryHistoryManager);
    }

    private void writeCondition(RegularTaskStorage regularTaskStorage
            , EpicTaskStorage epicTaskStorage
            , SubTaskStorage subTaskStorage
            , HistoryManager inMemoryHistoryManager) {

        List<TaskStorage> storages = new ArrayList<>();
        storages.add(regularTaskStorage);
        storages.add(epicTaskStorage);
        storages.add(subTaskStorage);

//        BufferedReader br = new BufferedReader(
//                new FileReader(
//                        FileToSaveCondition.toAbsolutePath().toString()));

        try (BufferedWriter out =
                     new BufferedWriter(
                             new FileWriter(FileToSaveCondition.toAbsolutePath().toString())
                     )
        ) {
            out.write(HEADERS);
            out.newLine();
            for (TaskStorage storage : storages) {
                for (Map.Entry<Integer, Task> entry : storage.getStorage().entrySet()) {
                    Task taskToCSV = entry.getValue();
                    out.write(taskToCSV.toStringForCSV());
                    out.newLine();
                }
            }
            List<Task> history = inMemoryHistoryManager.getHistory();

            out.newLine();
            String separator = "";
            for(Task task : history){
                out.write(separator + task.getId());
                separator = ", ";
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public HashMap<Integer, Task> getRegularTaskStorage() {
        HashMap<Integer, Task> result = super.getRegularTaskStorage();
        save();
        return result;
    }
//  Методы для работы с обычными задачами
    @Override
    public String createRegularTask(RegularTask task) {
        String result = super.createRegularTask(task);
        save();
        return result;
    }
    @Override
    public String clearRegularTaskStorage() {
        String result = super.clearRegularTaskStorage();
        save();
        return result;
    }
    @Override
    public Task getRegularTask(int regularId) {
        Task taskToReturn = super.getRegularTask(regularId);
        save();
        return taskToReturn;
    }
    @Override
    public String updateRegularTask(RegularTask regularTaskToUpdate) {
        String result = super.updateRegularTask(regularTaskToUpdate);
        save();
        return result;
    }
    @Override
    public String removeRegularTask(int regularId) {
        String result = super.removeRegularTask(regularId);
        save();
        return result;
    }
// Методы для работы с подзадачами
    @Override
    public String createSubTask(SubTask task) {
        String result = super.createSubTask(task);
        save();
        return result;
    }
// Методы для работы с эпик задачами
    @Override
    public String createEpicTask(EpicTask task) {
        String result = super.createEpicTask(task);
        save();
        return result;
    }
    @Override
    public String clearEpicTaskStorage() {
        String result = super.clearEpicTaskStorage();
        save();
        return result;
    }
    @Override
    public Task getEpicTask(int epicId) {
        Task result = super.getEpicTask(epicId);
        save();
        return result;
    }
}
