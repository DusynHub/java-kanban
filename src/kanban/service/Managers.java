package kanban.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

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
        //gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }
}
