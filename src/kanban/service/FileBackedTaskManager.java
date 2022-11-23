package kanban.service;

import kanban.exceptions.ManagerSaveException;
import kanban.module.*;
import kanban.module.storage.EpicTaskStorage;
import kanban.module.storage.RegularTaskStorage;
import kanban.module.storage.SubTaskStorage;
import kanban.module.storage.TaskStorage;
import kanban.util.CSVTaskFormat;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

    protected final String STORAGE_HEADERS
                                    = "ID|TYPE|TASK_NAME|DESCRIPTION|STATUS|EPIC_ID FOR SUBTASK";
    protected final String HISTORY_SEPARATOR = "BELOW_THIS_ROW_HISTORY_DATA";
    protected final String HISTORY_HEADERS = "ID|TASK_TYPE";
    protected final String COUNT_ID_SEPARATOR = "BELOW_THIS_ROW_CONT_ID_VALUE";
    Path fileToSaveCondition;
    public static void main(String[] args){

        Path pathOfStorage = Paths.get("src/kanban/taskManagerStorageInFile");
        try{
            if(!Files.exists(pathOfStorage)){
                Files.createDirectory(pathOfStorage);
            }
        } catch(IOException e){
            System.out.println("Ошибка при создании директории: ");
            e.printStackTrace();
        }

        Path pathOfFile = Paths.get(pathOfStorage.toAbsolutePath()
                + "/TaskStorage.csv");
        try{

            if(!Files.exists(pathOfFile)){
                Files.createFile(pathOfFile);
            }
        }catch(IOException e){
            System.out.println("Ошибка при создании файла хранения FileBackedTaskManager: ");
            e.printStackTrace();
        }

        RegularTask theBigLebowskiTask;
        RegularTask deathTask;
        RegularTask resentmentTask;
        RegularTask importantTask;
        {
            theBigLebowskiTask = new RegularTask(
                    0,
                    "Задача Лебовски",
                    "Где деньги, Лебовски?",
                    StatusName.NEW,
                    TaskType.REGULAR_TASK);
            deathTask = new RegularTask(
                    0,
                    "Что такое Смерть?",
                    "Смерть — это то, что бывает с другими",
                    StatusName.IN_PROGRESS,
                    TaskType.REGULAR_TASK);
            resentmentTask = new RegularTask(
                    0, "Как обижать людей?",
                    "Он всегда недолюбливал людей, которые «никого не хотели обидеть». " +
                            "Удобная фраза: произнес ее — и обижай кого хочешь.",
                    StatusName.NEW,
                    TaskType.REGULAR_TASK);
            importantTask = new RegularTask(
                    2, "Найти ответ на главный вопрос жизни, вселенной и всего такого",
                    "Может быть это 6 х 9 ?",
                    StatusName.IN_PROGRESS,
                    TaskType.REGULAR_TASK);
        }
        EpicTask cookRice;
        EpicTask doPracticumHomework;
        EpicTask doTraining;
        {
            cookRice = new EpicTask(
                    0, "Приготовить рис",
                    "Нужен гарнир из коричневого риса",
                    TaskType.EPIC_TASK);
            doPracticumHomework = new EpicTask(
                    0, "Выполнить домашнее задание практикума",
                    "Нужно успеть до 09.10.2022",
                    TaskType.EPIC_TASK);
            doTraining = new EpicTask(
                    0, "Выполнить треннировку",
                    "Выполнить 3 упражнения по 10 подходов",
                    TaskType.EPIC_TASK);
        }

        FileBackedTaskManager fileBackedTaskManager
                = FileBackedTaskManager.loadFromFile(pathOfStorage);

        SubTask subTaskCookRice1;
        SubTask subTaskCookRice2;

        System.out.println(fileBackedTaskManager.createRegularTask(theBigLebowskiTask));
        System.out.println(fileBackedTaskManager.createRegularTask(deathTask));
        System.out.println(fileBackedTaskManager.createRegularTask(resentmentTask));
        System.out.println(fileBackedTaskManager.createRegularTask(importantTask));

        System.out.println(fileBackedTaskManager.createEpicTask(cookRice));
        System.out.println(fileBackedTaskManager.createEpicTask(doPracticumHomework));
        System.out.println(fileBackedTaskManager.createEpicTask(doTraining));

        Iterator<Integer> it = fileBackedTaskManager.getEpicTaskStorage().keySet().iterator();

        int existingEpicId = -1;
        if(it.hasNext()){
            existingEpicId = it.next();
        }

        subTaskCookRice1 = new SubTask(
                0, "Промыть рис",
                "Желательно 400 гр",
                StatusName.DONE, TaskType.SUBTASK, existingEpicId);
        subTaskCookRice2 = new SubTask(
                0, "Варить 10 минут",
                "Не уходить с кухни",
                StatusName.NEW, TaskType.SUBTASK, existingEpicId);

        System.out.println(fileBackedTaskManager.createSubTask(subTaskCookRice1));
        System.out.println(fileBackedTaskManager.createSubTask(subTaskCookRice2));

        for(Integer key : fileBackedTaskManager.getRegularTaskStorage().keySet()){
            System.out.println(fileBackedTaskManager.getRegularTask(key));
        }


        for(Integer key : fileBackedTaskManager.getSubTaskStorage().keySet()){
            System.out.println(fileBackedTaskManager.getSubTask(key));
        }
// Вызов несуществующей задачи
        System.out.println(fileBackedTaskManager.getSubTask(-354));

        for(Integer key : fileBackedTaskManager.getEpicTaskStorage().keySet()){
            System.out.println(fileBackedTaskManager.getEpicTask(key));
        }

        System.out.println("\nИстория вызовов первого менеджера");
        fileBackedTaskManager.getHistoryOfTasks();

        System.out.println();

        FileBackedTaskManager fileBackedTaskManager2
                = FileBackedTaskManager.loadFromFile(pathOfStorage);
        System.out.println("\nИстория вызовов второго менеджера");
        fileBackedTaskManager2.getHistoryOfTasks();


        System.out.println("\nПеречень обычных задач первого менеджера");
        fileBackedTaskManager.printRegularTaskStorage();
        System.out.println("\nПеречень обычных задач второго менеджера");
        fileBackedTaskManager2.printRegularTaskStorage();

        System.out.println("\nПеречень эпик задач первого менеджера");
        fileBackedTaskManager.printEpicTaskStorage();
        System.out.println("\nПеречень эпик задач второго менеджера");
        fileBackedTaskManager2.printEpicTaskStorage();

        System.out.println("\nПеречень подзадач первого менеджера");
        fileBackedTaskManager.printSubTaskStorage();
        System.out.println("\nПеречень подзадач второго менеджера");
        fileBackedTaskManager2.printSubTaskStorage();
    }

    public FileBackedTaskManager() {

        Path pathOfStorage = Paths.get("src/kanban/taskManagerStorageInFile");
        try{
            if(!Files.exists(pathOfStorage)){
                Files.createDirectory(pathOfStorage);
            }
        } catch(IOException e){
            System.out.println("Ошибка при создании директории: ");
            e.printStackTrace();
        }

        Path pathOfFile = Paths.get(pathOfStorage.toAbsolutePath()
                + "/TaskStorage.csv");
        try{

            if(!Files.exists(pathOfFile)){
                Files.createFile(pathOfFile);
            }
        }catch(IOException e){
            System.out.println("Ошибка при создании файла хранения FileBackedTaskManager: ");
            e.printStackTrace();
        }

        this.fileToSaveCondition = pathOfFile;
        restoreCondition(pathOfFile);
    }
    public static FileBackedTaskManager loadFromFile(Path pathOfFile){
        return new FileBackedTaskManager();
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
            , HistoryManager inMemoryHistoryManager) throws ManagerSaveException    {

        List<TaskStorage> storages = new ArrayList<>();
        storages.add(regularTaskStorage);
        storages.add(epicTaskStorage);
        storages.add(subTaskStorage);

        try (BufferedWriter out =
                     new BufferedWriter(
                             new FileWriter(fileToSaveCondition.toAbsolutePath().toString())
                     )
        ) {
            storagesToCSV(storages, out);
            historyToCSV(inMemoryHistoryManager, out);
            countIdToCSV(out);
        } catch (IOException e) {
            throw new ManagerSaveException(e,"Ошибка при сохранении состояния менеджера");
        }
    }
    private void countIdToCSV(BufferedWriter out) throws IOException {
        out.write(System.lineSeparator() + COUNT_ID_SEPARATOR + System.lineSeparator());
        out.write("COUNT_ID" +"|" + taskCreator.getCountId());
    }
    private void storagesToCSV(List<TaskStorage> storages, BufferedWriter out) throws IOException {
        out.write(STORAGE_HEADERS + System.lineSeparator());

        for (TaskStorage storage : storages) {
            for (Map.Entry<Integer, Task> entry : storage.getStorage().entrySet()) {
                Task taskToCSV = entry.getValue();
                out.write(taskToCSV.toStringForCSV());
                out.newLine();
            }
        }
    }
    private void historyToCSV(HistoryManager inMemoryHistoryManager
                                , BufferedWriter out
                                 ) throws IOException {
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
    }
    private void restoreCondition(Path fileToRestoreCondition){
        ArrayList<String[]> regularTasks = new ArrayList<>();
        ArrayList<String[]> epicTasks = new ArrayList<>();
        ArrayList<String[]> subTasks = new ArrayList<>();
        ArrayList<String[]> history = new ArrayList<>();

        int countIdToRestore = readConditionFromCSV(fileToRestoreCondition
                                            ,regularTasks
                                            ,epicTasks
                                            , subTasks
                                            , history);


        for (String[] taskInString : regularTasks) {
            RegularTask recreatedTask = CSVTaskFormat.createRegularTaskFromCSV(taskInString);
            restoreInMemoryRegularTask(recreatedTask);
        }

        for (String[] taskInString : epicTasks) {
            EpicTask recreatedTask = CSVTaskFormat.createEpicTaskFromCSV(taskInString);
            restoreInMemoryEpicTask(recreatedTask);
        }

        for (String[] taskInString : subTasks) {
            SubTask recreatedTask = CSVTaskFormat.createSubTaskFromCSV(taskInString);
            restoreInMemorySubTask(recreatedTask);
        }

        restoreHistoryFromCSV(history);

        taskCreator.setCountId(countIdToRestore);

        if(countIdToRestore == 0){
            System.out.println("Создан новый  Менеджер задач");
        } else {
            System.out.println("Менедежер задач загрузил информацию о задачах");
            System.out.println("Общее количество созданных за время работы менеджера" +
                    " задач равно: " + countIdToRestore);
        }

    }
    private void restoreHistoryFromCSV(ArrayList<String[]> history) {
        for(String[] taskInfo : history){

            int taskId = Integer.parseInt(taskInfo[0]);

            if(TaskType.valueOf(taskInfo[1]) == TaskType.REGULAR_TASK){

                RegularTask taskToHistory
                        = (RegularTask) regularTaskStorage.getStorage().get(taskId);
                inMemoryHistoryManager.add(taskToHistory);
            }

            if(TaskType.valueOf(taskInfo[1]) == TaskType.EPIC_TASK){

                EpicTask taskToHistory = (EpicTask) epicTaskStorage.getStorage().get(taskId);
                inMemoryHistoryManager.add(taskToHistory);
            }

            if(TaskType.valueOf(taskInfo[1]) == TaskType.SUBTASK){

                SubTask taskToHistory
                        = (SubTask) subTaskStorageForTaskManager.getStorage().get(taskId);
                inMemoryHistoryManager.add(taskToHistory);
            }

            if(TaskType.valueOf(taskInfo[1]) == TaskType.NOT_EXISTING_TASK){

                inMemoryHistoryManager.add(null);
            }
        }
    }
    private int readConditionFromCSV(Path fileToRestoreCondition
            , ArrayList<String[]> regularTasks
            , ArrayList<String[]> epicTasks
            , ArrayList<String[]> subTasks
            , ArrayList<String[]> history) {
        int savedCountId= 0;
        try(
                BufferedReader br = new BufferedReader(
                new FileReader(
                        fileToRestoreCondition.toAbsolutePath().toString()))){

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
    private void restoreInMemoryRegularTask(RegularTask taskToRestore){
        regularTaskStorage.saveInStorage(taskToRestore.getId(), taskToRestore);
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
    private void restoreInMemorySubTask(SubTask taskToRestore){
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
    private void restoreInMemoryEpicTask(EpicTask taskToRestore){
        epicTaskStorage.saveInStorage(taskToRestore.getId(), taskToRestore);
    }
}
