package kanban.service.storage;

import kanban.module.Task;

import java.util.HashMap;

public class SubTaskStorage extends TaskStorage {
    @Override
    public HashMap<Integer, Task> getStorage() {
//        if(storage.isEmpty()){
//            System.out.println("Ни одной подзадачи не было создано. Данные о задачах отсутсвуют. Необходимо сначала создать задачу.");
//        }
        return storage;
    }
    @Override
    public void saveInStorage(int id, Task taskToSave){

        storage.put(id, taskToSave);
    }
    @Override
    public void printStorage(){
        if(storage.isEmpty()){
            System.out.println("Нет подзадач для печати");
            return;
        }
        for(Integer id : storage.keySet()){
            printLine();
            System.out.println(storage.get(id));
        }
        printLine();
    }
    @Override
    public String clearStorage(){
        if(storage.isEmpty()){
            return "Нет подзадач для удаления\n";
        }
        storage.clear();
        return "Все подзадачи удалены";
    }
    private void printLine(){
        for (int i = 0; i < 100; i++) {
            System.out.print("=");
        }
        System.out.println();
    }
}
