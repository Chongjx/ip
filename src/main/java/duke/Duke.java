package duke;

import duke.managers.DateTimeManager;
import duke.managers.IOManager;
import duke.managers.TaskManager;
import duke.managers.UIManager;

import java.util.Scanner;

/**
 * Entry point of the application.
 * Initializes the application and starts the interaction with the user.
 */
public class Duke {

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
    private CommandType userCommand;

    /**
     * Output message for the operations.
     */
    private String replyMessage;

    private TaskManager taskManager;
    private IOManager ioManager;
    private UIManager uiManager;

    private static boolean isExit;

    /** Runs the program until termination */
    public void run() {
        init();
        do {
            handleUserInput();
        }
        while (!isExit);
    }
    /**
     * Returns the default message of respective commands.
     *
     * @param command Type of command.
     * @return Respective command message.
     */
    private String getDefaultMessage(CommandType command) {
        switch (command) {
        case COMMAND_INIT:
            replyMessage = UIManager.INDENT_ONE_TAB + "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " +
                    DateTimeManager.getDay() + ". The time now is " + DateTimeManager.getTime() + "." + UIManager.LS +
                    UIManager.INDENT_ONE_TAB + "What can I do for you?";
            break;
        case COMMAND_EXIT:
            replyMessage = UIManager.INDENT_ONE_TAB + "Bye! Hope to see you again soon!";
            break;
        case COMMAND_MISSING:
            replyMessage = UIManager.INDENT_ONE_TAB + "You did not enter anything, did you?";
            break;
        case COMMAND_UNRECOGNIZED:
            replyMessage = UIManager.INDENT_ONE_TAB + "Sorry I don't know what that means... >.<";
            break;
        default:
            replyMessage = "";
        }
        return replyMessage;
    }

    /**
     * Initializes the variables and load the saved data (if any) from the text file into the taskList.
     */
    private void init() {
        isExit = false;
        userCommand = CommandType.COMMAND_INIT;

        uiManager = new UIManager();
        taskManager = new TaskManager();
        ioManager = new IOManager();

        // Prints out the status of loading the saved data into the taskList
        uiManager.prints(ioManager.loadTaskList(taskManager.getTaskList()));
        uiManager.prints(getDefaultMessage(userCommand));
    }

    /**
     * Handles user input and execute the functions accordingly.
     */
    private void handleUserInput() {
        // String object to hold any user input or output message
        String userInput = uiManager.getUserInput();

        // Split the message to the command and the remaining message
        String[] splitMessages = (userInput.split(" ", 2));
        String command = splitMessages[0];

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
            replyMessage = taskManager.addTask(splitMessages, command);
            ioManager.saveTaskList(taskManager.getTaskList());
            break;
        case COMMAND_LIST:
            replyMessage = taskManager.listTask();
            break;
        case COMMAND_MARK_DONE:
            replyMessage = taskManager.markTaskDone(splitMessages);
            ioManager.saveTaskList(taskManager.getTaskList());
            break;
        case COMMAND_DELETE:
            replyMessage = taskManager.deleteTask(splitMessages);
            ioManager.saveTaskList(taskManager.getTaskList());
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
        uiManager.prints(replyMessage);
    }

    /**
     * Main function of the program.
     *
     * @param args User entered arguments.
     */
    public static void main(String[] args) {
        new Duke().run();
    }
}