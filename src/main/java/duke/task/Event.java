package duke.task;

import duke.util.Formatter;

public class Event extends Task {
    String dateTime;

    public Event(String description, String dateTime) {
        super(description);
        this.dateTime = dateTime;
        this.taskType = "E";
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return super.toString() + Formatter.encloseWithBrackets("At:" + dateTime, Formatter.bracketTypes.ROUND_BRACKET);
    }
}
