package duke.task;

import duke.managers.DateTimeManager;
import duke.managers.UIManager;

import java.time.LocalDateTime;

/**
 * Represents a Deadline task.
 */
public class Deadline extends Task {
    public static final String TASK_TYPE = "D";
    public static final String IDENTIFIER = "/by";

    /** The date and time info that it is due by. */
    private LocalDateTime dateTime;

    /**
     * Overloaded constructor for Deadline. Accepts date time as a LocalDateTime object.
     *
     * @param description Description of the task.
     * @param dateTime Date and time info of the task.
     */
    public Deadline(String description, LocalDateTime dateTime) {
        super(description, TASK_TYPE);
        setDateTime(dateTime);
    }

    /**
     * Sets the Deadline date and time info.
     *
     * @param dateTime Deadline date and time info in string format.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Gets the Deadline date and time info.
     *
     * @return Deadline date and time info in string format.
     */
    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Overrides the parent class toString method and returns additional Deadline date and time info.
     *
     * @return parent toString and Deadline date and time info.
     */
    @Override
    public String toString() {
        String message;
        message = super.toString() + " " + UIManager.encloseWithBrackets("By: " +
                        dateTime.format(DateTimeManager.DISPLAY_DATE_TIME_FORMAT), UIManager.bracketType.ROUND_BRACKET);
        return message;
    }
}
