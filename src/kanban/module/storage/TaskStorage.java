package kanban.module.storage;

import kanban.module.Task;

import java.util.HashMap;
import java.util.Map;

public abstract class TaskStorage {
    protected Map<Integer, Task> storage = new HashMap<>();
    public abstract Map<Integer, Task> getStorage();
    public abstract void saveInStorage(int id, Task taskToSave);
    public abstract void printStorage();
    public abstract String clearStorage();
}
