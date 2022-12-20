package kanban.service;

import kanban.module.Task;
import kanban.module.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryUserManager implements UserManager{

    private final HashMap<Integer, User> users = new HashMap<>();

    private final TaskManager taskManager = Managers.getDefault();


    private int generatedId = 0;

    protected int generateId(){
        return ++generatedId;
    }

    @Override
    public int add(User user) {
        int id = generateId();
        user.setId(id);
        users.put(id, user);
        return id;
    }

    @Override
    public void update(User user) {
        int id = user.getId();
        if(!users.containsKey(id)){
            return;
        }
        users.put(id, user);
    }

    @Override
    public User getById(int userId) {
        return users.get(userId);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<Task> getUserTasks(int getUserId) {
        return taskManager.getPrioritizedTasks()
                .stream()
                .filter(task -> task.getUser().getId() == getUserId)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int userId) {
        users.remove(userId);
    }

    @Override
    public TaskManager getTaskManager() {
        return taskManager;
    }
}
