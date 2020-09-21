package duke;

import duke.managers.IOManager;
import duke.managers.TaskManager;
import duke.managers.UIManager;

/**
 * Entry point of the application.
 * Initializes the application and starts the interaction with the user.
 */
public class Duke {

    /**
     * Output message for the operations.
     */
    private String replyMessage;

    /**
     * Holds the type of user command.
     */
    private TaskManager.CommandType userCommand;

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
     * Initializes the variables and load the saved data (if any) from the text file into the taskList.
     */
    private void init() {
        isExit = false;
        userCommand = TaskManager.CommandType.COMMAND_INIT;

        uiManager = new UIManager();
        taskManager = new TaskManager();
        ioManager = new IOManager();

        // Prints out the status of loading the saved data into the taskList
        uiManager.prints(ioManager.loadTaskList(taskManager.getTaskList()));
        uiManager.printDefaultMessage(userCommand);
    }

    /**
     * Handles user input and execute the functions accordingly.
     */
    private void handleUserInput() {
        // retrieve the type of command based on user input
        userCommand = uiManager.getUserInput();

        // Retrieve the message is split into command and description
        String[] splitMessages = uiManager.getSplitMessages();
        String command = splitMessages[0];

        // Process according to the command
        switch (userCommand) {
        case COMMAND_ADD:
            replyMessage = taskManager.addTask(splitMessages, command);
            ioManager.saveTaskList(taskManager.getTaskList());
            uiManager.prints(replyMessage);
            break;
        case COMMAND_LIST:
            replyMessage = taskManager.listTask();
            uiManager.prints(replyMessage);
            break;
        case COMMAND_MARK_DONE:
            replyMessage = taskManager.markTaskDone(splitMessages);
            ioManager.saveTaskList(taskManager.getTaskList());
            uiManager.prints(replyMessage);
            break;
        case COMMAND_DELETE:
            replyMessage = taskManager.deleteTask(splitMessages);
            ioManager.saveTaskList(taskManager.getTaskList());
            uiManager.prints(replyMessage);
            break;
        case COMMAND_EXIT:
            isExit = true;
        case COMMAND_UNRECOGNIZED:
        case COMMAND_MISSING:
        case COMMAND_INIT:
            uiManager.printDefaultMessage(userCommand);
            break;
        default:
            break;
        }
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