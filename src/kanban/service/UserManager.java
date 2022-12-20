package kanban.service;

import kanban.module.Task;
import kanban.module.user.User;

import java.util.List;

public interface UserManager {

    int add(User user);

    void update (User user);

    User getById(int userId);

    List<User> getAll();

    List<Task> getUserTasks(int getUserId);

    void delete (int userId);

    TaskManager getTaskManager();
}
