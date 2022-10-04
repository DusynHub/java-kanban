package kanban.service.storage;

import kanban.module.Task;

import java.util.HashMap;

public class RegularTaskStorage extends TaskStorage {

    @Override
    public HashMap<Integer, Task> getStorage() {
        if(storage.isEmpty()){
            System.out.println("Ни одной задачи не было создано. Данные о задачах отсутсвуют. Необходимо сначала создать задачу.");
        }
        return storage;
    }
    @Override
    public void saveInStorage(int id, Task taskToSave){
        storage.put(id, taskToSave);
    }
    public void printRegularTaskStorage(){
        if(storage.isEmpty()){
            System.out.println("Нет задач для печати");
            return;
        }
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

    public String clearStorage(){
        if(storage.isEmpty()){
            return "Нет задач для удаления\n";
        }
        storage.clear();
        return "Все обычные задачи удалены";
    }
}
