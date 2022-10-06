package kanban.module;

import kanban.service.storage.SubTaskStorage;

import java.util.Objects;

public class EpicTask extends Task{

    private SubTaskStorage subTaskStorageForEpic = new SubTaskStorage();
    public EpicTask(String name, String description, int id) {
        super(name, description, id, 1);
    }
    public EpicTask(EpicTask task) {
        super();
        super.setName(task.getName());
        super.setDescription(task.getDescription());
        super.setId(task.getId());
        super.setStatus(STATUS_NAME_STORAGE.get(1));
    }
    public SubTaskStorage getSubTaskStorageForEpic() {
        return subTaskStorageForEpic;
    }
    public void setSubTaskStorageForEpic(SubTaskStorage subTaskStorageForEpic) {
        this.subTaskStorageForEpic = subTaskStorageForEpic;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskStorageForEpic);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EpicTask)) return false;
        EpicTask otherTask = (EpicTask) o;
        return getId() == otherTask.getId()
                && getName().equals(otherTask.getName())
                && getDescription().equals(otherTask.getDescription())
                && getStatus().equals(otherTask.getStatus())
                && subTaskStorageForEpic.equals(otherTask.getSubTaskStorageForEpic());
    }
    @Override
    public String toString() {
        return "EpicTask { \n" +
                "id эпик задачи = '" + getId() + "'\n" +
                "Эпик задача = '" + getName() + "'\n" +
                "Длина описания = '" + getDescription().length() + "'\n" +
                "Cтатус эпик задачи = '" + getStatus() + "'\n" +
                "Количество подзадач в эпик задаче '" + subTaskStorageForEpic.getStorage().size() + "'\n" +
        "}";
    }
}
