package kanban.service;

import kanban.module.Task;
import kanban.service.storage.RegularTaskStorage;

import java.util.HashMap;

public class TaskGetter {

    public Task getRegularTask(int id, RegularTaskStorage regularTaskStorage){

        HashMap<Integer, Task> storage = regularTaskStorage.getStorage();
        if(storage.isEmpty()){
            return storage.get(id);
        }
        if(storage.get(id) == null) {
            System.out.println("Задача с указанным id отсутствует. Возвращено пустое значение.");
        }
        return storage.get(id);
    }
}
