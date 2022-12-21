package kanban.module;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class RegularTask extends Task{

    public RegularTask(int id
                        , String name
                        , String description
                        , StatusName status
                        , TaskType type
                        , Optional<Duration> duration
                        , Optional<ZonedDateTime> startTime
                       )
    {
        super(id, name, description, status, type, duration, startTime);
    }

    public RegularTask(int id, String name
                        , String description
                        , StatusName status
                        , TaskType type) {
        super(id, name, description, status, type);
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
            curStartTime = getStartTime().get().format(DateTimeFormatter.ISO_DATE_TIME);
        } else {
            curStartTime = "не задано";
        }

        return "RegularTask { \n"
        + "id задачи = '" + getId() + "'\n"
        + "Задача = '" + getName() + "'\n"
        + "Длина описания = '" + getDescription().length() + "'\n"
        + "Cтатус задачи = '" + getStatus().getStatusName() + "'\n"
        + "Продолжительность задачи = '" + curDuration + "'\n"
        + "Время начала задачи = '" + curStartTime + "'\n"
        + "}";
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegularTask)) return false;
        RegularTask otherTask = (RegularTask) o;
        return getId() == otherTask.getId()
                && getName().equals(otherTask.getName())
                && getDescription().equals(otherTask.getDescription())
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
                + curStartTime;
    }
}
