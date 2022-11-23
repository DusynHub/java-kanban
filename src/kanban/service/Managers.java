package kanban.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Managers {

    public static TaskManager getDefault(){

        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory(){

        return new InMemoryHistoryManager();
    }
}
