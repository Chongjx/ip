package duke.task;

/**
 * Todo class which inherits from Task class.
 */
public class Todo extends Task {
    /** Static final variable for Todo task type */
    public static final String TASK_TYPE = "T";

    /**
     * Constructor of a Todo.
     *
     * @param description Todo description.
     */
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