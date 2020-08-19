public class Duke {
    public static void main(String[] args) {
        // Formatting
        String dashOutline = "--------------------------------------------------";

        // Welcome message
        System.out.println(dashOutline + "\nHello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " + DateTimeManager.getDay() + ". The time now is " + DateTimeManager.getTime() + "\nWhat can I do for you?\n" + dashOutline);

        // Good bye message
        System.out.println("Bye. Hope to see you again soon!\n" + dashOutline);
    }
}
