package duke.task;

import duke.managers.UIManager;

/**
 * Abstract Task class that contains the basic info of a task.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected String taskType;

    public static final char TICK = '\u2713';
    public static final char CROSS = '\u2718';

    public Task(String description, String taskType) {
        setDescription(description);
        setTaskType(taskType);
        setIsDone(false);
    }

    /**
     * Gets the task description.
     *
     * @return Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the task description.
     *
     * @param description Task description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the isDone status of the task.
     *
     * @return isDone status of the task.
     */
    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Sets the isDone status of the task.
     *
     * @param isDone isDone status of the task.
     */
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Gets the task type. Todo, Event or Deadline.
     *
     * @return Task type.
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * Sets the task type. Todo, Event or Deadline.
     *
     * @param taskType Task type.
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * Gets the date and time info of the task. Only valid for Event and Deadline task.
     *
     * @return Date and time info of the task.
     */
    public abstract String getDateTime();

    /**
     * Overrides the parent class toString method and returns the task type, isDone status and description with formatting.
     *
     * @return Task type, isDone status and description.
     */
    @Override
    public String toString() {
        String message = UIManager.encloseWithBrackets(taskType, UIManager.bracketType.SQUARE_BRACKET);

        if (isDone) {
            message = message.concat(UIManager.encloseWithBrackets(String.valueOf(TICK),
                    UIManager.bracketType.SQUARE_BRACKET));
        } else {
            message = message.concat(UIManager.encloseWithBrackets(String.valueOf(CROSS),
                    UIManager.bracketType.SQUARE_BRACKET));
        }
        message = message.concat(" " + description);
        return message;
    }
}