package kanban.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kanban.module.Task;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Managers {

    public static TaskManager getDefault(){

        return new FileBackedTaskManager();
    }

    public static HistoryManager getDefaultHistory(){

        return new InMemoryHistoryManager();
    }

    public static Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter());
        gsonBuilder.registerTypeAdapter(Task.class, new TaskDeserializerAdapter());
        gsonBuilder.serializeNulls();
        return gsonBuilder.create();
    }
}
