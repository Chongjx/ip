package duke.task;

import duke.managers.UIManager;

/**
 * Represents a Deadline task.
 */
public class Deadline extends Task {
    public static final String TASK_TYPE = "D";
    public static final String IDENTIFIER = "/by";

    /** The date and time info that it is due by */
    String dateTime;

    public Deadline(String description, String dateTime) {
        super(description, TASK_TYPE);
        setDateTime(dateTime);
    }

    /**
     * Sets the Deadline date and time info.
     *
     * @param dateTime Deadline date and time info.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the Deadline date and time info.
     *
     * @return Deadline date and time info.
     */
    @Override
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Overrides the parent class toString method and returns additional Deadline date and time info.
     *
     * @return parent toString and Deadline date and time info.
     */
    @Override
    public String toString() {
        return super.toString() + UIManager.encloseWithBrackets("By:" + dateTime, UIManager.bracketType.ROUND_BRACKET);
    }
}
