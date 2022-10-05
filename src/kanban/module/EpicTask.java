package kanban.module;

import kanban.service.storage.SubTaskStorage;

public class EpicTask extends Task{

    private SubTaskStorage subTaskStorageForEpic = new SubTaskStorage();
    public EpicTask(String name, String description, int id, int statusId) {
        super(name, description, id, 1);
    }
    public EpicTask(EpicTask task) {
        super();
        super.setName(task.getName());
        super.setDescription(task.getDescription());
        super.setId(task.getId());
        super.setStatus(STATUS_NAME_STORAGE.get(1));
    }



    public SubTaskStorage getSubTaskStorageForEpic() {
        return subTaskStorageForEpic;
    }

    public void setSubTaskStorageForEpic(SubTaskStorage subTaskStorageForEpic) {
        this.subTaskStorageForEpic = subTaskStorageForEpic;
    }
}
