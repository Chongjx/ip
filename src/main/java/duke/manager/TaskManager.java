package duke.manager;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.util.DukeException;
import duke.util.Formatter;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    // User task list
    private List<Task> taskList;
    private String returnMessage;

    public TaskManager() {
        taskList = new ArrayList<>();
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    // Validate the info for adding task, ensures description and required keyword are present
    // Return: String[], more specifically an array of size 2 with the index 0 as the description of
    // the task and index 1 as the date and time info for Deadline and Event
    // Param: String[] message, the whole string of the original message, to be processed for Deadline and Event tasks
    // Param: String identifier, used to split the original message to obtain the date time info
    // Throws: DukeException, throws all possible exception that may occur, missing info
    public String[] validateTaskInfo(String[] message, String identifier) throws DukeException {
        String[] processedString = new String[2];
        String description;
        String dateTime;
        try {
            description = message[1];
            // if the message is empty, throw missing description exception
            if (description.isEmpty() || description.isBlank()) {
                throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
            } else if (!identifier.isEmpty()) {
                // split message by identifier, if specified. Throw missing identifier if the string does not contain
                // the identifier
                if (!description.contains(identifier)) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_IDENTIFIER);
                }
                // split message based on identifier
                processedString = message[1].split(identifier, 2);
                description = processedString[0];
                dateTime = processedString[1];
                // if missing description or datetime, throw respective exception
                if (description.isEmpty()) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
                } else if (dateTime.isEmpty() || dateTime.isBlank()) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DATETIME);
                }
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            // extra handling in the the event that the message is empty and cannot be split
            throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
        }
        return processedString;
    }

    // Create new task and add to the task list
    // Return: String, a reply message for the method operation
    // Param: String[] message, the whole string of the original message, to be passed into validateTaskInfo and
    // retrieve the specific info
    // Param: String command, used in switch case to add the proper task
    public String addTask(String[] message, String command) {
        String[] taskInfo;
        try {
            Task newTask = null;
            returnMessage = Formatter.FORMAT_ONE_TAB + "Added ";

            // Create new task object based on the command type
            switch (command.toUpperCase()) {
            case "DEADLINE":
                taskInfo = validateTaskInfo(message, "/by");
                newTask = new Deadline(taskInfo[0], taskInfo[1]);
                returnMessage += "a deadline task ";
                break;
            case "EVENT":
                taskInfo = validateTaskInfo(message, "/at");
                newTask = new Event(taskInfo[0], taskInfo[1]);
                returnMessage += "an event ";
                break;
            case "TODO":
                validateTaskInfo(message, "");
                newTask = new Todo(message[1]);
                returnMessage += "a todo task ";
                break;
            }
            // add to the list
            taskList.add(newTask);
            // print out the newly added task
            returnMessage += newTask + "!" + System.lineSeparator() + Formatter.FORMAT_TWO_TABS + "Now you have " +
                    taskList.size() + " task(s) in the list!";
        } catch (DukeException e) {
            returnMessage = Formatter.FORMAT_ONE_TAB + e.getMessage();
        }
        return  returnMessage;
    }

    // List all the task in the taskList
    // Return: String, a reply message for the method operation
    public String listTask() {
        // print out default message if there are no task, else print the list of tasks
        if (taskList.size() == 0) {
            returnMessage = Formatter.FORMAT_ONE_TAB + "There are currently no task in the list! (*^▽^*)";
        } else {
            returnMessage = Formatter.FORMAT_ONE_TAB + "Here is your list of task(s):";
            int index = 1;
            for (Task i : taskList) {
                returnMessage += System.lineSeparator() + Formatter.FORMAT_TWO_TABS + index + "." + i;
                ++index;
            }
        }
        return returnMessage;
    }

    // Common functionality for markTaskDone and deleteTask
    // Param: boolean isMarkDone, true for marking task done; false for deleting task
    // Param: String[] taskIndexString, contains the whole string of the original message. Index 1 is the task index
    private void markDoneOrDeleteTask(boolean isMarkDone, String[] taskIndexString) {
        // Get the task index
        try {
            int taskIndex = Integer.parseInt(taskIndexString[1]);
            Task task = taskList.get(taskIndex - 1);
            // mark task done or delete task depending on the option
            if (isMarkDone) {
                task.setIsDone(true);
                returnMessage = Formatter.FORMAT_ONE_TAB + "Completed task " + taskIndex + "!" + System.lineSeparator() +
                        Formatter.FORMAT_TWO_TABS + task;
            } else {
                taskList.remove(taskIndex - 1);
                returnMessage = Formatter.FORMAT_ONE_TAB + "I have removed the task !" + System.lineSeparator() +
                        Formatter.FORMAT_TWO_TABS + task + System.lineSeparator() + Formatter.FORMAT_ONE_TAB +
                        "Now you have " + taskList.size() + " task(s) in the list!";
            }
        } catch (NumberFormatException exception) {
            returnMessage = Formatter.FORMAT_ONE_TAB + "Invalid input, cannot convert to integer!";
        } catch (ArrayIndexOutOfBoundsException exception) {
            returnMessage = Formatter.FORMAT_ONE_TAB + "You did not enter any value!";
        } catch (IndexOutOfBoundsException exception) {
            returnMessage = Formatter.FORMAT_ONE_TAB + "Ops, you have entered an invalid task number! You have " +
                    taskList.size() + " task(s)!";
        }
    }

    // Mark tasks that are done
    // Return: String, a reply message for the method operation
    // Param: String[] taskIndexString, the original message, to be passed into markDoneOrDelete to process
    public String markTaskDone(String[] taskIndexString) {
        markDoneOrDeleteTask(true, taskIndexString);
        return returnMessage;
    }

    // Delete specific task
    // Return: String, a reply message for the method operation
    // Param: String[] taskIndexString, the original message, to be passed into markDoneOrDelete to process
    public String deleteTask(String[] taskIndexString) {
        markDoneOrDeleteTask(false, taskIndexString);
        return returnMessage;
    }
}