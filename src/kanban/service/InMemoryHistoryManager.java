package kanban.service;

import kanban.module.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private List<Task> taskHistory = new ArrayList<>();
    private final CustomLinkedList linkedStorage = new CustomLinkedList();


    @Override
    public void add(Task task) {

          linkedStorage.addLast(task);
    }
    @Override
    public void remove(Task task){
        linkedStorage.remove(linkedStorage.getNode(task));
    }

    @Override
    public List<Task> getHistory() {

        return taskHistory = linkedStorage.getHistoryInList();
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
