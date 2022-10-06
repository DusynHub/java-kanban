package kanban.module;

import java.util.HashMap;
import java.util.Objects;

public abstract class Task {
    private String name;
    private String description;
    private int id;
    private String status;
    protected static final HashMap<Integer, String> STATUS_NAME_STORAGE = new HashMap<>();
    static {
        STATUS_NAME_STORAGE.put(1, "NEW");
        STATUS_NAME_STORAGE.put(2, "IN_PROGRESS");
        STATUS_NAME_STORAGE.put(3, "DONE");
    }
    public Task(String name, String description, int id, int statusId) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = STATUS_NAME_STORAGE.get(statusId);
    }
    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }
    public Task(){

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setStatus(int statusId) {
        this.status = STATUS_NAME_STORAGE.get(statusId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.getId()
                && name.equals(task.getName())
                && description.equals(task.getDescription())
                && status.equals(task.getStatus());
    }
}
