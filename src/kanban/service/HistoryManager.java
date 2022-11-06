package kanban.service;

import kanban.module.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    List<Task> getHistory();

    void remove(Task task);

    void printHistory();
}
