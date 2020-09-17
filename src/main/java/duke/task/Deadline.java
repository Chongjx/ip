package duke.task;

import duke.util.Formatter;

/**
 * Deadline class which inherits from Task class.
 */
public class Deadline extends Task {
    /** Static final variables for Deadline task type and identifier */
    public static final String TASK_TYPE = "D";
    public static final String IDENTIFIER = "/by";

    /** The date and time info that it is due by */
    String dateTime;

    /**
     * Constructor of a Deadline.
     *
     * @param description Deadline description.
     * @param dateTime Deadline date and time info.
     */
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
        return super.toString() + Formatter.encloseWithBrackets("By:" + dateTime, Formatter.bracketType.ROUND_BRACKET);
    }
}
