package duke.task;

import duke.util.Formatter;

public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected String taskType;

    public Task(String description, String taskType) {
        setDescription(description);
        setTaskType(taskType);
        setIsDone(false);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public abstract String getDateTime();

    @Override
    public String toString() {
        String message = Formatter.encloseWithBrackets(taskType, Formatter.bracketTypes.SQUARE_BRACKET);

        if (isDone) {
            message += Formatter.encloseWithBrackets(String.valueOf(Formatter.TICK), Formatter.bracketTypes.SQUARE_BRACKET);
        } else {
            message += Formatter.encloseWithBrackets(String.valueOf(Formatter.CROSS),Formatter.bracketTypes.SQUARE_BRACKET);
        }
        message += " " + description;
        return message;
    }
}