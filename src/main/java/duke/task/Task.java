package duke.task;

import duke.util.Formatter;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task() {
        this.description = "";
        this.isDone = false;
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
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

    @Override
    public String toString() {
        String message = "[";
        if (isDone) {
            message += Formatter.tick;
        } else {
            message += Formatter.cross;
        }
        message += "] " + description;
        return message;
    }
}