package duke.util;

public class Formatter {
    // Formatting
    public static final String FORMAT_DASHES = "----------------------------------------------------------------------";
    public static final String INDENT_ONE_TAB = "    ";
    public static final String INDENT_TWO_TABS = "        ";
    // Characters for tick and cross
    public static final char TICK = '\u2713';
    public static final char CROSS = '\u2718';
    public static final char FILE_DELIMITER = '|';

    public enum bracketTypes {
        ROUND_BRACKET,
        SQUARE_BRACKET,
        CURLY_BRACKET,
    }

    public static String encloseWithBrackets(String message, bracketTypes bracket) {
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