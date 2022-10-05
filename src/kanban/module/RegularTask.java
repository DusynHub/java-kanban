package kanban.module;

import java.util.Objects;

public class RegularTask extends Task{
    public RegularTask(String name, String description, int id, int statusId) {
        super(name, description, id, statusId);
    }
    public RegularTask(RegularTask task) {
        super();
        super.setName(task.getName());
        super.setDescription(task.getDescription());
        super.setId(task.getId());
        super.setStatus(task.getStatus());
    }
    @Override
    public String toString() {
        String content = "RegularTask { \n";
        content = content + "id задачи = '" + getId() + "'\n";
        content = content + "Задача = '" + getName() + "'\n";
        content = content + "Длина описания = '" + getDescription().length() + "'\n";
        content = content + "Cтатус задачи = '" + getStatus() + "'\n";
        content = content + "}";
        return content;
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
}
