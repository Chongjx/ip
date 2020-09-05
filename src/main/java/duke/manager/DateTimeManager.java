package duke.manager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeManager {
    private static LocalDateTime now = LocalDateTime.now();
    
    public static String getDate() {
        return now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String getTime() {
        return now.format(DateTimeFormatter.ofPattern("HH.mm"));
    }

    public static String getDay() {
        return now.format(DateTimeFormatter.ofPattern("EEE"));
    }
}