package duke.managers;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Text UI of the application.
 */
public class UIManager {
    /** Offset for displaying 1st level of message. */
    public static final String INDENT_ONE_TAB = "    ";
    /** Offset for displaying 2nd level of message. */
    public static final String INDENT_TWO_TABS = "        ";
    public static final String DIVIDER = "===========================================================================";
    /** A platform independent line separator. */
    public static final String LS = System.lineSeparator();

    private final Scanner in;
    private final PrintStream out;

    private String[] taskDetails;

    public UIManager() {
        this(System.in, System.out);
    }

    public UIManager(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Returns the command based on user input.
     *
     * @return user command.
     */
    public String getUserCommandInput() {
        String fullInputMessage = in.nextLine();
        taskDetails = fullInputMessage.split(" ", 2);

        return taskDetails[0];
    }

    /**
     * Returns the task details.
     *
     * @return task details.
     */
    public String[] getTaskDetails() {
        return taskDetails;
    }

    /**
     * Prints out a message with a standard format.
     *
     * @param message Message to be printed out.
     */
    public void prints(String message) {
        out.println(message + LS + DIVIDER);
    }

    /**
     * Types of bracket.
     */
    public enum bracketType {
        ROUND_BRACKET,
        SQUARE_BRACKET,
        CURLY_BRACKET,
    }

    /**
    * Returns the default message of respective commands.
    *
    * @param command Type of command.
     */
    public void printsDefaultMessage(TaskManager.CommandType command) {
        String defaultMessage;
        switch (command) {
        case COMMAND_INIT:
            defaultMessage = INDENT_ONE_TAB + "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " +
                    DateTimeManager.getDay() + ". The time now is " + DateTimeManager.getTime() + "." + LS +
                    INDENT_ONE_TAB + "What can I do for you?";
            break;
        case COMMAND_EXIT:
            defaultMessage = INDENT_ONE_TAB + "Bye! Hope to see you again soon!";
            break;
        case COMMAND_MISSING:
            defaultMessage = INDENT_ONE_TAB + "You did not enter anything, did you?";
            break;
        case COMMAND_UNRECOGNIZED:
            defaultMessage = INDENT_ONE_TAB + "Sorry I don't know what that means... >.<";
            break;
        default:
            defaultMessage = "";
        }
        prints(defaultMessage);
    }

    /**
     * Returns the message enclosed with the selected bracket.
     *
     * @param message Original string of the message.
     * @param bracket Type of bracket to enclose the message.
     * @return Enclosed message.
     */
    public static String encloseWithBrackets(String message, bracketType bracket) {
        String processedMessage;
        switch (bracket){
        case ROUND_BRACKET:
            processedMessage = "(" + message +")";
            break;
        case CURLY_BRACKET:
            processedMessage = "{" + message +"}";
            break;
        case SQUARE_BRACKET:
        default:
            processedMessage = "[" + message +"]";
        }
        return processedMessage;
    }
}
