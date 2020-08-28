import java.lang.reflect.Array;
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
    }

    // Formatting
    private static String formatDashes = "----------------------------------------------------------------------";
    private static String formatTwoTabs = "    ";
    private static String formatFourTabs = "        ";

    // String object to hold any user input or output message
    private static String message = "";
    private static String command = "";
    // Types of command that the user entered
    private static COMMANDS userCommand = COMMANDS.COMMAND_INIT;

    private static Scanner in = new Scanner(System.in);

    // Characters for tick and cross
    private static char tick = '\u2713';
    private static char cross = '\u2718';

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
        // create a new task object
        Task newTask = new Task(message);
        // add to the list
        taskList.add(newTask);
        // print out the newly added task
        reply(formatTwoTabs + "Added task " + taskList.size() + ". " + message + System.lineSeparator());
    }

    // List task
    private static void ListTask() {
        String output = "";
        int index = 1;
        // print out default message if there are no task, else print the list of tasks
        if (taskList.size() == 0) {
            output = formatTwoTabs + "There are currently no task in the list! >.<" + System.lineSeparator();
        } else {
            output = formatTwoTabs + "Here is your list of task(s):" + System.lineSeparator();
            for (Task i : taskList) {
                output += formatFourTabs + index + ".[" + (i.getIsDone() ? tick : cross) + "]" + i.getDescription() + System.lineSeparator();
                ++index;
            }
        }
        reply(output);
    }

    // Mark tasks that are done
    private static void MarkTaskDone() {
        // Get the index of the space
        int spacingIndex = message.indexOf(" ");
        // Get the index that the user mark done
        int taskIndex = Integer.parseInt(message.substring(spacingIndex + 1, message.length()));

        // Do a valid check if within number of task
        if (taskIndex > 0 && taskIndex <= taskList.size()) {
            Task task = taskList.get(taskIndex - 1);
            task.setIsDone(true);
            message = formatTwoTabs + "Completed task " + taskIndex + "!" + System.lineSeparator() + formatFourTabs + "[" +
                    (task.getIsDone() ? tick : cross) + "]" + task.getDescription() + System.lineSeparator();
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

        command = (message.split(" "))[0];

        // Set the type of user command based on input
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
        default:
            userCommand = COMMANDS.COMMAND_ADD;
            break;
        }

        // Handles different user command
        switch (userCommand) {
        case COMMAND_INIT:
            break;
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
