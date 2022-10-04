package kanban.service.storage;

import kanban.module.Task;

import java.util.HashMap;

public class RegularTaskStorage extends TaskStorage {
    private HashMap<Integer, Task> storage = new HashMap<>();
    @Override
    public HashMap<Integer, Task> getStorage() {
        return storage;
    }
    @Override
    public void saveInStorage(int id, Task taskToSave){
        storage.put(id, taskToSave);
    }
    public void printRegularTaskStorage(){
        for(Integer id : storage.keySet()){
            printLine();
            System.out.println(storage.get(id));
        }
        printLine();
    }
    private void printLine(){
        for (int i = 0; i < 100; i++) {
            System.out.print("=");
        }
        System.out.println();
    }
}
