package kanban.service;

import kanban.service.storage.RegularTaskStorage;

public class TaskRemover {
    public String removeAllRegularTasks(RegularTaskStorage regularTaskStorage){
        return regularTaskStorage.clearStorage();
    }
}
