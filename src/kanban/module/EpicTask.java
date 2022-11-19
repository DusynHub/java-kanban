package kanban.module;

import kanban.module.storage.SubTaskStorage;
import java.util.Objects;

public class EpicTask extends Task{

    private final SubTaskStorage subTaskStorageForEpic = new SubTaskStorage();
    public EpicTask(String name, String description, int id, TaskType type) {
        super(name, description, id, StatusName.NEW, type);
    }
    public SubTaskStorage getSubTaskStorageForEpic() {
        return subTaskStorageForEpic;
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
        return "EpicTask { \n"
                + "id эпик задачи = '" + getId() + "'\n"
                + "Эпик задача = '" + getName() + "'\n"
                + "Длина описания = '" + getDescription().length() + "'\n"
                + "Cтатус эпик задачи = '" + getStatus().getStatusName() + "'\n"
                + "Количество подзадач в эпик задаче '"
                    + subTaskStorageForEpic.getStorage().size() + "'\n"
                + "}";
    }

    @Override
    public String toStringForCSV() {
        String delimiter = " | ";
        return getId() + delimiter
                + getType().name() + delimiter
                + getName() + delimiter
                + getDescription() + delimiter
                + getStatus().name() + delimiter;
    }
}
