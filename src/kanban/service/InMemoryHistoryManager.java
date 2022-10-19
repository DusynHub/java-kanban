package kanban.service;

import kanban.module.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private ArrayList<Task> taskHistory = new ArrayList<>();
    private final int defaultHistorySize = 10;

    @Override
    public void add(Task task) {
        if(taskHistory.size() < defaultHistorySize) {
            taskHistory.add(task);
        }

        if(taskHistory.size() == defaultHistorySize){
            taskHistory.remove(0);
            taskHistory.add(task);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return taskHistory;
    }

    @Override
    public void printHistory(){
        for(int i = 0; i < taskHistory.size(); i++){
            if(taskHistory.get(i) == null){
                System.out.println("Вызов " + (i + 1) + ": Вызванная задача отсутствовала");
            } else {
                System.out.println("Вызов " + (i + 1) + ": " + taskHistory.get(i));
            }
        }
    }
}
