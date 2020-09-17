package duke.task;

import duke.util.Formatter;

/**
 * Event class which inherits from Task class.
 */
public class Event extends Task {
    /** Static final variables for Event task type and identifier */
    public static final String TASK_TYPE = "E";
    public static final String IDENTIFIER = "/at";

    /** The date and time info that it is at */
    String dateTime;

    /**
     * Constructor of an Event.
     *
     * @param description Event description.
     * @param dateTime Event date and time info.
     */
    public Event(String description, String dateTime) {
        super(description, TASK_TYPE);
        setDateTime(dateTime);
    }

    /**
     * Sets the Event date and time info.
     *
     * @param dateTime Event date and time info.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the Event date and time info.
     *
     * @return Event date and time info.
     */
    @Override
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Overrides the parent class toString method and returns additional Event date and time info.
     *
     * @return parent toString and Event date and time info.
     */
    @Override
    public String toString() {
        return super.toString() + Formatter.encloseWithBrackets("At:" + dateTime, Formatter.bracketType.ROUND_BRACKET);
    }
}
