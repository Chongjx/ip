import java.util.Arrays;
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
    private static String message = "";

    // boolean for exit app
    private static boolean isExit = false;

    // user task list
    private static String[] taskList = new String[100];
    // keep track of the number of task that the user entered
    private static int numTask = 0;

    // Print message with formatting
    private static void reply(String msg) {
        System.out.println(msg + dashOutline);
    }

    // Add task
    private static void AddTask(String task) {
        taskList[numTask] = task;
        ++numTask;
        reply(indentation + "Added: " + task + System.lineSeparator());
    }

    // List task
    private static void ListTask() {
        String output = "";
        if (numTask == 0) {
            output = "There are currently no task in the list!";
        } else {
            for (int i = 0; i < numTask; ++i) {
                output += indentation + (i + 1) + ". " + taskList[i] + System.lineSeparator();
            }
        }
        reply(output);
    }

    public static void main(String[] args) {
        // string to hold the user input
        String userInput;
        Scanner in = new Scanner(System.in);

        // type of command that the user entered
        COMMAND userCommand = COMMAND.INIT;

        // Welcome message
        message = indentation + "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " + DateTimeManager.getDay() + ". The " +
                "time now is " + DateTimeManager.getTime() + "." + System.lineSeparator() + indentation + "What can I" +
                " do for you?" + System.lineSeparator();
        reply(message);

        do {
            message = in.nextLine();
            reply("");

            // set the type of user command based on input
            switch (message.toUpperCase()) {
            case "LIST":
                userCommand = COMMAND.LIST;
                break;
            case "BYE":
                userCommand = COMMAND.EXIT;
                break;
            default:
                userCommand = COMMAND.ADD;
                break;
            }

            // handle different user command
            switch (userCommand) {
            case INIT:
                break;
            case ADD:
                AddTask(message);
                break;
            case LIST:
                ListTask();
                break;
            case MARKDONE:
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
