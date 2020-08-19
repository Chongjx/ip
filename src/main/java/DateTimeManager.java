import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeManager {
    static LocalDateTime current = LocalDateTime.now();
    
    public static String getDate() {
        return current.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String getTime() {
        return current.format(DateTimeFormatter.ofPattern("HH.mm"));
    }

    public static String getDay() {
        return current.format(DateTimeFormatter.ofPattern("EEE"));
    }
}