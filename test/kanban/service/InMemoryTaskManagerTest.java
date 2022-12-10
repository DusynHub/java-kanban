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

}