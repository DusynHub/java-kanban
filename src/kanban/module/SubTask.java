package kanban.module;

import java.util.Objects;

public class SubTask extends Task{
    private int epicId;
    public int getEpicId() {
        return epicId;
    }
    public SubTask(String name, String description, int epicId, int id, StatusName status) {
        super(name, description, id, status);
        this.epicId = epicId;
    }
    @Override
    public  String toString() {
        return "SubTask { \n"
         + "Epic id подзадачи = '" + epicId + "'\n"
         + "id подзадачи =  '" + getId() + "'\n"
         + "Подзадача = '" + getName() + "'\n"
         + "Длина описания = '" + getDescription().length() + "'\n"
         + "Cтатус подзадачи = '" + getStatus().getStatusName() + "'\n"
         + "}";
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
                && epicId == otherTask.getEpicId()
                && getStatus().equals(otherTask.getStatus());
    }
}
