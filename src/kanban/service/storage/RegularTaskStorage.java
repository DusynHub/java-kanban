package kanban.service.storage;

import kanban.module.Task;

import java.util.HashMap;

public class RegularTaskStorage extends TaskStorage {

    private HashMap<Integer, Task> storage = new HashMap<>();

    @Override
    public HashMap<Integer, Task> getStorage() {

        return storage;
    }
    public void saveInStorage(int id, Task taskToSave){

        storage.put(id, taskToSave);
    }


}
