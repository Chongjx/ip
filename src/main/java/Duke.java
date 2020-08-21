public class Duke {
    // Formatting
    private static String dashOutline = "----------------------------------------------------------------------";
    private static String message = "";
    
    public static void reply(String msg) {
        System.out.println(msg + dashOutline);
    }

    public static void main(String[] args) {
        reply(message);

        // Welcome message
        message = "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " + DateTimeManager.getDay() + ". The " +
                "time now is " + DateTimeManager.getTime() + "\nWhat can I do for you?\n";
        reply(message);

        message = "Bye. Hope to see you again soon!\n";
        reply(message);
    }
}
