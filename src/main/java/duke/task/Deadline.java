package duke.task;

import duke.util.Formatter;

public class Deadline extends Task {
    public static final String IDENTIFIER = "/by";
    String dateTime;

    public Deadline(String description, String dateTime) {
        super(description, TASK_TYPE_DEADLINE);
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
        return super.toString() + Formatter.encloseWithBrackets("By:" + dateTime, Formatter.bracketTypes.ROUND_BRACKET);
    }
}
