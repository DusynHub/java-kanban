package kanban.module;

public class RegularTask extends Task{
    public RegularTask(int id, String name
                        , String description
                        , StatusName status
                        , TaskType type) {
        super(id, name, description, status, type);
    }
    @Override
    public String toString() {
        return "RegularTask { \n"
        + "id задачи = '" + getId() + "'\n"
        + "Задача = '" + getName() + "'\n"
        + "Длина описания = '" + getDescription().length() + "'\n"
        + "Cтатус задачи = '" + getStatus().getStatusName() + "'\n"
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
                && getStatus().equals(otherTask.getStatus());
    }
    @Override
    public String toStringForCSV() {
        String delimiter = "|";
        return getId() + delimiter
                + getType().name() + delimiter
                + getName() + delimiter
                + getDescription() + delimiter
                + getStatus().name() + delimiter;
    }
}
