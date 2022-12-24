package kanban.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kanban.module.Task;
import kanban.server.KVClient;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class HttpTaskManager extends FileBackedTaskManager {

    private Gson gson = Managers.getGson();

    private KVClient kVClient;

    public HttpTaskManager(String URL) {
        try{
            this.kVClient = new KVClient(URL);
            this.restoreConditionFromServer();
        } catch(IOException | InterruptedException e) {
            System.out.println("Ошибка в конструкторе HttpTaskManager");
        }

    }

    @Override
    protected void save() {
        try {
            String regularTaskStorage = gson.toJson(super.regularTaskStorage.getStorage());
            kVClient.put("regularTasks", regularTaskStorage);

            String epicTaskStorage = gson.toJson(super.epicTaskStorage.getStorage());
            kVClient.put("epicTasks", epicTaskStorage);

            String subTaskStorage = gson.toJson(super.epicTaskStorage.getStorage());
            kVClient.put("subTasks", subTaskStorage);

            String historyManagerInList = gson.toJson(getHistoryOfTasks());
            kVClient.put("history", historyManagerInList);

            String currentId = gson.toJson(taskCreator.getCountId());
            kVClient.put("currentIdCounter", currentId);

            String prioritizedInList = gson.toJson(getPrioritizedTasks());
            kVClient.put("prioritizedInList", prioritizedInList);
        } catch(InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }


    protected void restoreConditionFromServer() throws IOException, InterruptedException {
        Type hashMapType = new TypeToken<HashMap<Integer, Task>>() {}.getType();

        HashMap<Integer, Task> regularTaskStorageFromServer = gson.fromJson(kVClient.load("regularTasks"), hashMapType);
        if( regularTaskStorageFromServer != null && !regularTaskStorageFromServer.isEmpty()){
            for(Map.Entry<Integer, Task> entry: regularTaskStorageFromServer.entrySet()){
                regularTaskStorage.getStorage().put(entry.getKey(), entry.getValue());
            }
        }

        HashMap<Integer, Task> epicTaskStorageFromServer = gson.fromJson(kVClient.load("epicTasks"), hashMapType);
        if(epicTaskStorageFromServer != null &&!epicTaskStorageFromServer.isEmpty()){
            for(Map.Entry<Integer, Task> entry: epicTaskStorageFromServer.entrySet()){
                epicTaskStorage.getStorage().put(entry.getKey(), entry.getValue());
            }
        }

        HashMap<Integer, Task> subTaskStorageFromServer = gson.fromJson(kVClient.load("subTasks"), hashMapType);
        if( subTaskStorageFromServer != null && !subTaskStorageFromServer.isEmpty()){
            for(Map.Entry<Integer, Task> entry: subTaskStorageFromServer.entrySet()){
                subTaskStorageForTaskManager.getStorage().put(entry.getKey(), entry.getValue());
            }
        }

        Type listType = new TypeToken<ArrayList<Task>>() {}.getType();

        List<Task> historyInList = gson.fromJson(kVClient.load("history"), listType);

        if(!(historyInList == null) && !historyInList.isEmpty()) {
            for (Task task : historyInList) {
                inMemoryHistoryManager.add(task);
            }
        }
        Integer countId = gson.fromJson(kVClient.load("currentIdCounter"), Integer.class);
        if(!(countId == null)) {
            taskCreator.setCountId(countId);
        }

        List<Task> prioritized = gson.fromJson(kVClient.load("prioritizedInList"), listType);
        if(!(prioritized == null) && !prioritized.isEmpty()) {
                getPrioritized().addAll(prioritized);
        }
    }
}
