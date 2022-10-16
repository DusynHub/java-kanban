package kanban.module;

public enum StatusNames {

    NEW("To Do"), // index = 0
    IN_PROGRESS("In Progress"), // index = 1
    DONE("Done") // index = 2
    ;

    private final String statusName;

    StatusNames(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    @Override
    public String toString() {
        return "StatusNames{" +
                "statusName='" + statusName + '\'' +
                '}';
    }
}
