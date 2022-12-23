package kanban.service;

import kanban.module.*;
import kanban.server.HttpTaskServer;
import kanban.server.KVServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;


class HttpTaskManagerTest  extends TaskManagerTest<HttpTaskManager> {

    public KVServer kVServer;
    static final ZoneId zone = ZoneId.of("Europe/Moscow");
    private HttpTaskServer taskServer;

    @BeforeAll
    public static void beforeAll(){
        rt0 = new RegularTask(
                0
                , "0 RegularTask"
                , "RegularTask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 8, 22, 0)
                , zone))
        );
        rt1 = new RegularTask(
                1
                , "1 RegularTask"
                , "RegularTask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(120))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 9, 9, 0)
                , zone))
        );
        rt2 = new RegularTask(
                2
                , "2 RegularTask"
                , "RegularTask with id 2"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(240))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 10, 7, 0)
                , zone))
        );

        rt3 = new RegularTask(
                3
                , "3 RegularTask"
                , "RegularTask with id 3"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(90))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 18, 11, 0)
                , zone))
        );

        rt4 = new RegularTask(
                4
                , "4 RegularTask"
                , "RegularTask with id 4"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(120))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 19, 13, 0)
                , zone))
        );

        rt5 = new RegularTask(
                5
                , "5 RegularTask"
                , "RegularTask with id 5"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(30))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 20, 8, 0)
                , zone))
        );

        rt6 = new RegularTask(
                6
                , "6 RegularTask"
                , "RegularTask with id 6"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(45))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 21, 15, 0)
                , zone))
        );

        rt7 = new RegularTask(
                7
                , "7 RegularTask"
                , "RegularTask with id 7"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(240))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 22, 16, 0)
                , zone))
        );

        rt8 = new RegularTask(
                8
                , "8 RegularTask"
                , "RegularTask with id 8"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(45))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 11, 23, 8, 0)
                , zone))
        );

        rt01 = new RegularTask(
                11
                , "0 RegularTask"
                , "RegularTask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 8, 22, 1)
                , zone))
        );

        rtAnyId = new RegularTask(
                0
                , "0 RegularTask"
                , "RegularTask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
        );


        et0 = new EpicTask(
                0
                , "0 epic"
                , "epic with id 0"
                , TaskType.EPIC_TASK
        );

        et1 = new EpicTask(
                1
                , "1 epic"
                , "epic with id 1"
                , TaskType.EPIC_TASK
        );

        et2 = new EpicTask(
                2
                , "2 epic"
                , "epic with id 2"
                , TaskType.EPIC_TASK
        );

        et3 = new EpicTask(
                3
                , "3 epic"
                , "epic with id 3"
                , TaskType.EPIC_TASK
        );

        et4 = new EpicTask(
                4
                , "4 epic"
                , "epic with id 4"
                , TaskType.EPIC_TASK
        );
        st1 = new SubTask(
                1
                , "1 subtask"
                , "subtask with id 1"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 11, 9, 0)
                , zone))
                , 0
        );
        st2 = new SubTask(
                2
                , "2 subtask"
                , "subtask with id 2"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 25, 9, 0)
                , zone))
                , 0
        );
        st3 = new SubTask(
                3
                , "3 subtask"
                , "subtask with id 3"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 11, 12, 0)
                , zone))
                , 2
        );
        st5 = new SubTask(
                5
                , "5 subtask"
                , "subtask with id 5"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 13, 13, 0)
                , zone))
                , 4
        );

        st02SameStartTimeAsRt0 = new SubTask(
                2
                , "2 subtask"
                , "subtask with id 2"
                , StatusName.IN_PROGRESS
                , TaskType.SUBTASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 8, 21, 59)
                , zone))
                , 1
        );
    }

    @BeforeEach
    void init() throws IOException {
        kVServer = new KVServer();
        kVServer.start();
        taskServer = new HttpTaskServer();
        taskServer.start();
        taskManager = (HttpTaskManager) taskServer.getTaskManager();
    }

    @AfterEach
    void stopServer() {
        taskServer.stop();
        kVServer.stop();
    }
}