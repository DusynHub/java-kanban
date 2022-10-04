package kanban.module;

public class RegularTask extends Task{

    public RegularTask(String name, String description, int id, int statusId) {
        super(name, description, id, statusId);
    }

    @Override
    public String toString() {
        String content = "RegularTask { \n";
        content = content + "id задачи = '" + getId() + "'\n";
        content = content + "Задача = '" + getName() + "'\n";
        content = content + "Длина описания = " + getDescription().length() + "'\n";
        content = content + "Cтатус задачи = " + getStatus() + "'\n";
        content = content + "}";
        return content;
    }
}
