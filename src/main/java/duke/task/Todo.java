package duke.task;

/**
 * Represents a Todo task.
 */
public class Todo extends Task {
    public static final String TASK_TYPE = "T";

    public Todo(String description) {
        super(description, TASK_TYPE);
    }

    /**
     * Gets the Todo date and time info.
     *
     * @return An empty string since it is not applicable for Todo.
     */
    @Override
    public String getDateTime() {
        return "";
    }

}