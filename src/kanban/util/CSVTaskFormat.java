package kanban.util;

import kanban.module.*;

public final class CSVTaskFormat {

    public static RegularTask createRegularTaskFromCSV(String[] regularTasksFromCSV) {

        int id = Integer.parseInt(regularTasksFromCSV[0]);
        String name = regularTasksFromCSV[2];
        String description = regularTasksFromCSV[3];
        String status = regularTasksFromCSV[4];
        String type = regularTasksFromCSV[1];

        return new RegularTask(id
                , name
                , description
                , StatusName.valueOf(status)
                , TaskType.valueOf(type));
    }
    public static EpicTask createEpicTaskFromCSV(String[] epicTasksFromCSV) {

        int id = Integer.parseInt(epicTasksFromCSV[0]);
        String name = epicTasksFromCSV[2];
        String description = epicTasksFromCSV[3];
        String type = epicTasksFromCSV[1];

        return new EpicTask(id
                , name
                , description
                , TaskType.valueOf(type));
    }
    public static SubTask createSubTaskFromCSV(String[] subTasksFromCSV)
    {
        int id = Integer.parseInt(subTasksFromCSV[0]);
        String name = subTasksFromCSV[2];
        String description = subTasksFromCSV[3];
        String status = subTasksFromCSV[4];
        String type = subTasksFromCSV[1];
        int epicId = Integer.parseInt(subTasksFromCSV[5]);

        return new SubTask(id
                , name
                , description
                , StatusName.valueOf(status)
                , TaskType.valueOf(type)
                , epicId);
    }
}
