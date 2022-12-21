package kanban.service;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import kanban.module.EpicTask;
import kanban.module.RegularTask;
import kanban.module.SubTask;
import kanban.module.Task;

import java.lang.reflect.Type;

public class TaskDeserializerAdapter implements JsonDeserializer<Task> {
    @Override
    public Task deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String type = jsonElement.getAsJsonObject().get("type").getAsString();
        switch(type) {
            case "REGULAR_TASK": {
                return jsonDeserializationContext.deserialize(jsonElement, RegularTask.class);
            }
            case "EPIC_TASK": {
                return jsonDeserializationContext.deserialize(jsonElement, EpicTask.class);
            }
            case "SUBTASK": {
                return jsonDeserializationContext.deserialize(jsonElement, SubTask.class);
            }
            default:
                throw new IllegalArgumentException("Neither REGULAR_TASK, EPIC_TASK or SUBTASK");
        }
    }
}

