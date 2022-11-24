package kanban.module.storage;
import kanban.module.EpicTask;
import kanban.module.Task;
import java.util.HashMap;

public class EpicTaskStorage extends TaskStorage {
    @Override
    public HashMap<Integer, Task> getStorage() {
        return (HashMap<Integer, Task>) storage;
    }
    @Override
    public void saveInStorage(int id, Task taskToSave){

        storage.put(id, taskToSave);
    }
    @Override
    public void printStorage(){
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
    @Override
    public String clearStorage(){
        if(storage.isEmpty()){
            return "Нет задач для удаления\n";
        }
        storage.clear();
        return "Все эпик задачи и из подзадачи удалены";
    }
    private void printLine(){
        for (int i = 0; i < 100; i++) {
            System.out.print("=");
        }
        System.out.println();
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof EpicTaskStorage)) return false;
        EpicTaskStorage otherStorage = (EpicTaskStorage) o;

        boolean result =  true;

        for(Integer key : storage.keySet()){
            if(!otherStorage.getStorage().containsKey(key)){
                result = false;
            } else{
                EpicTask thisEpicTask = (EpicTask) this.getStorage().get(key);
                EpicTask toCheck = (EpicTask) otherStorage.getStorage().get(key);
                if(!thisEpicTask.equals(toCheck)){
                    result =false;
                }
            }
        }
        return result;
    }
}
