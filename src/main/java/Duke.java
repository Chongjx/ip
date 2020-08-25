import java.util.Arrays;
import java.util.Scanner;

public class Duke {
    // Formatting
    private static String dashOutline = "----------------------------------------------------------------------";
    private static String indentation = "       ";
    private static String message = "";
    
    public static void reply(String msg) {
        System.out.println(msg + dashOutline);
    }

    enum COMMAND {
        INIT,
        ADD,
        LIST,
        DONE,
        EXIT,
    }

    private static boolean isExit = false;

    public static void main(String[] args) {
        String userInput;
        Scanner in = new Scanner(System.in);

        COMMAND userCommand = COMMAND.INIT;

        // Welcome message
        message = "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " + DateTimeManager.getDay() + ". The " +
                "time now is " + DateTimeManager.getTime() + ".\nWhat can I do for you?\n";
        reply(message);

        do {
            message = in.nextLine();
            reply("");
            
            if (message.equalsIgnoreCase("BYE")) {
                isExit = true;
                break;
            } else {
                reply(indentation + message + "\n");
            }
        }
        while (!isExit);

        message = "Bye! Hope to see you again soon!\n";
        reply(message);
    }
}
