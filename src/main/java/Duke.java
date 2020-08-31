import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    // types of command
    enum COMMANDS {
        COMMAND_INIT,
        COMMAND_ADD,
        COMMAND_LIST,
        COMMAND_MARKDONE,
        COMMAND_EXIT,
        COMMAND_UNRECOGNIZE,
    }

    // Formatting
    private final static String formatDashes = "----------------------------------------------------------------------";
    private final static String formatTwoTabs = "    ";
    private final static String formatFourTabs = "        ";

    // String object to hold any user input or output message
    private static String message = "";
    private static String[] splitMessage;
    private static String command = "";

    private static Scanner in = new Scanner(System.in);

    // Characters for tick and cross
    public static char tick = '\u2713';
    public static char cross = '\u2718';

    // Boolean for exit app
    private static boolean isExit = false;

    // User task list
    private static List<Task> taskList = new ArrayList<>();

    // Print message with formatting
    private static void reply(String msg) {
        System.out.println(msg + formatDashes);
    }

    private static void printWelcomeMessage() {
        // Welcome message
        message = formatTwoTabs + "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " + DateTimeManager.getDay() +
                ". The " + "time now is " + DateTimeManager.getTime() + "." + System.lineSeparator() + formatTwoTabs + "What can I" +
                " do for you?" + System.lineSeparator();
        reply(message);
    }

    private static void printExitMessage() {
        message = formatTwoTabs + "Bye! Hope to see you again soon!" + System.lineSeparator();
        reply(message);
    }

    // Add task
    private static void AddTask() {
        Task newTask;

        message = formatTwoTabs + "Added ";
        // Create new task object based on the command type
        switch(command.toUpperCase()) {
        case "DEADLINE":
            splitMessage = splitMessage[1].split("/by ", 2);
            newTask = new Deadline(splitMessage[0], splitMessage[1]);
            message += "a deadline task ";
            break;
        case "EVENT":
            splitMessage = splitMessage[1].split("/at ", 2);
            newTask = new Event(splitMessage[0], splitMessage[1]);
            message += "an event ";
            break;
        case "TODO":
        default:
            newTask = new Todo(splitMessage[1]);
            message += "a todo task ";
            break;
        }

        // add to the list
        taskList.add(newTask);
        // print out the newly added task
        message += newTask + "!" + System.lineSeparator() + formatTwoTabs + "Now you have " + taskList.size() +
                " task(s) in the list" + System.lineSeparator();
        reply(message);
    }

    // List task
    private static void ListTask() {
        int index = 1;
        // print out default message if there are no task, else print the list of tasks
        if (taskList.size() == 0) {
            message = formatTwoTabs + "There are currently no task in the list! >.<" + System.lineSeparator();
        } else {
            message = formatTwoTabs + "Here is your list of task(s):" + System.lineSeparator();
            for (Task i : taskList) {
                message += formatFourTabs + index + "." + i + System.lineSeparator();
                ++index;
            }
        }
        reply(message);
    }

    // Mark tasks that are done
    private static void MarkTaskDone() {
        // Get the index of the space
        int spacingIndex = message.indexOf(" ");
        // Get the index that the user mark done
        int taskIndex = Integer.parseInt(message.substring(spacingIndex + 1));

        // Do a valid check if within number of task
        if (taskIndex > 0 && taskIndex <= taskList.size()) {
            Task task = taskList.get(taskIndex - 1);
            task.setIsDone(true);
            message = formatTwoTabs + "Completed task " + taskIndex + "!" + System.lineSeparator() + formatFourTabs + task
                    + System.lineSeparator();
        } else {
            message = formatTwoTabs + "Ops, you have entered an invalid task number! You have " + taskList.size() +
                    " task(s)!" + System.lineSeparator();
        }
        reply(message);
    }

    private static void HandleUserInput() {
        message = in.nextLine();
        // reply for formatting
        reply("");

        // Split the message to the first word and the remaining message
        splitMessage = (message.split(" ", 2));
        command = splitMessage[0];

        // Set the type of user command based on input
        COMMANDS userCommand;

        switch (command.toUpperCase()) {
        case "LIST":
            userCommand = COMMANDS.COMMAND_LIST;
            break;
        case "DONE":
            userCommand = COMMANDS.COMMAND_MARKDONE;
            break;
        case "BYE":
            userCommand = COMMANDS.COMMAND_EXIT;
            break;
        case "TODO":
        case "EVENT":
        case "DEADLINE":
            userCommand = COMMANDS.COMMAND_ADD;
            break;
        default:
            userCommand = COMMANDS.COMMAND_UNRECOGNIZE;
            break;
        }

        // Handles different user command
        switch (userCommand) {
        case COMMAND_ADD:
            AddTask();
            break;
        case COMMAND_LIST:
            ListTask();
            break;
        case COMMAND_MARKDONE:
            MarkTaskDone();
            break;
        case COMMAND_EXIT:
            isExit = true;
            break;
        case COMMAND_UNRECOGNIZE:
        case COMMAND_INIT:
        default:
            break;
        }
    }

    public static void main(String[] args) {
        printWelcomeMessage();
        do {
            HandleUserInput();
        }
        while (!isExit);
        printExitMessage();
    }
}
