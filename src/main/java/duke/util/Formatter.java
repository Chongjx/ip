package duke.util;

/**
 * Formatter class. Contains static final variables which are used to format the print out
 */
public class Formatter {
    /**
     * Static final variables for formatting.
     */
    public static final String FORMAT_DASHES = "----------------------------------------------------------------------";
    public static final String INDENT_ONE_TAB = "    ";
    public static final String INDENT_TWO_TABS = "        ";
    public static final char TICK = '\u2713';
    public static final char CROSS = '\u2718';
    public static final char FILE_DELIMITER = '|';

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