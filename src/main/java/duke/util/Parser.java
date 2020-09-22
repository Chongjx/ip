package duke.util;

import duke.Duke;
import duke.managers.TaskManager;

/**
 * Parses user input.
 */
public class Parser {
    /**
     * Static final variables for the list of command string
     */
    public static final String COMMAND_STRING_TODO = "TODO";
    public static final String COMMAND_STRING_EVENT = "EVENT";
    public static final String COMMAND_STRING_DEADLINE = "DEADLINE";
    public static final String COMMAND_STRING_LIST = "LIST";
    public static final String COMMAND_STRING_DONE = "DONE";
    public static final String COMMAND_STRING_DELETE = "DELETE";
    public static final String COMMAND_STRING_EMPTY = "";
    public static final String COMMAND_STRING_BYE = "BYE";

    /**
     * Parsers user input into command for execution.
     *
     * @param userCommandInput user input string
     * @return the command based on the user input
     */
    public TaskManager.CommandType parseCommand(String userCommandInput) {
        TaskManager.CommandType userCommand;

        switch (userCommandInput.toUpperCase()) {
        case Parser.COMMAND_STRING_TODO:
            userCommand = TaskManager.CommandType.COMMAND_ADD_TODO;
            break;
        case Parser.COMMAND_STRING_EVENT:
            userCommand = TaskManager.CommandType.COMMAND_ADD_EVENT;
            break;
        case Parser.COMMAND_STRING_DEADLINE:
            userCommand = TaskManager.CommandType.COMMAND_ADD_DEADLINE;
            break;
        case Parser.COMMAND_STRING_LIST:
            userCommand = TaskManager.CommandType.COMMAND_LIST;
            break;
        case Parser.COMMAND_STRING_DONE:
            userCommand = TaskManager.CommandType.COMMAND_MARK_DONE;
            break;
        case Parser.COMMAND_STRING_BYE:
            userCommand = TaskManager.CommandType.COMMAND_EXIT;
            break;
        case Parser.COMMAND_STRING_DELETE:
            userCommand = TaskManager.CommandType.COMMAND_DELETE;
            break;
        case Parser.COMMAND_STRING_EMPTY:
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
     * Ensures that description and the required keyword are not empty are present.
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
            description = message[1];
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
