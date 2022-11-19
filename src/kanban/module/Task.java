package kanban.module;

import java.util.Objects;


/**
 * The type Task.
 */
public abstract class Task {
    private String name;
    private String description;
    private int id;
    private StatusName status;
    private TaskType type;
    public Task(String name, String description, int id, StatusName status, TaskType type) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
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
    public String getDescription() {
        return description;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public StatusName getStatus() {
        return status;
    }
    public TaskType getType() {
        return type;
    }
    public void setStatus(StatusName status) {
        this.status = status;
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

    public String toStringForCSV() {
        String delimiter = " | ";
        return getId() + delimiter
                + "Task" + delimiter
                + getName() + delimiter
                + getDescription() + delimiter
                + getStatus().name() + delimiter
                + getType().name();
    }
}
