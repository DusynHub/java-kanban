package kanban.service;

import kanban.module.RegularTask;
import kanban.module.Task;
import kanban.service.storage.RegularTaskStorage;

import java.util.HashMap;

public class TaskUpdater {
    public String updateRegularTask(RegularTask regularTaskToUpdate, RegularTaskStorage regularTaskStorage) {

        HashMap<Integer, Task> storage = regularTaskStorage.getStorage();
        if(storage.containsKey(regularTaskToUpdate.getId())){
            storage.put(regularTaskToUpdate.getId(), regularTaskToUpdate);
            return "Задача c id = "+ regularTaskToUpdate.getId() + "обновлена";
        } else {
            return "Задача отсутствует c id = " + regularTaskToUpdate.getId() + ". Сначала создайте задачу с соответвующим id. Обновление невозможно";
        }
    }
}
