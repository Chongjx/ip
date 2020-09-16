package duke;

import duke.manager.DateTimeManager;
import duke.manager.IOManager;
import duke.manager.TaskManager;
import duke.util.Formatter;
import java.util.Scanner;

public class Duke {
    // Print message with formatting
    public static void reply(String message) {
        System.out.println(message + System.lineSeparator() + Formatter.FORMAT_DASHES);
    }

    // types of command
    private enum Commands {
        COMMAND_INIT,
        COMMAND_ADD,
        COMMAND_LIST,
        COMMAND_MARK_DONE,
        COMMAND_DELETE,
        COMMAND_EXIT,
        COMMAND_UNRECOGNIZED,
        COMMAND_MISSING,
    }

    // Command string identifier
    private static final String CSI_LIST = "LIST";
    private static final String CSI_DONE = "DONE";
    private static final String CSI_DELETE = "DELETE";
    private static final String CSI_BYE = "BYE";
    private static final String CSI_EMPTY = "";
    public static final String CSI_TODO = "TODO";
    public static final String CSI_EVENT = "EVENT";
    public static final String CSI_DEADLINE = "DEADLINE";

    // Set the type of user command based on input
    private static Commands userCommand = Commands.COMMAND_INIT;
    private static final Scanner in = new Scanner(System.in);

    // String for replying
    private static String replyMessage;

    // Boolean for exit app
    private static boolean isExit = false;

    // Create a task manager
    private static final TaskManager taskManager = new TaskManager();

    // Set up to print default messages
    // Return: String, the default message based on different command
    // Param: Commands command, used in switch case to set the proper replyMessage
    private static String printDefaultMessage(Commands command) {
        switch (command) {
        case COMMAND_INIT:
            replyMessage = Formatter.INDENT_ONE_TAB + "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " +
                    DateTimeManager.getDay() + ". The " + "time now is " + DateTimeManager.getTime() + "." +
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

    private static void init() {
        reply(IOManager.loadTaskList(taskManager.getTaskList()));
        reply(printDefaultMessage(userCommand));
    }

    // Handles user input
    private static void handleUserInput() {
        // String object to hold any user input or output message
        String message = in.nextLine();

        // Split the message to the command and the remaining message
        String[] splitMessage = (message.split(" ", 2));
        String command = splitMessage[0];

        switch (command.toUpperCase()) {
        case CSI_LIST:
            userCommand = Commands.COMMAND_LIST;
            break;
        case CSI_DONE:
            userCommand = Commands.COMMAND_MARK_DONE;
            break;
        case CSI_BYE:
            userCommand = Commands.COMMAND_EXIT;
            break;
        case CSI_TODO:
        case CSI_EVENT:
        case CSI_DEADLINE:
            userCommand = Commands.COMMAND_ADD;
            break;
        case CSI_DELETE:
            userCommand = Commands.COMMAND_DELETE;
            break;
        case CSI_EMPTY:
            userCommand = Commands.COMMAND_MISSING;
            break;
        default:
            userCommand = Commands.COMMAND_UNRECOGNIZED;
            break;
        }

        // Process according to the command
        switch (userCommand) {
        case COMMAND_ADD:
            replyMessage = taskManager.addTask(splitMessage, command);
            break;
        case COMMAND_LIST:
            replyMessage = taskManager.listTask();
            break;
        case COMMAND_MARK_DONE:
            replyMessage = taskManager.markTaskDone(splitMessage);
            break;
        case COMMAND_DELETE:
            replyMessage = taskManager.deleteTask(splitMessage);
            break;
        case COMMAND_EXIT:
            IOManager.saveTaskList(taskManager.getTaskList());
            isExit = true;
        case COMMAND_UNRECOGNIZED:
        case COMMAND_MISSING:
        case COMMAND_INIT:
            replyMessage = printDefaultMessage(userCommand);
            break;
        default:
            break;
        }
        reply(replyMessage);
    }

    public static void main(String[] args) {
        init();
        do {
            handleUserInput();
        }
        while (!isExit);
    }
}