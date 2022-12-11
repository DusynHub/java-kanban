package kanban.util;

import kanban.module.*;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public final class CSVTaskFormat {
    private static final DateTimeFormatter formatter
            = DateTimeFormatter.ISO_DATE_TIME;

    public static RegularTask createRegularTaskFromCSV(String[] regularTasksFromCSV) {

        int id = Integer.parseInt(regularTasksFromCSV[0]);
        String name = regularTasksFromCSV[2];
        String description = regularTasksFromCSV[3];
        String status = regularTasksFromCSV[4];
        String type = regularTasksFromCSV[1];
        String stringDuration = regularTasksFromCSV[5];
        Optional<Duration> duration;
        String StringStartTime = regularTasksFromCSV[6];
        Optional<ZonedDateTime> startTime;
        if(stringDuration.equals("не задана")){
            duration = Optional.empty();
        } else{
            duration = Optional.of(Duration.ofMinutes(Integer.parseInt(stringDuration)));
        }

        if(StringStartTime.equals("не задано")){
            startTime = Optional.empty();
        } else{
            startTime = Optional.of(ZonedDateTime.parse(StringStartTime, formatter));
        }

        return new RegularTask(id
                , name
                , description
                , StatusName.valueOf(status)
                , TaskType.valueOf(type)
                , duration
                , startTime);
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
        String stringDuration = subTasksFromCSV[5];
        String StringStartTime = subTasksFromCSV[6];

        Optional<Duration> duration;
        Optional<ZonedDateTime> startTime;

        if(stringDuration.equals("не задана")){
            duration = Optional.empty();
        } else{
            duration = Optional.of(Duration.ofMinutes(Integer.parseInt(stringDuration)));
        }

        if(StringStartTime.equals("не задано")){
            startTime = Optional.empty();
        } else{
            startTime = Optional.of(ZonedDateTime.parse(StringStartTime, formatter));
        }



        int epicId = Integer.parseInt(subTasksFromCSV[7]);

        return new SubTask(id
                , name
                , description
                , StatusName.valueOf(status)
                , TaskType.valueOf(type)
                , duration
                , startTime
                , epicId);
    }
}
