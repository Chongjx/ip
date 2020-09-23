package duke.managers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Manages the date time info and format.
 */
public class DateTimeManager {

    /** List of acceptable date time formats */
    public static final String[] DATE_TIME_FORMATS = {
            // Time in 24hr format
            // Date format with - and time format with :
            "dd-M-yy HH:mm",
            "dd-MM-yy HH:mm",
            "dd-MMM-yy HH:mm",
            "dd-M-yyyy HH:mm",
            "dd-MM-yyyy HH:mm",
            "dd-MMM-yyyy HH:mm",

            // Date format with / and time format with :
            "dd/M/yy HH:mm",
            "dd/MM/yy HH:mm",
            "dd/MMM/yy HH:mm",
            "dd/M/yyyy HH:mm",
            "dd/MM/yyyy HH:mm",
            "dd/MMM/yyyy HH:mm",

            // Date format with space and time format with :
            "dd M yy HH:mm",
            "dd MM yy HH:mm",
            "dd MMM yy HH:mm",
            "dd M yyyy HH:mm",
            "dd MM yyyy HH:mm",
            "dd MMM yyyy HH:mm",

            // Date format with - and time format with .
            "dd-M-yy HH.mm",
            "dd-MM-yy HH.mm",
            "dd-MMM-yy HH.mm",
            "dd-M-yyyy HH.mm",
            "dd-MM-yyyy HH.mm",
            "dd-MMM-yyyy HH.mm",

            // Date format with / and time format wih .
            "dd/M/yy HH.mm",
            "dd/MM/yy HH.mm",
            "dd/MMM/yy HH.mm",
            "dd/M/yyyy HH.mm",
            "dd/MM/yyyy HH.mm",
            "dd/MMM/yyyy HH.mm",

            // Date format with space and time format wih .
            "dd M yy HH.mm",
            "dd MM yy HH.mm",
            "dd MMM yy HH.mm",
            "dd M yyyy HH.mm",
            "dd MM yyyy HH.mm",
            "dd MMM yyyy HH.mm",

            // Date format with - and time format with no symbol
            "dd-M-yy HHmm",
            "dd-MM-yy HHmm",
            "dd-MMM-yy HHmm",
            "dd-M-yyyy HHmm",
            "dd-MM-yyyy HHmm",
            "dd-MMM-yyyy HHmm",

            // Date format with / and time format with no symbol
            "dd/M/yy HHmm",
            "dd/MM/yy HHmm",
            "dd/MMM/yy HHmm",
            "dd/M/yyyy HHmm",
            "dd/MM/yyyy HHmm",
            "dd/MMM/yyyy HHmm",

            // Date format with space and time format with no symbol
            "dd M yy HHmm",
            "dd MM yy HHmm",
            "dd MMM yy HHmm",
            "dd M yyyy HHmm",
            "dd MM yyyy HHmm",
            "dd MMM yyyy HHmm",

            // Time in 12hr format
            // Date format with - and time format with :
            "dd-M-yy hh:mma",
            "dd-MM-yy hh:mm",
            "dd-MMM-yy hh:mma",
            "dd-M-yyyy hh:mma",
            "dd-MM-yyyy hh:mma",
            "dd-MMM-yyyy hh:mma",

            // Date format with / and time format with :
            "dd/M/yy hh:mma",
            "dd/MM/yy hh:mma",
            "dd/MMM/yy hh:mma",
            "dd/M/yyyy hh:mma",
            "dd/MM/yyyy hh:mma",
            "dd/MMM/yyyy hh:mma",

            // Date format with space and time format wih :
            "dd M yy hh:mma",
            "dd MM yy hh:mma",
            "dd MMM yy hh:mma",
            "dd M yyyy hh:mma",
            "dd MM yyyy hh:mma",
            "dd MMM yyyy hh:mma",

            // Date format with - and time format with .
            "dd-M-yy hh.mma",
            "dd-MM-yy hh.mma",
            "dd-MMM-yy hh.mma",
            "dd-M-yyyy hh.mma",
            "dd-MM-yyyy hh.mma",
            "dd-MMM-yyyy hh.mma",

            // Date format with / and time format wih .
            "dd/M/yy hh.mma",
            "dd/MM/yy hh.mma",
            "dd/MMM/yy hh.mma",
            "dd/M/yyyy hh.mma",
            "dd/MM/yyyy hh.mma",
            "dd/MMM/yyyy hh.mma",

            // Date format with space and time format with .
            "dd M yy hh.mma",
            "dd MM yy hh.mma",
            "dd MMM yy hh.mma",
            "dd M yyyy hh.mma",
            "dd MM yyyy hh.mma",
            "dd MMM yyyy hh.mma",
    };

    /** List of acceptable date formats */
    public static final String[] DATE_FORMATS = {
            "dd/M/yy",
            "dd/MM/yy",
            "dd/MMM/yy",
            "dd/M/yyyy",
            "dd/MM/yyyy",
            "dd/MMM/yyyy",

            "dd-M-yy",
            "dd-MM-yy",
            "dd-MMM-yy",
            "dd-M-yyyy",
            "dd-MM-yyyy",
            "dd-MMM-yyyy",

            "dd M yy",
            "dd MM yy",
            "dd MMM yy",
            "dd M yyyy",
            "dd MM yyyy",
            "dd MMM yyyy",
    };

    /** Fix format for printing */
    public static final DateTimeFormatter DISPLAY_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mma");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("hh.mma");
    private static final DateTimeFormatter DAY_FORMAT = DateTimeFormatter.ofPattern("EEE");

    /** An instance of current DateTime */
    private static final LocalDateTime now = LocalDateTime.now();

    /**
     * Returns today's date in the format: dd/MM/yyyy.
     *
     * @return Today's date.
     */
    public static String getDate() {
        return now.format(DATE_FORMAT);
    }

    /**
     * Returns current local time in the format: HH.mm.
     *
     * @return Current local time.
     */
    public static String getTime() {
        return now.format(TIME_FORMAT);
    }

    /**
     * Returns day of the week in the format of EEE
     * i.e. For Monday it will return MON.
     *
     * @return Day of the week.
     */
    public static String getDay() {
        return now.format(DAY_FORMAT);
    }
}