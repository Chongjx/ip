package duke.util;

public class DukeException extends Exception {
    public enum ExceptionType {
        EXCEPTION_MISSING_DESCRIPTION("You missed out on the description?! O.o"),
        EXCEPTION_MISSING_IDENTIFIER("You missed out key identifier! (/by /at)"),
        EXCEPTION_MISSING_DATETIME("You did not set a date/time ¯\\_(ツ)_/¯"),
        EXCEPTION_INVALID_INPUT("Invalid input, cannot convert to integer!"),
        EXCEPTION_ARRAY_INDEX_OUT_OF_BOUNDS("You did not enter any value!"),
        EXCEPTION_INDEX_OUT_OF_BOUNDS("Ops, you have entered an invalid task number!");

        private final String exceptionMessage;

        ExceptionType(String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
        }

        @Override
        public String toString() {
            return exceptionMessage;
        }
    }

    public DukeException(ExceptionType exception) {
        super(exception.toString());
    }
}
