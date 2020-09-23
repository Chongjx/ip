package duke.util;

import duke.managers.DateTimeManager;
import duke.managers.TaskManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

/**
 * Parses user input.
 */
public class Parser {
    /**
     * String value of all the command types.
     */
    private static final String COMMAND_STRING_TODO = "TODO";
    private static final String COMMAND_STRING_EVENT = "EVENT";
    private static final String COMMAND_STRING_DEADLINE = "DEADLINE";
    private static final String COMMAND_STRING_LIST = "LIST";
    private static final String COMMAND_STRING_ON = "ON";
    private static final String COMMAND_STRING_DONE = "DONE";
    private static final String COMMAND_STRING_DELETE = "DELETE";
    private static final String COMMAND_STRING_EMPTY = "";
    private static final String COMMAND_STRING_BYE = "BYE";

    /**
     * Parsers user input into command for execution.
     *
     * @param userCommandInput user input string
     * @return the command based on the user input
     */
    public TaskManager.CommandType parseCommand(String userCommandInput) {
        TaskManager.CommandType userCommand;

        switch (userCommandInput.toUpperCase()) {
        case COMMAND_STRING_TODO:
            userCommand = TaskManager.CommandType.COMMAND_ADD_TODO;
            break;
        case COMMAND_STRING_EVENT:
            userCommand = TaskManager.CommandType.COMMAND_ADD_EVENT;
            break;
        case COMMAND_STRING_DEADLINE:
            userCommand = TaskManager.CommandType.COMMAND_ADD_DEADLINE;
            break;
        case COMMAND_STRING_LIST:
            userCommand = TaskManager.CommandType.COMMAND_LIST;
            break;
        case COMMAND_STRING_ON:
            userCommand = TaskManager.CommandType.COMMAND_ON;
            break;
        case COMMAND_STRING_DONE:
            userCommand = TaskManager.CommandType.COMMAND_MARK_DONE;
            break;
        case COMMAND_STRING_BYE:
            userCommand = TaskManager.CommandType.COMMAND_EXIT;
            break;
        case COMMAND_STRING_DELETE:
            userCommand = TaskManager.CommandType.COMMAND_DELETE;
            break;
        case COMMAND_STRING_EMPTY:
            userCommand = TaskManager.CommandType.COMMAND_MISSING;
            break;
        default:
            userCommand = TaskManager.CommandType.COMMAND_UNRECOGNIZED;
            break;
        }
        return userCommand;
    }

    /**
     * Parsers string info into description and date time info for adding of task.
     * Ensures that description and the required keyword are present.
     *
     * @param message The string of the original message, to be processed for Event and Deadline tasks.
     * @param identifier Used to split the original message to obtain the data time info for Event and Deadline tasks.
     * @return The description and date and time info, in index 0 and index 1 respectively, for Event and Deadline task.
     * @throws DukeException Missing description, identifier or date and time info.
     */
    public String[] parseTaskInfo(String[] message, String identifier) throws DukeException {
        String[] descriptionAndDateTimeInfo = new String[2];
        String description;
        String dateTimeInfo;
        try {
            description = message[1].trim();
            // if the message is empty or blank, throw missing description exception
            if (description.isEmpty() || description.isBlank()) {
                throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
            } else if (!identifier.isEmpty()) {
                // split message by identifier, if specified. Throw missing identifier if the string does not contain
                // the identifier
                if (!description.contains(identifier)) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_IDENTIFIER);
                }
                // split message based on identifier
                descriptionAndDateTimeInfo = message[1].split(identifier, 2);

                descriptionAndDateTimeInfo[0] = descriptionAndDateTimeInfo[0].trim();
                descriptionAndDateTimeInfo[1] = descriptionAndDateTimeInfo[1].trim();

                description = descriptionAndDateTimeInfo[0];
                dateTimeInfo = descriptionAndDateTimeInfo[1];
                // if missing description or datetime, throw respective exception
                if (description.isEmpty()) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
                } else if (dateTimeInfo.isEmpty() || dateTimeInfo.isBlank()) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DATETIME);
                }
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            // extra handling in the the event that the message is empty and cannot be split
            throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
        }
        return descriptionAndDateTimeInfo;
    }

    /**
     * Parses string info into a LocalDateTime object.
     *
     * @param dateTimeInfo The string of the original message, to be processed for Event and Deadline tasks.
     * @return The LocalDateTime object.
     * @throws DukeException Invalid date time format entered.
     */
    public LocalDateTime parseDateTime(String dateTimeInfo) throws DukeException {
        // Verify against all time format
        for (String dt : DateTimeManager.DATE_TIME_FORMATS) {
            try {
                DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
                builder.parseCaseInsensitive();
                builder.appendPattern(dt);

                DateTimeFormatter format = builder.toFormatter();
                return LocalDateTime.parse(dateTimeInfo, format);
            } catch (DateTimeParseException e) {
                // continue the loop
            }
        }
        throw new DukeException(DukeException.ExceptionType.EXCEPTION_UNRECOGNIZED_DATE_TIME_FORMAT);
    }

    /**
     * Parses string info into a LocalDate object.
     *
     * @param dateInfo The string of the original message.
     * @return The LocalDate object.
     * @throws DukeException Invalid date format entered.
     */
    public LocalDate parseDate(String[] dateInfo) throws DukeException {
        for (String d : DateTimeManager.DATE_FORMATS) {
            try {
                if (dateInfo[1].isBlank() || dateInfo[1].isEmpty()) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DATETIME);
                }

                DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
                builder.parseCaseInsensitive();
                builder.appendPattern(d);

                DateTimeFormatter format = builder.toFormatter();
                return LocalDate.parse(dateInfo[1], format);
            } catch (ArrayIndexOutOfBoundsException exception) {
                throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DATETIME);
            } catch (DateTimeParseException exception) {
                // continue the loop
            }
        }
        throw new DukeException(DukeException.ExceptionType.EXCEPTION_UNRECOGNIZED_DATE_FORMAT);
    }

    /**
     * Parses string into the task index.
     *
     * @param taskIndexString The whole string of message. To be converted to int.
     * @param taskListSize Size of the task list. To validate the index value.
     * @return Task index retrieved from the message.
     * @throws DukeException Index out of bounds, invalid input and array index out of bound.
     */
    public int parseTaskIndex(String[] taskIndexString, int taskListSize) throws DukeException {
        int taskIndex;
        // Converts the string to int
        try {
            if (taskIndexString[1].isBlank()) {
                throw new DukeException(DukeException.ExceptionType.EXCEPTION_ARRAY_INDEX_OUT_OF_BOUNDS);
            }
            taskIndex = Integer.parseInt(taskIndexString[1]) - 1;
            if (taskIndex < 0 || taskIndex > taskListSize) {
                throw new DukeException(DukeException.ExceptionType.EXCEPTION_INDEX_OUT_OF_BOUNDS);
            }
        } catch (NumberFormatException exception) {
            throw new DukeException(DukeException.ExceptionType.EXCEPTION_INVALID_INPUT);
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new DukeException(DukeException.ExceptionType.EXCEPTION_ARRAY_INDEX_OUT_OF_BOUNDS);
        }
        return taskIndex;
    }
}
