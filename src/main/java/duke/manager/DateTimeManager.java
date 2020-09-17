package duke.manager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeManager to retrieve current date and time info.
 */
public class DateTimeManager {
    /** An instance of current DateTime */
    private static final LocalDateTime now = LocalDateTime.now();

    /**
     * Returns today's date in the format: dd/MM/yyyy.
     *
     * @return Today's date.
     */
    public static String getDate() {
        return now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Returns current local time in the format: HH.mm.
     *
     * @return Current local time.
     */
    public static String getTime() {
        return now.format(DateTimeFormatter.ofPattern("HH.mm"));
    }

    /**
     * Returns day of the week in the format of EEE
     * i.e. For Monday it will return MON.
     *
     * @return Day of the week.
     */
    public static String getDay() {
        return now.format(DateTimeFormatter.ofPattern("EEE"));
    }
}