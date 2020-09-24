package duke.task;

import duke.managers.DateTimeManager;
import duke.managers.UIManager;

import java.time.LocalDateTime;

/**
 * Represents an Event task.
 */
public class Event extends Task {
    public static final String TASK_TYPE = "E";
    public static final String IDENTIFIER = "/at";

    /** The date and time info that it is at. */
    private LocalDateTime dateTime;

    /**
     * Overloaded constructor for Event. Accepts date time as a LocalDateTime object.
     *
     * @param description Description of the Event.
     * @param dateTime Date and time info of the Event.
     */
    public Event(String description, LocalDateTime dateTime) {
        super(description, TASK_TYPE);
        setDateTime(dateTime);
    }

    /**
     * Sets the Event date and time info.
     *
     * @param dateTime Event date and time info.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the Event date and time info.
     *
     * @return Event date and time info.
     */
    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Overrides the parent class toString method and returns additional Event date and time info.
     *
     * @return parent toString and Event date and time info.
     */
    @Override
    public String toString() {
        return super.toString() + " " + UIManager.encloseWithBrackets("At: " +
                        dateTime.format(DateTimeManager.DISPLAY_DATE_TIME_FORMAT), UIManager.bracketType.ROUND_BRACKET);
    }
}
