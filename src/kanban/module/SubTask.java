package kanban.module;

import java.util.Objects;

public class SubTask extends Task{
    private int epicId;
    public int getEpicId() {
        return epicId;
    }
    public SubTask(String name, String description, int epicId, int id, StatusNames status) {
        super(name, description, id, status);
        this.epicId = epicId;
    }
    @Override
    public String toString() {
        String content = "SubTask { \n";
        content = content + "Epic id подзадачи = '" + epicId + "'\n";
        content = content + "id подзадачи =  '" + getId() + "'\n";
        content = content + "Подзадача = '" + getName() + "'\n";
        content = content + "Длина описания = '" + getDescription().length() + "'\n";
        content = content + "Cтатус подзадачи = '" + getStatus().getStatusName() + "'\n";
        content = content + "}";
        return content;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEpicId());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubTask)) return false;
        SubTask otherTask = (SubTask) o;
        return getId() == otherTask.getId()
                && getName().equals(otherTask.getName())
                && getDescription().equals(otherTask.getDescription())
                && getStatus().equals(otherTask.getStatus())
                && epicId == otherTask.getEpicId();
    }
}
