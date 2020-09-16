package duke.task;

import duke.util.Formatter;

public class Event extends Task {
    public static final String IDENTIFIER = "/at";

    String dateTime;

    public Event(String description, String dateTime) {
        super(description, TASK_TYPE_EVENT);
        setDateTime(dateTime);
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
