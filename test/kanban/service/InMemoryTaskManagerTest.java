package kanban.service;

import org.junit.jupiter.api.BeforeEach;


class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @BeforeEach
    public void beforeEachForInMemoryTaskManagerTest(){
        taskManager = new InMemoryTaskManager();
    }
}