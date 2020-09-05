public class DukeException extends Exception {
    public enum ExceptionType {
        EXCEPTION_MISSING_DESCRIPTION("You missed out on the description?! O.o"),
        EXCEPTION_MISSING_IDENTIFIER("You missed out key identifier! (/by /at)"),
        EXCEPTION_MISSING_DATETIME("You did not set a date/time ¯\\_(ツ)_/¯");

        private String exceptionMessage;

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
