package kanban.service;

import kanban.module.Task;
import java.util.ArrayList;

public interface HistoryManager {

    void add(Task task);

    ArrayList<Task> getHistory();

    void printHistory();
}
