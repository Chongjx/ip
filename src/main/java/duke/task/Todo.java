package duke.task;

public class Todo extends Task {
    public Todo(String description) {
        super(description, TASK_TYPE_TODO);
    }

    @Override
    public String getDateTime() {
        return "";
    }
}