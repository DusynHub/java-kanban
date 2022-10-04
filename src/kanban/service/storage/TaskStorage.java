package kanban.service.storage;

import kanban.module.Task;

import java.util.HashMap;

public abstract class TaskStorage {

    protected HashMap<Integer, Task> storage = new HashMap<>();
    public abstract HashMap<Integer, Task> getStorage();

    public abstract void saveInStorage(int id, Task taskToSave);
}
