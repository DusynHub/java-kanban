package kanban.util;

import kanban.module.Task;

public final class TaskIdGetter {

    public static int getTaskId(Task task){
        int idForTaskIfDoNotExist = -1;
        int taskId;

        if (task == null) {
            taskId = idForTaskIfDoNotExist;
        } else {
            taskId = task.getId();
        }
        return taskId;
    }
}