package kanban.service;

import kanban.module.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{

    private ArrayList<Task> taskHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        if(taskHistory.size() < 10) {
            taskHistory.add(task);
        }

        if(taskHistory.size() == 10){
            ArrayList<Task> newHistory = new ArrayList<>();
            for(int i = 1; i < 10; i++){
                newHistory.add(taskHistory.get(i));
            }
            newHistory.add(task);
            taskHistory = newHistory;
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
