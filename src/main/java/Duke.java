import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Duke {
    public static String getDate() {
        LocalDate current = LocalDate.now();
        return current.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String getTime() {
        LocalTime current = LocalTime.now();
        return current.format(DateTimeFormatter.ofPattern("HH.mm"));
    }

    public static String getDay() {
        LocalDate current = LocalDate.now();
        return current.format(DateTimeFormatter.ofPattern("EEE"));
    }

    public static void main(String[] args) {
        /*String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);*/
        // Formatting
        String dashOutline = "--------------------------------------------------";

        // Welcome message
        System.out.println(
                dashOutline + "\nHello! I'm Jay. Today is " + getDate() + ", " + getDay() + ". The time now is " + getTime() +
                "\nWhat can I do for you?\n" + dashOutline);

        // Good bye message
        System.out.println("Bye. Hope to see you again soon!\n" + dashOutline);
    }
}
