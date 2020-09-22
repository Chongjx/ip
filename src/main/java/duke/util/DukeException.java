package duke.util;

/**
 * Signals the different type of possible exceptions.
 */
public class DukeException extends Exception {
    /**
     * Types of exception.
     */
    public enum ExceptionType {
        EXCEPTION_MISSING_DESCRIPTION("You missed out on the description?! O.o"),
        EXCEPTION_MISSING_IDENTIFIER("You missed out key identifier! (/by /at)"),
        EXCEPTION_MISSING_DATETIME("You did not set a date/time ¯\\_(ツ)_/¯"),
        EXCEPTION_INVALID_INPUT("Invalid input, cannot convert to integer!"),
        EXCEPTION_ARRAY_INDEX_OUT_OF_BOUNDS("You did not enter any value!"),
        EXCEPTION_INDEX_OUT_OF_BOUNDS("Ops, you have entered an invalid task number!");

        /**
         * The exception message.
         */
        private final String exceptionMessage;

        /**
         * Constructor of ExceptionType.
         *
         * @param exceptionMessage The exception message.
         */
        ExceptionType(String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
        }

        /**
         * Overrides the parent toString method and returns the exception message.
         *
         * @return The exception message.
         */
        @Override
        public String toString() {
            return exceptionMessage;
        }
    }

    /**
     * Constructor of a DukeException.
     *
     * @param exception Type of exception.
     */
    public DukeException(ExceptionType exception) {
        super(exception.toString());
    }
}
