package duke.task;

public class Todo extends Task {
    public static final String TASK_TYPE = "T";

    public Todo(String description) {
        super(description, TASK_TYPE);
    }

    @Override
    public String getDateTime() {
        return "";
    }
}