package kanban.module;

import kanban.module.storage.SubTaskStorage;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

public class EpicTask extends Task{

    private final SubTaskStorage subTaskStorageForEpic = new SubTaskStorage();
    private Optional<ZonedDateTime> endTime;
    public EpicTask(int id, String name, String description, TaskType type) {
        super(id, name, description, StatusName.NEW, type);
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
                && getDuration().equals(otherTask.getDuration())
                && getStartTime().equals(otherTask.getStartTime())
                && subTaskStorageForEpic.equals(otherTask.getSubTaskStorageForEpic()
                );
    }
    @Override
    public String toString() {

        String curDuration;
        if(getDuration().isPresent()){
            curDuration = String.valueOf( getDuration().get().toMinutes());
        } else {
            curDuration = "не задана";
        }

        String curStartTime;
        if(getStartTime().isPresent()){
            curStartTime = getStartTime().get().format(getFormatter());
        } else {
            curStartTime = "не задано";
        }


        return "EpicTask { \n"
                + "id эпик задачи = '" + getId() + "'\n"
                + "Эпик задача = '" + getName() + "'\n"
                + "Длина описания = '" + getDescription().length() + "'\n"
                + "Cтатус эпик задачи = '" + getStatus().getStatusName() + "'\n"
                + "Количество подзадач в эпик задаче '"
                    + subTaskStorageForEpic.getStorage().size() + "'\n"
                + "Продолжительность задачи = '" + curDuration + "'\n"
                + "Время начала задачи = '" + curStartTime + "'\n"
                + "}";
    }

    @Override
    public String toStringForCSV() {
        String curDuration;
        if(getDuration().isPresent()){
            curDuration = String.valueOf( getDuration().get().toMinutes());
        } else {
            curDuration = "не задана";
        }

        String curStartTime;
        if(getStartTime().isPresent()){
            curStartTime = getStartTime().get().format(getFormatter());
        } else {
            curStartTime = "не задано";
        }

        String delimiter = "|";
        return getId() + delimiter
                + getType().name() + delimiter
                + getName() + delimiter
                + getDescription() + delimiter
                + getStatus().name() + delimiter
                + curDuration + delimiter
                + curStartTime;
    }

    @Override
    public Optional<ZonedDateTime> getEndTime() {
        return endTime;
    }

    public void setEndTime(Optional<ZonedDateTime> endTime) {
        this.endTime = endTime;
    }
}
