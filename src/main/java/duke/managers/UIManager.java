package duke.managers;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Text UI of the application
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

    private String[] splitMessages;

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
    public TaskManager.CommandType getUserInput() {
        String fullInputMessage = in.nextLine();
        splitMessages = fullInputMessage.split(" ", 2);
        String command = splitMessages[0];

        TaskManager.CommandType userCommand;

        switch (command.toUpperCase()) {
        case TaskManager.COMMAND_STRING_LIST:
            userCommand = TaskManager.CommandType.COMMAND_LIST;
            break;
        case TaskManager.COMMAND_STRING_DONE:
            userCommand = TaskManager.CommandType.COMMAND_MARK_DONE;
            break;
        case TaskManager.COMMAND_STRING_BYE:
            userCommand = TaskManager.CommandType.COMMAND_EXIT;
            break;
        case TaskManager.COMMAND_STRING_TODO:
        case TaskManager.COMMAND_STRING_EVENT:
        case TaskManager.COMMAND_STRING_DEADLINE:
            userCommand = TaskManager.CommandType.COMMAND_ADD;
            break;
        case TaskManager.COMMAND_STRING_DELETE:
            userCommand = TaskManager.CommandType.COMMAND_DELETE;
            break;
        case TaskManager.COMMAND_STRING_EMPTY:
            userCommand = TaskManager.CommandType.COMMAND_MISSING;
            break;
        default:
            userCommand = TaskManager.CommandType.COMMAND_UNRECOGNIZED;
            break;
        }
        return userCommand;
    }

    /**
     * Returns the split messages
     *
     * @return split messages
     */
    public String[] getSplitMessages () {
        return splitMessages;
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
    public void printDefaultMessage(TaskManager.CommandType command) {
        String replyMessage;
        switch (command) {
        case COMMAND_INIT:
            replyMessage = INDENT_ONE_TAB + "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " +
                    DateTimeManager.getDay() + ". The time now is " + DateTimeManager.getTime() + "." + LS +
                    INDENT_ONE_TAB + "What can I do for you?";
            break;
        case COMMAND_EXIT:
            replyMessage = INDENT_ONE_TAB + "Bye! Hope to see you again soon!";
            break;
        case COMMAND_MISSING:
            replyMessage = INDENT_ONE_TAB + "You did not enter anything, did you?";
            break;
        case COMMAND_UNRECOGNIZED:
            replyMessage = INDENT_ONE_TAB + "Sorry I don't know what that means... >.<";
            break;
        default:
            replyMessage = "";
        }
        prints(replyMessage);
    }

    /**
     * Returns the message enclosed with the selected bracket.
     *
     * @param message Original string of the message.
     * @param bracket Type of bracket to enclose the message.
     * @return Enclosed message.
     */
    public static String encloseWithBrackets(String message, bracketType bracket) {
        String output;
        switch (bracket){
        case ROUND_BRACKET:
            output = "(" + message +")";
            break;
        case CURLY_BRACKET:
            output = "{" + message +"}";
            break;
        case SQUARE_BRACKET:
        default:
            output = "[" + message +"]";
        }
        return output;
    }
}
