package kanban.service;

import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.Task;
import kanban.service.storage.RegularTaskStorage;

import java.util.HashMap;
import java.util.Iterator;

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

    public void epicStatusUpdater(EpicTask epicTask){
        HashMap<Integer, Task> storage = epicTask.getSubTaskStorageForEpic().getStorage();

        if(storage.isEmpty()){
            epicTask.setStatus(1);
            return;
        }

        String value = null;

        Iterator<Integer> iterator = storage.keySet().iterator();
        if(iterator.hasNext()){
            value = storage.get( iterator.next() ).getStatus();
        }

        for(Integer subTaskId : storage.keySet()){

            String subTaskStatus = storage.get(subTaskId).getStatus();
            if(!value.equals(subTaskStatus)){
                epicTask.setStatus(2);
                return;
            }
        }
        epicTask.setStatus(value);
    }
}
