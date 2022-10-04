package kanban.module;

public class SubTask extends Task{
    private int epicId;
    public int getEpicId() {
        return epicId;
    }
    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
    public SubTask(String name, String description, int epicId, int id, int statusId) {
        super(name, description, id, statusId);
        this.epicId = epicId;
    }
    @Override
    public String toString() {
        String content = "Task { \n";
        content = content + "Epic id  = '" + epicId + "'\n";
        content = content + "id задачи =  '" + getId() + "'\n";
        content = content + "Задача = '" + getName() + "'\n";
        content = content + "Длина описания = " + getDescription().length() + "'\n";
        content = content + "Cтатус задачи =  " + getStatus() + "'\n";
        content = content + "}";
        return content;
    }
}
