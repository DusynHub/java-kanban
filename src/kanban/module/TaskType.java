package kanban.module;

public enum TaskType {

    REGULAR_TASK("Обычная задача"),
    SUBTASK("Подзадача"),
    EPIC_TASK("Эпик задача"),
    NOT_EXISTING_TASK("Не существует");

    private String typeName;

    TaskType(String typeName){
        this.typeName = typeName;
    }

}
