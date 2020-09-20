package duke.managers;

import duke.Duke;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.util.DukeException;

import java.util.ArrayList;
import java.util.List;

/**
 * TaskManager to store all the tasks into an ArrayList.
 * Manage adding, listing, deleting and marking task as done operations.
 */
public class TaskManager {
    /** User task list */
    private final List<Task> taskList;
    /** Output message for the operations */
    private String returnMessage;

    /**
     * Creates a new ArrayList to store the tasks.
     */
    public TaskManager() {
        taskList = new ArrayList<>();
    }

    /**
     * Returns the taskList.
     *
     * @return taskList.
     */
    public List<Task> getTaskList() {
        return taskList;
    }

    /**
     * Validates the info for adding task, ensures description and the required keyword are present.
     *
     * @param message The string of the original message, to be processed for Event and Deadline tasks.
     * @param identifier Used to split the original message to obtain the data time info for Event and Deadline tasks.
     * @return The description and date and time info, in index 0 and index 1 respectively, for Event Deadline task.
     * @throws DukeException Missing description, identifier or date and time info.
     */
    public String[] validateTaskInfo(String[] message, String identifier) throws DukeException {
        String[] processedString = new String[2];
        String description;
        String dateTime;
        try {
            description = message[1];
            // if the message is empty or blank, throw missing description exception
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

    /**
     * Creates new task and add to the task list if the info are valid.
     *
     * @param message The whole string of the original message. To validated and retrieve the description of the task
     *               and date time info for Event and Deadline task.
     * @param commandTaskType The type of task to be created.
     * @return An outcome message of this method operation.
     */
    public String addTask(String[] message, String commandTaskType) {
        String[] taskInfo;
        try {
            Task newTask = null;
            returnMessage = UIManager.INDENT_ONE_TAB + "Added ";

            // Create new task object based on the command type
            switch (commandTaskType.toUpperCase()) {
            case Duke.COMMAND_STRING_DEADLINE:
                taskInfo = validateTaskInfo(message, Deadline.IDENTIFIER);
                newTask = new Deadline(taskInfo[0], taskInfo[1]);
                returnMessage = returnMessage.concat("a deadline task ");
                break;
            case Duke.COMMAND_STRING_EVENT:
                taskInfo = validateTaskInfo(message, Event.IDENTIFIER);
                newTask = new Event(taskInfo[0], taskInfo[1]);
                returnMessage = returnMessage.concat("an event ");
                break;
            case Duke.COMMAND_STRING_TODO:
                validateTaskInfo(message, "");
                newTask = new Todo(message[1]);
                returnMessage = returnMessage.concat("a todo task ");
                break;
            }
            // add to the list
            taskList.add(newTask);
            // print out the newly added task
            returnMessage = returnMessage.concat(newTask + "!" + UIManager.LS + UIManager.INDENT_TWO_TABS +
                    "Now you have " + taskList.size() + " task(s) in the list!");
        } catch (DukeException dukeException) {
            returnMessage = UIManager.INDENT_ONE_TAB + dukeException.getMessage();
        }
        return returnMessage;
    }

    // List all the task in the taskList
    // Return: String, a reply message for the method operation

    /**
     * Lists all the task(s) in the taskList.
     *
     * @return All the task(s) in the taskList.
     */
    public String listTask() {
        // print out default message if there are no task, else print the list of tasks
        if (taskList.size() == 0) {
            returnMessage = UIManager.INDENT_ONE_TAB + "There are currently no task in the list! (*^▽^*)";
        } else {
            returnMessage = UIManager.INDENT_ONE_TAB + "Here is your list of task(s):";
            int index = 1;
            for (Task i : taskList) {
                returnMessage = returnMessage.concat(UIManager.LS + UIManager.INDENT_TWO_TABS + index + "." + i);
                ++index;
            }
        }
        return returnMessage;
    }

    /**
     * Validate the task index that the user entered.
     *
     * @param taskIndexString The whole string of message. To be converted to int.
     * @return Task index retrieved from the message.
     * @throws DukeException Index out of bounds, invalid input and array index out of bound.
     */
    private int validateTaskIndex(String[] taskIndexString) throws DukeException {
        int taskIndex;
        // try retrieving the task index
        try {
            taskIndex = Integer.parseInt(taskIndexString[1]) - 1;
            if (taskIndex < 0 || taskIndex > taskList.size() - 1) {
                throw new DukeException(DukeException.ExceptionType.EXCEPTION_INDEX_OUT_OF_BOUNDS);
            }
        } catch (NumberFormatException exception) {
            throw new DukeException(DukeException.ExceptionType.EXCEPTION_INVALID_INPUT);
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new DukeException(DukeException.ExceptionType.EXCEPTION_ARRAY_INDEX_OUT_OF_BOUNDS);
        }
        return taskIndex;
    }

    /**
     * Mark task done based on the index that the user entered.
     *
     * @param taskIndexString The task index in string. To be validated and retrieve the index as int.
     * @return An outcome message of this method operation.
     */
    public String markTaskDone(String[] taskIndexString) {
        // try accessing the taskList and mark the task as done
        try {
            int taskIndex = validateTaskIndex(taskIndexString);
            Task task = taskList.get(taskIndex);
            task.setIsDone(true);
            returnMessage = UIManager.INDENT_ONE_TAB + "Completed task " + (taskIndex + 1) + "!" + UIManager.LS +
                    UIManager.INDENT_TWO_TABS + task;
        } catch (DukeException dukeException) {
            returnMessage = UIManager.INDENT_ONE_TAB + dukeException.getMessage();
        }
        return returnMessage;
    }

    // Delete specific task
    // Return: String, a reply message for the method operation
    // Param: String[] taskIndexString, the original message, to be passed into markDoneOrDelete to process
    /**
     * Delete task based on the index that the user entered.
     *
     * @param taskIndexString The task index in string. To be validated and retrieve the index as int.
     * @return An outcome message of this method operation.
     */
    public String deleteTask(String[] taskIndexString) {
        // try accessing the taskList and delete the task
        try {
            int taskIndex = validateTaskIndex(taskIndexString);
            Task task = taskList.get(taskIndex);
            taskList.remove(taskIndex);
            returnMessage = UIManager.INDENT_ONE_TAB + "I have removed the task!" + UIManager.LS +
                    UIManager.INDENT_TWO_TABS + task + UIManager.LS + UIManager.INDENT_ONE_TAB +
                    "Now you have " + taskList.size() + " task(s) in the list!";
        } catch (DukeException dukeException) {
            returnMessage = UIManager.INDENT_ONE_TAB + dukeException.getMessage();
        }
        return returnMessage;
    }
}