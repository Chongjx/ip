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
        COMMAND_MARKDONE,
        COMMAND_EXIT,
        COMMAND_UNRECOGNIZE,
        COMMAND_MISSING,
    }

    // String object to hold any user input or output message
    private static String message = "";

    // Set the type of user command based on input
    private static Commands userCommand = Commands.COMMAND_INIT;
    private static Scanner in = new Scanner(System.in);

    // Boolean for exit app
    private static boolean isExit = false;

    // Create a task manager
    private static TaskManager taskManager = new TaskManager();

    // Set up for default messages
    private static void printDefaultMessage(Commands command) {
        switch (command) {
        case COMMAND_INIT:
            message = Formatter.FORMAT_TWO_TABS + "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " + DateTimeManager.getDay() +
                    ". The " + "time now is " + DateTimeManager.getTime() + "." + System.lineSeparator() + Formatter.FORMAT_TWO_TABS +
                    "What can I do for you?";
            break;
        case COMMAND_EXIT:
            message = Formatter.FORMAT_TWO_TABS + "Bye! Hope to see you again soon!";
            break;
        case COMMAND_MISSING:
            message = Formatter.FORMAT_TWO_TABS + "You did not enter anything, did you?";
            break;
        case COMMAND_UNRECOGNIZE:
            message = Formatter.FORMAT_TWO_TABS + "Sorry I don't know what that means... >.<";
            break;
        default:
            message = "";
        }
        reply(message);
    }

    // Handles user input
    private static void handleUserInput() {
        message = in.nextLine();

        // Split the message to the command and the remaining message
        String[] splitMessage = (message.split(" ", 2));
        String command = splitMessage[0];

        switch (command.toUpperCase()) {
        case "LIST":
            userCommand = Commands.COMMAND_LIST;
            break;
        case "DONE":
            userCommand = Commands.COMMAND_MARKDONE;
            break;
        case "BYE":
            userCommand = Commands.COMMAND_EXIT;
            break;
        case "TODO":
        case "EVENT":
        case "DEADLINE":
            userCommand = Commands.COMMAND_ADD;
            break;
        case "":
            userCommand = Commands.COMMAND_MISSING;
            break;
        default:
            userCommand = Commands.COMMAND_UNRECOGNIZE;
            break;
        }

        // Process according to the command
        switch (userCommand) {
        case COMMAND_ADD:
            message = taskManager.addTask(splitMessage, command);
            reply(message);
            break;
        case COMMAND_LIST:
            message = taskManager.listTask();
            reply(message);
            break;
        case COMMAND_MARKDONE:
            message = taskManager.markTaskDone(splitMessage);
            reply(message);
            break;
        case COMMAND_EXIT:
            IOManager.saveTaskList(taskManager.getTaskList());
            isExit = true;
            break;
        case COMMAND_UNRECOGNIZE:
        case COMMAND_MISSING:
            printDefaultMessage(userCommand);
            break;
        case COMMAND_INIT:
        default:
            break;
        }
    }

    public static void main(String[] args) {
        IOManager.loadTaskList(taskManager.getTaskList());
        printDefaultMessage(userCommand);
        do {
            handleUserInput();
        }
        while (!isExit);
        printDefaultMessage(userCommand);
    }
}
