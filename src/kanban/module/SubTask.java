package kanban.module;

import java.util.Objects;

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
        return  getId() == otherTask.getId()
                && getName().equals(otherTask.getName())
                && getDescription().equals(otherTask.getDescription())
                && epicId == otherTask.getEpicId()
                && getStatus().equals(otherTask.getStatus());
    }
    @Override
    public String toStringForCSV() {
        String delimiter = "|";
        return getId() + delimiter
                + getType().name() + delimiter
                + getName() + delimiter
                + getDescription() + delimiter
                + getStatus().name() + delimiter
                + getEpicId();
    }
}
