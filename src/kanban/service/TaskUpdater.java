package kanban.service;

import kanban.module.*;
import kanban.module.storage.EpicTaskStorage;
import kanban.module.storage.RegularTaskStorage;
import kanban.module.storage.SubTaskStorage;
import kanban.util.StartDateValidator;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.TreeSet;

public class TaskUpdater {
    public String updateRegularTask(RegularTask regularTaskToUpdate
                                    , RegularTaskStorage regularTaskStorage
                                    , TreeSet<Task> prioritized)
    {
        if(!StartDateValidator.validateStartDate(regularTaskToUpdate, prioritized, true)){
            return "Время выполнения обновлённого RegularTask пересекается с имеющимися заданиями"
                    + "Здача не была обновлена. Изменити длительность или время начала";

        }

        HashMap<Integer, Task> storage = regularTaskStorage.getStorage();
        if(storage.containsKey(regularTaskToUpdate.getId())){
            prioritized.remove(regularTaskStorage.getStorage().get(regularTaskToUpdate.getId()));
            prioritized.add(regularTaskToUpdate);
            storage.put(regularTaskToUpdate.getId(), regularTaskToUpdate);
            return "Задача c id = "+ regularTaskToUpdate.getId() + " обновлена.";
        } else {
            return "Задача отсутствует c id = " + regularTaskToUpdate.getId()
                    + ". Сначала создайте задачу с соответвующим id. Обновление невозможно.";
        }
    }
    public String updateEpicTask(EpicTask epicTaskToUpdate, EpicTaskStorage epicTaskStorage) {

        HashMap<Integer, Task> storage = epicTaskStorage.getStorage();

        if(storage == null){
            return "Эпик задача отсутствует c id = " + epicTaskToUpdate.getId()
                    + ". Сначала создайте задачу с соответвующим id. Обновление невозможно.";
        }

        if(storage.containsKey(epicTaskToUpdate.getId())){
            storage.put(epicTaskToUpdate.getId(), epicTaskToUpdate);
            return "Эпик задача c id = " + epicTaskToUpdate.getId() + " обновлена";
        } else {
            return "Эпик задача отсутствует c id = " + epicTaskToUpdate.getId()
                    + ". Сначала создайте задачу с соответвующим id. Обновление невозможно.";
        }
    }

    public String updateSubTask(SubTask subTaskToUpdate
                                , SubTaskStorage subTaskStorage
                                , EpicTaskStorage epicTaskStorage
                                , TreeSet<Task> prioritized)
    {
        HashMap<Integer, Task> storage = subTaskStorage.getStorage();
        if(storage.containsKey(subTaskToUpdate.getId())){
            if(!StartDateValidator.validateStartDate(subTaskToUpdate, prioritized, true)){
                return "Время выполнения обновлённого SubTask пересекается с имеющимися заданиями"
                        + "Здача не была обновлена";

            }
            prioritized.remove(subTaskToUpdate);
            prioritized.add(subTaskToUpdate);
            storage.put(subTaskToUpdate.getId(), subTaskToUpdate);
            EpicTask epicTask = (EpicTask) epicTaskStorage.getStorage().get(subTaskToUpdate.getEpicId());
            epicTask.getSubTaskStorageForEpic().saveInStorage(subTaskToUpdate.getId(), subTaskToUpdate);

            return "Подзадача c id = " + subTaskToUpdate.getId() + " обновлена";
        } else {
            return "Подзадача отсутствует c id = " + subTaskToUpdate.getId()
                    + ". Сначала создайте подзадачу с соответвующим id. Обновление невозможно.";
        }
    }

    public void epicStatusUpdater(EpicTask epicTask){
        HashMap<Integer, Task> storage = epicTask.getSubTaskStorageForEpic().getStorage();

        if(storage.isEmpty()){
            epicTask.setStatus(StatusName.NEW);
            return;
        }

        StatusName value = null;

        Iterator<Integer> iterator = storage.keySet().iterator();
        if(iterator.hasNext()){
            value = storage.get( iterator.next() ).getStatus();
        }

        for(Integer subTaskId : storage.keySet()){

            StatusName subTaskStatus = storage.get(subTaskId).getStatus();
            if(!value.equals(subTaskStatus) && (value != null)){
                epicTask.setStatus(StatusName.IN_PROGRESS);
                return;
            }
        }
        epicTask.setStatus(value);
    }

    public void epicStartTimeUpdater(EpicTask epicTask){
        HashMap<Integer, Task> storage = epicTask.getSubTaskStorageForEpic().getStorage();

        if(storage.isEmpty()){
            epicTask.setStartTime(Optional.empty());
            return;
        }
        epicTask.setStartTime(findMinStartTime(epicTask.getSubTaskStorageForEpic()));
    }

    public void epicDurationUpdater(EpicTask epicTask){
        HashMap<Integer, Task> storage = epicTask.getSubTaskStorageForEpic().getStorage();

        if(storage.isEmpty()){
            epicTask.setDuration(Optional.empty());
            return;
        }
        epicTask.setDuration(sumDuration(epicTask.getSubTaskStorageForEpic()));
    }

    public Optional<ZonedDateTime> findMinStartTime(SubTaskStorage subTaskStorage){

        HashMap<Integer, Task> subTaskStorageFromEpic = subTaskStorage.getStorage();

        if(subTaskStorageFromEpic.isEmpty()){
            return Optional.empty();
        }

        Optional<ZonedDateTime> minStartTime = Optional.empty();

        for(Task curTask : subTaskStorageFromEpic.values()){
            if(minStartTime.isEmpty()){
                minStartTime = curTask.getStartTime();
                continue;
            }
            if(minStartTime.get().isAfter(curTask.getStartTime().get())){
                minStartTime = curTask.getStartTime();
            }

        }
        return minStartTime;

    }

    public Optional<Duration> sumDuration(SubTaskStorage subTaskStorage){

        HashMap<Integer, Task> subTaskStorageFromEpic = subTaskStorage.getStorage();

        if(subTaskStorageFromEpic.isEmpty()){
            return Optional.empty();
        }

        Optional<Duration> sumDuration = Optional.empty();
        for(Task curTask : subTaskStorageFromEpic.values()){
            if(curTask.getDuration().isPresent()){
                if(sumDuration.isEmpty()){
                    sumDuration = curTask.getDuration();
                } else {
                    sumDuration = Optional.of(sumDuration.get()
                                                        .plusMinutes(curTask
                                                        .getDuration()
                                                        .get().toMinutes())
                                             );
                }
            }
        }
        return sumDuration;
    }
}
