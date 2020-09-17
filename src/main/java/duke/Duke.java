package duke;

import duke.manager.DateTimeManager;
import duke.manager.IOManager;
import duke.manager.TaskManager;
import duke.util.Formatter;
import java.util.Scanner;

/**
 * Duke class to control the main application flow.
 */
public class Duke {
    /**
     * Prints out a message with a standard format.
     *
     * @param message Message to be printed out.
     */
    public static void reply(String message) {
        System.out.println(message + System.lineSeparator() + Formatter.FORMAT_DASHES);
    }

    /**
     * Types of command.
     */
    private enum CommandType {
        COMMAND_INIT,
        COMMAND_ADD,
        COMMAND_LIST,
        COMMAND_MARK_DONE,
        COMMAND_DELETE,
        COMMAND_EXIT,
        COMMAND_UNRECOGNIZED,
        COMMAND_MISSING,
    }

    /**
     * Static final variables for the list of command string
     */
    private static final String COMMAND_STRING_LIST = "LIST";
    private static final String COMMAND_STRING_DONE = "DONE";
    private static final String COMMAND_STRING_DELETE = "DELETE";
    private static final String COMMAND_STRING_BYE = "BYE";
    private static final String COMMAND_STRING_EMPTY = "";
    public static final String COMMAND_STRING_TODO = "TODO";
    public static final String COMMAND_STRING_EVENT = "EVENT";
    public static final String COMMAND_STRING_DEADLINE = "DEADLINE";

    /**
     * Scanner to handler user input.
     */
    private static final Scanner in = new Scanner(System.in);

    /**
     * Holds the type of user command.
     */
    private static CommandType userCommand = CommandType.COMMAND_INIT;

    /**
     * Output message for the operations.
     */
    private static String replyMessage;

    /**
     * Boolean to control the flow of the program.
     */
    private static boolean isExit = false;

    /**
     * Task manager for the application.
     */
    private static final TaskManager taskManager = new TaskManager();

    // Set up to print default messages
    // Return: String, the default message based on different command
    // Param: Commands command, used in switch case to set the proper replyMessage

    /**
     * Returns the default message of respective commands.
     *
     * @param command Type of command.
     * @return Respective command message.
     */
    private static String getDefaultMessage(CommandType command) {
        switch (command) {
        case COMMAND_INIT:
            replyMessage = Formatter.INDENT_ONE_TAB + "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " +
                    DateTimeManager.getDay() + ". The time now is " + DateTimeManager.getTime() + "." +
                    System.lineSeparator() + Formatter.INDENT_ONE_TAB + "What can I do for you?";
            break;
        case COMMAND_EXIT:
            replyMessage = Formatter.INDENT_ONE_TAB + "Bye! Hope to see you again soon!";
            break;
        case COMMAND_MISSING:
            replyMessage = Formatter.INDENT_ONE_TAB + "You did not enter anything, did you?";
            break;
        case COMMAND_UNRECOGNIZED:
            replyMessage = Formatter.INDENT_ONE_TAB + "Sorry I don't know what that means... >.<";
            break;
        default:
            replyMessage = "";
        }
        return replyMessage;
    }

    /**
     * Loads the taskList on initialization.
     */
    private static void init() {
        reply(IOManager.loadTaskList(taskManager.getTaskList()));
        reply(getDefaultMessage(userCommand));
    }

    /**
     * Handles user input and execute the functions accordingly.
     */
    private static void handleUserInput() {
        // String object to hold any user input or output message
        String message = in.nextLine();

        // Split the message to the command and the remaining message
        String[] splitMessage = (message.split(" ", 2));
        String command = splitMessage[0];

        switch (command.toUpperCase()) {
        case COMMAND_STRING_LIST:
            userCommand = CommandType.COMMAND_LIST;
            break;
        case COMMAND_STRING_DONE:
            userCommand = CommandType.COMMAND_MARK_DONE;
            break;
        case COMMAND_STRING_BYE:
            userCommand = CommandType.COMMAND_EXIT;
            break;
        case COMMAND_STRING_TODO:
        case COMMAND_STRING_EVENT:
        case COMMAND_STRING_DEADLINE:
            userCommand = CommandType.COMMAND_ADD;
            break;
        case COMMAND_STRING_DELETE:
            userCommand = CommandType.COMMAND_DELETE;
            break;
        case COMMAND_STRING_EMPTY:
            userCommand = CommandType.COMMAND_MISSING;
            break;
        default:
            userCommand = CommandType.COMMAND_UNRECOGNIZED;
            break;
        }

        // Process according to the command
        switch (userCommand) {
        case COMMAND_ADD:
            replyMessage = taskManager.addTask(splitMessage, command);
            IOManager.saveTaskList(taskManager.getTaskList());
            break;
        case COMMAND_LIST:
            replyMessage = taskManager.listTask();
            break;
        case COMMAND_MARK_DONE:
            replyMessage = taskManager.markTaskDone(splitMessage);
            IOManager.saveTaskList(taskManager.getTaskList());
            break;
        case COMMAND_DELETE:
            replyMessage = taskManager.deleteTask(splitMessage);
            IOManager.saveTaskList(taskManager.getTaskList());
            break;
        case COMMAND_EXIT:
            isExit = true;
        case COMMAND_UNRECOGNIZED:
        case COMMAND_MISSING:
        case COMMAND_INIT:
            replyMessage = getDefaultMessage(userCommand);
            break;
        default:
            break;
        }
        reply(replyMessage);
    }

    /**
     * Main function of the program.
     *
     * @param args User entered arguments.
     */
    public static void main(String[] args) {
        init();
        do {
            handleUserInput();
        }
        while (!isExit);
    }
}