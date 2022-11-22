package kanban.service;

import kanban.module.*;
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

    protected final String STORAGE_HEADERS
                                    = "ID|TYPE|TASK_NAME|DESCRIPTION|STATUS|EPIC_ID FOR SUBTASK";
    protected final String HISTORY_SEPARATOR = "BELOW_THIS_ROW_HISTORY_DATA";
    protected final String HISTORY_HEADERS = "ID|TASK_TYPE";
    protected final String COUNT_ID_SEPARATOR = "BELOW_THIS_ROW_CONT_ID_VALUE";
    Path fileToSaveCondition;
    public FileBackedTaskManager(Path pathOfFile) {
        this.fileToSaveCondition = pathOfFile;
        restoreCondition(pathOfFile);
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

        try (BufferedWriter out =
                     new BufferedWriter(
                             new FileWriter(fileToSaveCondition.toAbsolutePath().toString())
                     )
        ) {
            out.write(STORAGE_HEADERS + System.lineSeparator());

            for (TaskStorage storage : storages) {
                for (Map.Entry<Integer, Task> entry : storage.getStorage().entrySet()) {
                    Task taskToCSV = entry.getValue();
                    out.write(taskToCSV.toStringForCSV());
                    out.newLine();
                }
            }

            List<Task> history = inMemoryHistoryManager.getHistory();
            out.write(System.lineSeparator() + HISTORY_SEPARATOR);
            out.write(System.lineSeparator() + HISTORY_HEADERS + System.lineSeparator());

            for(Task task : history){

                int taskId;
                String taskType;

                if(task == null){
                    taskId = -1;
                    taskType = "NOT_EXISTING_TASK";
                } else {
                    taskId = task.getId();
                    taskType = task.getType().name();
                }
                out.write(taskId + "|" +  taskType);
                out.newLine();
            }
            out.write(System.lineSeparator() + COUNT_ID_SEPARATOR + System.lineSeparator());
            out.write("COUNT_ID" +"|" + taskCreator.getCountId());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void restoreCondition(Path fileToRestoreCondition){
        ArrayList<String[]> regularTasks = new ArrayList<>();
        ArrayList<String[]> epicTasks = new ArrayList<>();
        ArrayList<String[]> subTasks = new ArrayList<>();
        ArrayList<String[]> history = new ArrayList<>();
        int countIdToRestore = readFromCSV(fileToRestoreCondition
                                            ,regularTasks
                                            ,epicTasks
                                            , subTasks
                                            , history);
        readFromCSV(fileToRestoreCondition, regularTasks, epicTasks, subTasks, history);

        for (String[] taskInString : regularTasks) {
            RegularTask recreatedTask = createRegularTaskFromCSV(taskInString);
            restoreInMemoryRegularTask(recreatedTask);
        }

        for (String[] taskInString : epicTasks) {
            EpicTask recreatedTask = createEpicTaskFromCSV(taskInString);
            restoreInMemoryEpicTask(recreatedTask);
        }

        for (String[] taskInString : subTasks) {
            SubTask recreatedTask = createSubTaskFromCSV(taskInString);
            restoreInMemorySubTask(recreatedTask);
        }

        for(String[] taskInfo : history){

            int taskId = Integer.parseInt(taskInfo[0]);

            if(TaskType.valueOf(taskInfo[1]) == TaskType.REGULAR_TASK){

                RegularTask taskToHistory = (RegularTask) regularTaskStorage.getStorage().get(taskId);
                inMemoryHistoryManager.add(taskToHistory);
            }

            if(TaskType.valueOf(taskInfo[1]) == TaskType.EPIC_TASK){

                EpicTask taskToHistory = (EpicTask) epicTaskStorage.getStorage().get(taskId);
                inMemoryHistoryManager.add(taskToHistory);
            }

            if(TaskType.valueOf(taskInfo[1]) == TaskType.SUBTASK){

                SubTask taskToHistory = (SubTask) subTaskStorageForTaskManager.getStorage().get(taskId);
                inMemoryHistoryManager.add(taskToHistory);
            }

            if(TaskType.valueOf(taskInfo[1]) == TaskType.NOT_EXISTING_TASK){

                inMemoryHistoryManager.add(null);
            }

            taskCreator.setCountId(countIdToRestore);
        }

        if(countIdToRestore == 0){
            System.out.println("Создан новый  Менеджер задач");
        } else {
            System.out.println("Менедежер задач загрузил информацию о задачах");
            System.out.println("Общее количество созданных за время работы менеджера" +
                    " задач равно: " + countIdToRestore);
        }

    }
    private int readFromCSV(Path fileToRestoreCondition
            , ArrayList<String[]> regularTasks
            , ArrayList<String[]> epicTasks
            , ArrayList<String[]> subTasks
            , ArrayList<String[]> history) {
        int savedCountId= 0;
        try(
                BufferedReader br = new BufferedReader(
                new FileReader(
                        fileToRestoreCondition.toAbsolutePath().toString()));){

            boolean areTasksRead = false;
            boolean isHistoryRead = false;
            while(br.ready()){
                String curString = br.readLine();

                if( curString.equals(STORAGE_HEADERS) ){
                    continue;
                }

                if( curString.equals(HISTORY_HEADERS) ){
                    continue;
                }

                if( curString.equals("") ){
                    continue;
                }

                if(curString.equals(HISTORY_SEPARATOR)){
                    areTasksRead = true;
                    continue;
                }

                if(curString.equals(COUNT_ID_SEPARATOR)){
                    isHistoryRead = true;
                    continue;
                }

                if( areTasksRead && isHistoryRead ){
                    String[] countIdInfo = curString.split("\\|");
                    savedCountId = Integer.parseInt(countIdInfo[1]);
                    continue;
                }

                if( areTasksRead ){
                    String[] historyInfo = curString.split("\\|");
                    history.add(historyInfo);
                    continue;
                }

                String[] taskContent = curString.split("\\|");
                TaskType.valueOf(taskContent[1]).name();
                if(TaskType.valueOf(taskContent[1]) ==  TaskType.REGULAR_TASK ){
                    regularTasks.add(taskContent);
                    continue;
                }

                if(TaskType.valueOf(taskContent[1]) ==  TaskType.EPIC_TASK ){
                    epicTasks.add(taskContent);
                    continue;
                }

                if(TaskType.valueOf(taskContent[1]) ==  TaskType.SUBTASK ){
                    subTasks.add(taskContent);
                }
            }
            return savedCountId;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Методы для работы с обычными задачами
    @Override
    public String createRegularTask(RegularTask task) {
        String result = super.createRegularTask(task);
        save();
        return result;
    }

    public void restoreInMemoryRegularTask(RegularTask taskToRestore){
        regularTaskStorage.saveInStorage(taskToRestore.getId(), taskToRestore);
    }
    public void restoreInMemoryEpicTask(EpicTask taskToRestore){
        epicTaskStorage.saveInStorage(taskToRestore.getId(), taskToRestore);
    }
    public void restoreInMemorySubTask(SubTask taskToRestore){
        if(taskToRestore == null){
            return;
        }
        if(!epicTaskStorage.getStorage().containsKey(taskToRestore.getEpicId())){
            return;
        }
        subTaskStorageForTaskManager.saveInStorage(taskToRestore.getId(), taskToRestore);
        EpicTask epic = (EpicTask) epicTaskStorage.getStorage().get(taskToRestore.getEpicId());
        epic.getSubTaskStorageForEpic().saveInStorage(taskToRestore.getId(), taskToRestore);
        taskUpdater.epicStatusUpdater(epic);
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
    @Override
    public String clearSubTaskStorage() {
        String result = super.clearSubTaskStorage();
        save();
        return result;
    }
    @Override
    public Task getSubTask(int id) {
        Task result = super.getSubTask(id);
        save();
        return result;
    }
    @Override
    public String updateSubTask(SubTask subTaskToUpdate) {
        String result = super.updateSubTask(subTaskToUpdate);
        save();
        return result;
    }
    @Override
    public String removeSubTask(int subId) {
        String result = super.removeSubTask(subId);
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
    @Override
    public String updateEpicTask(EpicTask epicTaskToUpdate) {
        String result = super.updateEpicTask(epicTaskToUpdate);
        save();
        return result;
    }
    @Override
    public String removeEpicTask(int epicId) {
        String result = super.removeEpicTask(epicId);
        save();
        return result;
    }
    @Override
    public HashMap<Integer, Task> getSubTaskStorage() {
        HashMap<Integer, Task> result = super.getSubTaskStorage();
        save();
        return result;
    }


// Методы создания задач из массива строк
    private RegularTask createRegularTaskFromCSV(String[] regularTasksFromCSV) {

        int id = Integer.parseInt(regularTasksFromCSV[0]);
        String name = regularTasksFromCSV[2];
        String description = regularTasksFromCSV[3];
        String status = regularTasksFromCSV[4];
        String type = regularTasksFromCSV[1];

        return new RegularTask(id
                , name
                , description
                , StatusName.valueOf(status)
                , TaskType.valueOf(type));
    }
    private EpicTask createEpicTaskFromCSV(String[] epicTasksFromCSV) {

        int id = Integer.parseInt(epicTasksFromCSV[0]);
        String name = epicTasksFromCSV[2];
        String description = epicTasksFromCSV[3];
        String type = epicTasksFromCSV[1];

        return new EpicTask(id
                , name
                , description
                , TaskType.valueOf(type));
    }
    private SubTask createSubTaskFromCSV(String[] subTasksFromCSV)
    {
        int id = Integer.parseInt(subTasksFromCSV[0]);
        String name = subTasksFromCSV[2];
        String description = subTasksFromCSV[3];
        String status = subTasksFromCSV[4];
        String type = subTasksFromCSV[1];
        int epicId = Integer.parseInt(subTasksFromCSV[5]);

        return new SubTask(id
                , name
                , description
                , StatusName.valueOf(status)
                , TaskType.valueOf(type)
                , epicId);
    }


}
