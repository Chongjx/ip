package duke;

import duke.managers.IOManager;
import duke.managers.TaskManager;
import duke.managers.UIManager;
import duke.util.Parser;

/**
 * Entry point of the application.
 * Initializes the application and starts the interaction with the user.
 */
public class Duke {
    /**
     * Holds the type of user command.
     */
    private TaskManager.CommandType userCommand;

    /** TaskManager for the program. */
    private TaskManager taskManager;
    /** IOManager for the program. */
    private IOManager ioManager;
    /** UIManager for the program. */
    private UIManager uiManager;

    /** Runs the program until termination. */
    public void run() {
        init();
        do {
            handleUserInput();
        }
        while (!(userCommand == TaskManager.CommandType.COMMAND_EXIT));
    }

    /**
     * Initializes the variables and load the saved data (if any) from the text file into the taskList.
     */
    private void init() {
        userCommand = TaskManager.CommandType.COMMAND_INIT;

        uiManager = new UIManager();
        taskManager = new TaskManager();
        ioManager = new IOManager();

        // Initialize the task list from the txt file
        uiManager.prints(ioManager.loadTaskList(taskManager.getTaskList()));
        uiManager.prints(taskManager.getCommandDefaultMessage(TaskManager.CommandType.COMMAND_INIT));
    }

    /**
     * Handles user input and execute the functions accordingly.
     */
    private void handleUserInput() {
        // retrieve the type of command based on user input
        String userCommandInput = uiManager.getUserCommandInput();
        userCommand = new Parser().parseCommand(userCommandInput);

        // Retrieve the message is split into command and description
        String[] splitMessages = uiManager.getTaskInfo();

        uiManager.prints(taskManager.handleTask(userCommand, splitMessages));
        ioManager.saveTaskList(taskManager.getTaskList());
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