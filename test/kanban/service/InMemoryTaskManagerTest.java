package kanban.service;

import kanban.module.RegularTask;
import kanban.module.StatusName;
import kanban.module.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @BeforeEach
    public void beforeEachForInMemoryTaskManagerTest(){
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void shouldCreate4RegularTasksInRegularTaskStorage() {

        RegularTask rt00 = new RegularTask(
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

        RegularTask rt11 = new RegularTask(
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
        RegularTask rt22 = new RegularTask(
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

        RegularTask rt33 = new RegularTask(
                3
                , "3 RegularTask"
                , "RegularTask with id 3"
                , StatusName.NEW
                , TaskType.REGULAR_TASK
                , Optional.empty()
                , Optional.empty()
        );


        taskManager.createRegularTask(rt00);
        taskManager.createRegularTask(rt11);
        taskManager.createRegularTask(rt22);
        taskManager.createRegularTask(rt33);

        System.out.println(taskManager.getPrioritizedTasks());

    }

}