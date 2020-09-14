package duke.task;

import duke.util.Formatter;

public class Deadline extends Task {
    String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.taskType = "D";
    }

    public void setDateTime(String by) {
        this.by = by;
    }

    public String getDateTime() {
        return by;
    }

    @Override
    public String toString() {
        return super.toString() + Formatter.encloseWithBrackets("By:" + by, Formatter.bracketTypes.ROUND_BRACKET);
    }
}
