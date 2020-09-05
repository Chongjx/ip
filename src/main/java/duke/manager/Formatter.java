package duke.manager;

public class Formatter {
    // Formatting
    public final static String formatDashes = "----------------------------------------------------------------------";
    public final static String formatTwoTabs = "    ";
    public final static String formatFourTabs = "        ";// Characters for tick and cross
    public final static char tick = '\u2713';
    public final static char cross = '\u2718';

    // Print message with formatting
    public static void reply(String message) {
        System.out.println(message + System.lineSeparator() + formatDashes);
    }
}