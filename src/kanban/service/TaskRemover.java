package kanban.service;

import kanban.module.Task;
import kanban.service.storage.RegularTaskStorage;

import java.util.HashMap;

public class TaskRemover {
    public String removeAllRegularTasks(RegularTaskStorage regularTaskStorage){
        return regularTaskStorage.clearStorage();
    }

    public String removeRegularTask(int id, RegularTaskStorage regularTaskStorage){

        HashMap<Integer, Task> storage = regularTaskStorage.getStorage();
        if(storage.containsKey(id)){
            storage.remove(id);
            return "Задача удалена";
        } else {
            return "Задача отсутствует. Сначала создайте задачу с соответвующим id. Удаление невозможно";
        }
    }

}
