package kanban.service;

import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.SubTask;
import kanban.module.Task;
import kanban.service.storage.EpicTaskStorage;
import kanban.service.storage.RegularTaskStorage;
import kanban.service.storage.SubTaskStorage;

import java.util.HashMap;
import java.util.Iterator;

public class TaskUpdater {
    public String updateRegularTask(RegularTask regularTaskToUpdate, RegularTaskStorage regularTaskStorage) {

        HashMap<Integer, Task> storage = regularTaskStorage.getStorage();
        if(storage.containsKey(regularTaskToUpdate.getId())){
            storage.put(regularTaskToUpdate.getId(), regularTaskToUpdate);
            return "Задача c id = "+ regularTaskToUpdate.getId() + " обновлена.";
        } else {
            return "Задача отсутствует c id = " + regularTaskToUpdate.getId() + ". Сначала создайте задачу с соответвующим id. Обновление невозможно.";
        }
    }
    public String updateEpicTask(EpicTask epicTaskToUpdate, EpicTaskStorage epicTaskStorage) {

        HashMap<Integer, Task> storage = epicTaskStorage.getStorage();
        if(storage.containsKey(epicTaskToUpdate.getId())){
            storage.put(epicTaskToUpdate.getId(), epicTaskToUpdate);
            return "Эпик задача c id = " + epicTaskToUpdate.getId() + " обновлена";
        } else {
            return "Эпик задача отсутствует c id = " + epicTaskToUpdate.getId() + ". Сначала создайте задачу с соответвующим id. Обновление невозможно.";
        }
    }

    public String updateSubTask(SubTask subTaskToUpdate, SubTaskStorage subTaskStorage, EpicTaskStorage epicTaskStorage) {

        HashMap<Integer, Task> storage = subTaskStorage.getStorage();
        if(storage.containsKey(subTaskToUpdate.getId())){
            storage.put(subTaskToUpdate.getId(), subTaskToUpdate);
            EpicTask epicTask = (EpicTask) epicTaskStorage.getStorage().get(subTaskToUpdate.getEpicId());
            epicTask.getSubTaskStorageForEpic().saveInStorage(subTaskToUpdate.getId(), subTaskToUpdate);

            return "Подзадача c id = " + subTaskToUpdate.getId() + " обновлена";
        } else {
            return "Подзадача отсутствует c id = " + subTaskToUpdate.getId() + ". Сначала создайте подзадачу с соответвующим id. Обновление невозможно.";
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
