package kanban.util;

import kanban.module.Task;

import java.util.TreeSet;

public final class StartDateValidator {
    public static Boolean validateStartDate(Task task, TreeSet<Task> prioritized){
        boolean isStartDateFree = true;

        if(prioritized.isEmpty()){
            return true;
        }
        if(task.getStartTime().isEmpty()
                || task.getDuration().isEmpty()
                || task.getEndTime().isEmpty()){
            return isStartDateFree;
        }

        for(Task priorTask : prioritized){
            if(priorTask.getStartTime().isPresent() && priorTask.getEndTime().isPresent()){
                Boolean isTaskBeforePriorTask
                        = task.getEndTime().get().isBefore(priorTask.getStartTime().get());

                Boolean isTaskAfterPriorTask
                        = task.getStartTime().get().isAfter(priorTask.getEndTime().get());

                if( !(isTaskBeforePriorTask || isTaskAfterPriorTask) ){
                    isStartDateFree = false;
                }
            }
        }
        return isStartDateFree;
    }
}
