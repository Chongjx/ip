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

    public UIManager() {
        this(System.in, System.out);
    }

    public UIManager(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     *
     * @return
     */
    public String getUserInput() {
        String fullInputMessage = in.nextLine();
        return fullInputMessage;
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
