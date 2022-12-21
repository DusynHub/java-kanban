package kanban.module;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class SubTask extends Task{
    private final int epicId;
    public int getEpicId() {
        return epicId;
    }

    public SubTask(int id
            , String name
            , String description
            , StatusName status
            , TaskType type
            , Optional<Duration> duration
            , Optional<ZonedDateTime> startTime
            , int epicId) {
        super(id, name, description, status, type, duration, startTime);
        this.epicId = epicId;
    }

    public SubTask(int id
                    , String name
                    , String description
                    , StatusName status
                    , TaskType type
                    , int epicId) {
        super(id, name, description, status, type);
        this.epicId = epicId;
    }

    public SubTask(SubTask otherSubTask) {

        super(otherSubTask.getId()
                , otherSubTask.getName()
                ,otherSubTask.getDescription()
                , otherSubTask.getStatus()
                , otherSubTask.getType());
        this.epicId = otherSubTask.getEpicId();
    }


    @Override
    public  String toString() {

        String curDuration;
        if(getDuration().isPresent()){
            curDuration = String.valueOf( getDuration().get().toMinutes());
        } else {
            curDuration = "не задана";
        }

        String curStartTime;
        if(getStartTime().isPresent()){
            curStartTime = getStartTime().get().format(DateTimeFormatter.ISO_DATE_TIME);
        } else {
            curStartTime = "не задано";
        }

        return "SubTask { \n"
         + "Epic id подзадачи = '" + epicId + "'\n"
         + "id подзадачи =  '" + getId() + "'\n"
         + "Подзадача = '" + getName() + "'\n"
         + "Длина описания = '" + getDescription().length() + "'\n"
         + "Cтатус подзадачи = '" + getStatus().getStatusName() + "'\n"
         + "Продолжительность задачи = '" + curDuration + "'\n"
         + "Время начала задачи = '" + curStartTime + "'\n"
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
        return  getId() == otherTask.getId()
                && getName().equals(otherTask.getName())
                && getDescription().equals(otherTask.getDescription())
                && epicId == otherTask.getEpicId()
                && getStatus().equals(otherTask.getStatus())
                && getDuration().equals(otherTask.getDuration())
                && getStartTime().equals(otherTask.getStartTime());
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
            curStartTime = getStartTime().get().format(DateTimeFormatter.ISO_DATE_TIME);
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
                + curStartTime + delimiter
                + getEpicId() + delimiter;
    }
}
