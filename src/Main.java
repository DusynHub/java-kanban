import kanban.service.TaskCreator;

public class Main{

    public static void main(String[] args) {
        String name = "Тестовая задача";
        String description = "Тестовое описание тестовой задачи";
        int statusId = 1;
        TaskCreator taskCreator = new TaskCreator();
        System.out.println(taskCreator.createTask(name, description, statusId));
    }
}

