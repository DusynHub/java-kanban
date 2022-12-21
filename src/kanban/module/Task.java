package kanban.module;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;


/**
 * The type Task.
 */
public abstract class Task {
    private String name;
    private String description;
    private int id;
    private StatusName status;
    private TaskType type;
    private Optional<Duration> duration;
    private Optional<ZonedDateTime> startTime;
    //private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public Task(int id
            , String name
            , String description
            , StatusName status
            , TaskType type
            , Optional<Duration> duration
            , Optional<ZonedDateTime> startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(int id, String name, String description, StatusName status, TaskType type) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
        this.duration = Optional.empty();
        this.startTime = Optional.empty();
    }

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Task() {
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
        return Objects.hash(name, description, id, status, duration, startTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task otherTask = (Task) o;
        return id == otherTask.getId()
                && name.equals(otherTask.getName())
                && description.equals(otherTask.getDescription())
                && status.equals(otherTask.getStatus())
                && duration.equals(otherTask.duration)
                && startTime.equals(otherTask.startTime);
    }

    public String toStringForCSV() {
        String delimiter = "|";
        return getId() + delimiter
                + "Task" + delimiter
                + getName() + delimiter
                + getDescription() + delimiter
                + getStatus().name() + delimiter
                + getType().name();
    }

    public Optional<Duration> getDuration() {
        return duration;
    }

    public Optional<ZonedDateTime> getStartTime() {
        return startTime;
    }

    public Optional<ZonedDateTime> getEndTime(){

        if(startTime.isPresent() && duration.isPresent()){
            return Optional.of(startTime.get().plusMinutes(duration.get().toMinutes()));
        } else{
            return Optional.empty();
        }
    }

//    public DateTimeFormatter getFormatter() {
//        return formatter;
//    }

    public void setDuration(Optional<Duration> duration) {
        this.duration = duration;
    }

    public void setStartTime(Optional<ZonedDateTime> startTime) {
        this.startTime = startTime;
    }
}
