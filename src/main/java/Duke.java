import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    // types of command
    enum COMMAND {
        INIT,
        ADD,
        LIST,
        MARKDONE,
        EXIT,
    }

    // Formatting
    private static String dashOutline = "----------------------------------------------------------------------";
    private static String indentation = "       ";

    // String object to hold any user input or output message
    private static String message = "";
    private static String command = "";

    // Characters for tick and cross
    private static char tick = '\u2713';
    private static char cross = '\u2718';

    // Boolean for exit app
    private static boolean isExit = false;

    // User task list
    private static List<Task> taskList = new ArrayList<>();

    // Print message with formatting
    private static void reply(String msg) {
        System.out.println(msg + dashOutline);
    }

    // Add task
    private static void AddTask() {
        // create a new task object
        Task newTask = new Task(message);
        // add to the list
        taskList.add(newTask);
        // print out the newly added task
        reply(indentation + "Added task " + taskList.size() + ". " + message + System.lineSeparator());
    }

    // List task
    private static void ListTask() {
        String output = "";
        int index = 1;
        // print out default message if there are no task, else print the list of tasks
        if (taskList.size() == 0) {
            output = indentation + "There are currently no task in the list! >.<" + System.lineSeparator();
        } else {
            output = indentation + "Here is your list of task(s):" + System.lineSeparator();
            for (Task i : taskList) {
                output += indentation + index + ".[" + (i.getIsDone() ? tick : cross) + "]" + i.getDescription() + System.lineSeparator();
                ++index;
            }
        }
        reply(output);
    }

    // Mark tasks that are done
    private static void MarkDone() {
        // Get the index of the space
        int spacingIndex = message.indexOf(" ");
        // Get the index that the user mark done
        int taskIndex = Integer.parseInt(message.substring(spacingIndex + 1, message.length()));

        // Do a valid check if within number of task
        if (taskIndex > 0 && taskIndex <= taskList.size()) {
            Task task = taskList.get(taskIndex - 1);
            task.setIsDone(true);
            message = indentation + "Completed task " + taskIndex + "!" + System.lineSeparator() + indentation + "[" +
                    (task.getIsDone() ? tick : cross) + "]" + task.getDescription() + System.lineSeparator();
        } else {
            message = indentation+ "Ops, you have entered an invalid task number! You have " + taskList.size() + " " +
                    "task(s)!" + System.lineSeparator();
        }
        reply(message);
    }

    public static void main(String[] args) {
        // String to hold the user input
        String userInput;
        Scanner in = new Scanner(System.in);

        // Types of command that the user entered
        COMMAND userCommand = COMMAND.INIT;

        // Welcome message
        message = indentation + "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " + DateTimeManager.getDay() + ". The " +
                "time now is " + DateTimeManager.getTime() + "." + System.lineSeparator() + indentation + "What can I" +
                " do for you?" + System.lineSeparator();
        reply(message);

        do {
            message = in.nextLine();
            // reply for formatting
            reply("");

            command = (message.split(" "))[0];

            // Set the type of user command based on input
            switch (command.toUpperCase()) {
            case "LIST":
                userCommand = COMMAND.LIST;
                break;
            case "DONE":
                userCommand = COMMAND.MARKDONE;
                break;
            case "BYE":
                userCommand = COMMAND.EXIT;
                break;
            default:
                userCommand = COMMAND.ADD;
                break;
            }

            // Handles different user command
            switch (userCommand) {
            case INIT:
                break;
            case ADD:
                AddTask();
                break;
            case LIST:
                ListTask();
                break;
            case MARKDONE:
                MarkDone();
                break;
            case EXIT:
                isExit = true;
                break;
            }
        }
        while (!isExit);

        message = indentation + "Bye! Hope to see you again soon!" + System.lineSeparator();
        reply(message);
    }
}
