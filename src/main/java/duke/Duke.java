package duke;

import duke.manager.DateTimeManager;
import duke.manager.DukeException;
import duke.manager.Formatter;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    // types of command
    enum Commands {
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
    private static String[] splitMessage;
    private static String command = "";

    // Set the type of user command based on input
    private static Commands userCommand = Commands.COMMAND_INIT;

    private static Scanner in = new Scanner(System.in);

    // Boolean for exit app
    private static boolean isExit = false;

    // User task list
    private static List<Task> taskList = new ArrayList<>();

    // Set up for default messages
    private static void printDefaultMessage(Commands command) {
        switch (command) {
        case COMMAND_INIT:
            message = Formatter.formatTwoTabs + "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " + DateTimeManager.getDay() +
                    ". The " + "time now is " + DateTimeManager.getTime() + "." + System.lineSeparator() + Formatter.formatTwoTabs +
                    "What can I do for you?";
            break;
        case COMMAND_EXIT:
            message = Formatter.formatTwoTabs + "Bye! Hope to see you again soon!";
            break;
        case COMMAND_MISSING:
            message = Formatter.formatTwoTabs + "You did not enter anything, did you?";
            break;
        case COMMAND_UNRECOGNIZE:
            message = Formatter.formatTwoTabs + "Sorry I don't know what that means... >.<";
            break;
        default:
            message = "";
        }
        Formatter.reply(message);
    }

    // Validate the info for adding task
    private static void validateTaskInfo(String identifier) throws DukeException {
        try {
            // if the message is empty
            if (splitMessage[1].equals("")) {
                throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
            } else if (!identifier.equals("")) {
                // split message by identifier, throw exception if missing identifier
                if (!splitMessage[1].contains(identifier)) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_IDENTIFIER);
                }
                splitMessage = splitMessage[1].split(identifier, 2);
                // throw exception for respective missing info: description or datetime
                if (splitMessage[0].equals("")) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
                } else if (splitMessage[1].equals("")) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DATETIME);
                }
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
        }
    }

    // Add task
    private static void addTask() {
        try {
            Task newTask = null;

            message = Formatter.formatTwoTabs + "Added ";
            // Create new task object based on the command type
            switch (command.toUpperCase()) {
            case "DEADLINE":
                validateTaskInfo("/by ");
                newTask = new Deadline(splitMessage[0], splitMessage[1]);
                message += "a deadline task ";
                break;
            case "EVENT":
                validateTaskInfo("/at ");
                newTask = new Event(splitMessage[0], splitMessage[1]);
                message += "an event ";
                break;
            case "TODO":
                validateTaskInfo("");
                newTask = new Todo(splitMessage[1]);
                message += "a todo task ";
                break;
            }
            // add to the list
            taskList.add(newTask);
            // print out the newly added task
            message += newTask + "!" + System.lineSeparator() + Formatter.formatTwoTabs + "Now you have " + taskList.size() +
                    " task(s) in the list";
        } catch (DukeException e) {
            message = Formatter.formatTwoTabs + e.getMessage();
        } finally {
            Formatter.reply(message);
        }
    }

    // List task
    private static void listTask() {
        // print out default message if there are no task, else print the list of tasks
        if (taskList.size() == 0) {
            message = Formatter.formatTwoTabs + "There are currently no task in the list! (*^▽^*)";
        } else {
            message = Formatter.formatTwoTabs + "Here is your list of task(s):";
            int index = 1;
            for (Task i : taskList) {
                message += System.lineSeparator() + Formatter.formatFourTabs + index + "." + i;
                ++index;
            }
        }
        Formatter.reply(message);
    }

    // Mark tasks that are done
    private static void markTaskDone() {
        // Get the task index to be marked completed
        try {
            int taskIndex = Integer.parseInt(splitMessage[1]);
            Task task = taskList.get(taskIndex - 1);
            task.setIsDone(true);
            message = Formatter.formatTwoTabs + "Completed task " + taskIndex + "!" + System.lineSeparator() + Formatter.formatFourTabs + task;
        } catch (NumberFormatException exception) {
            message = Formatter.formatTwoTabs + "Invalid input, cannot convert to integer!";
        } catch (ArrayIndexOutOfBoundsException exception) {
            message = Formatter.formatTwoTabs + "You did not enter any value!";
        } catch (IndexOutOfBoundsException exception) {
            message = Formatter.formatTwoTabs + "Ops, you have entered an invalid task number! You have " + taskList.size() +
                    " task(s)!";
        } finally {
            Formatter.reply(message);
        }
    }

    // Handles user input
    private static void handleUserInput() {
        message = in.nextLine();

        // Split the message to the command and the remaining message
        splitMessage = (message.split(" ", 2));
        command = splitMessage[0];

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
            addTask();
            break;
        case COMMAND_LIST:
            listTask();
            break;
        case COMMAND_MARKDONE:
            markTaskDone();
            break;
        case COMMAND_EXIT:
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
        printDefaultMessage(userCommand);
        do {
            handleUserInput();
        }
        while (!isExit);
        printDefaultMessage(userCommand);
    }
}
