package duke.task;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
        this.taskType = "T";
    }

    @Override
    public String getDateTime() {
        return "";
    }
}