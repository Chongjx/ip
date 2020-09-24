package duke.managers;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.util.DukeException;
import duke.util.Parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the taskList as well as handle the operations prompt by the user. E.g. Add task, Delete task etc.
 */
public class TaskManager {
    /**
     * Types of command.
     */
    public enum CommandType {
        COMMAND_ADD_TODO,
        COMMAND_ADD_EVENT,
        COMMAND_ADD_DEADLINE,
        COMMAND_LIST,
        COMMAND_ON,
        COMMAND_MARK_DONE,
        COMMAND_DELETE,
        COMMAND_FIND,
        COMMAND_EXIT,
        COMMAND_UNRECOGNIZED,
        COMMAND_MISSING,
        COMMAND_INIT
    }

    /** User task list. */
    private final List<Task> taskList;

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
     * Executes the function for the respective commands and returns the result of the command operation.
     *
     * @param commandType Type of command to execute.
     * @param taskInfo Task information for the command.
     * @return Result of the operation.
     */
    public String handleTask(CommandType commandType, String[] taskInfo) {
        String taskOutputMessage;

        switch (commandType) {
        case COMMAND_ADD_TODO:
        case COMMAND_ADD_EVENT:
        case COMMAND_ADD_DEADLINE:
            taskOutputMessage = addTask(taskInfo, commandType);
            break;
        case COMMAND_LIST:
            taskOutputMessage = listTask();
            break;
        case COMMAND_ON:
            taskOutputMessage = listTaskOnDate(taskInfo);
            break;
        case COMMAND_MARK_DONE:
            taskOutputMessage = markTaskDone(taskInfo);
            break;
        case COMMAND_DELETE:
            taskOutputMessage = deleteTask(taskInfo);
            break;
        case COMMAND_FIND:
            taskOutputMessage = findTask(taskInfo);
            break;
        case COMMAND_UNRECOGNIZED:
        case COMMAND_MISSING:
        case COMMAND_INIT:
        case COMMAND_EXIT:
            taskOutputMessage = getCommandDefaultMessage(commandType);
            break;
        default:
            taskOutputMessage = "";
            break;
        }
        return taskOutputMessage;
    }

    /**
     * Returns the default message of respective commands.
     *
     * @param command Type of command.
     * @return Default command message.
     */
    public String getCommandDefaultMessage(TaskManager.CommandType command) {
        String defaultMessage;
        switch (command) {
        case COMMAND_INIT:
            defaultMessage = UIManager.INDENT_ONE_TAB + "Hello! I'm Jay. Today is " + DateTimeManager.getDate() + ", " +
                    DateTimeManager.getDay() + ". The time now is " + DateTimeManager.getTime() + "." + UIManager.LS +
                    UIManager.INDENT_ONE_TAB + "What can I do for you?";
            break;
        case COMMAND_EXIT:
            defaultMessage = UIManager.INDENT_ONE_TAB + "Bye! Hope to see you again soon!";
            break;
        case COMMAND_MISSING:
            defaultMessage = UIManager.INDENT_ONE_TAB + "You did not enter anything, did you?";
            break;
        case COMMAND_UNRECOGNIZED:
            defaultMessage = UIManager.INDENT_ONE_TAB + "Sorry I don't know what that means... >.<";
            break;
        default:
            defaultMessage = "";
        }
        return defaultMessage;
    }

    /**
     * Adds new task into the task list and returns the result of the operation.
     *
     * @param message The string of the original message. To be parsed and retrieve the task description and date time
     *                info for Event and Deadline task.
     * @param addTaskType The type of task to be created.
     * @return Result of the operation.
     */
    private String addTask(String[] message, CommandType addTaskType) {
        String[] taskInfo;
        LocalDateTime dateTimeInfo;
        String taskOutputMessage;
        try {
            Task newTask = null;
            taskOutputMessage = UIManager.INDENT_ONE_TAB + "Added ";

            // Create new task object based on the command type
            switch (addTaskType) {
            case COMMAND_ADD_TODO:
                new Parser().parseTaskInfo(message, "");
                newTask = new Todo(message[1]);
                taskOutputMessage = taskOutputMessage.concat("a todo task ");
                break;
            case COMMAND_ADD_EVENT:
                taskInfo = new Parser().parseTaskInfo(message, Event.IDENTIFIER);
                dateTimeInfo = new Parser().parseDateTime(taskInfo[1]);

                newTask = new Event(taskInfo[0], dateTimeInfo);
                taskOutputMessage = taskOutputMessage.concat("an event ");
                break;
            case COMMAND_ADD_DEADLINE:
                taskInfo = new Parser().parseTaskInfo(message, Deadline.IDENTIFIER);
                dateTimeInfo = new Parser().parseDateTime(taskInfo[1]);

                newTask = new Deadline(taskInfo[0], dateTimeInfo);
                taskOutputMessage = taskOutputMessage.concat("a deadline task ");
                break;
            }
            taskList.add(newTask);
            // print out the newly added task
            taskOutputMessage = taskOutputMessage.concat(newTask + "!" + UIManager.LS + UIManager.INDENT_TWO_TABS +
                    "Now you have " + taskList.size() + " task(s) in the list!");
        } catch (DukeException dukeException) {
            taskOutputMessage = UIManager.INDENT_ONE_TAB + dukeException.getMessage();
        }
        return taskOutputMessage;
    }

    /**
     * Generates a list of all the task(s) in the taskList and returns the result of the operation.
     *
     * @return Result of the operation.
     */
    private String listTask() {
        String taskOutputMessage;
        // print out default message if there are no task, else print the list of tasks
        if (taskList.size() == 0) {
            taskOutputMessage = UIManager.INDENT_ONE_TAB + "There are currently no task in the list! (*^▽^*)";
        } else {
            taskOutputMessage = UIManager.INDENT_ONE_TAB + "Here is your list of task(s):";
            int index = 1;
            for (Task i : taskList) {
                taskOutputMessage = taskOutputMessage.concat(UIManager.LS + UIManager.INDENT_TWO_TABS + index + "." + i);
                ++index;
            }
        }
        return taskOutputMessage;
    }

    /**
     * Generates a list of events and deadlines that are on the same date and returns the result of the operation.
     *
     * @param message The string of the original message. To be parsed and get the keyword.
     * @return Result of the operation.
     */
    private String listTaskOnDate(String[] message) {
        String taskOutputMessage;
        if (taskList.size() == 0) {
            taskOutputMessage = UIManager.INDENT_ONE_TAB + "There are no event or deadline on that date!";
        } else {
            LocalDate taskDate;
            ArrayList<Task> tasksOnDate = new ArrayList<>();
            try {
                // Verify the input info
                taskDate = new Parser().parseDate(message);

                for (Task i : taskList) {
                    if (!i.getTaskType().equals(Todo.TASK_TYPE) && i.getDateTime().toLocalDate().isEqual(taskDate)) {
                            tasksOnDate.add(i);
                        }
                }

                if (tasksOnDate.size() == 0) {
                    taskOutputMessage = UIManager.INDENT_ONE_TAB + "There are no event or deadline on that date!";
                } else {
                    taskOutputMessage =
                            UIManager.INDENT_ONE_TAB + "Here is the list of event(s) and deadline(s) on " +
                                    taskDate.format(DateTimeManager.DATE_FORMAT) + ":";
                    int index = 1;
                    for (Task i : tasksOnDate) {
                        taskOutputMessage = taskOutputMessage.concat(UIManager.LS + UIManager.INDENT_TWO_TABS +
                                index + "." + i);
                        ++index;
                    }
                }
            } catch (DukeException dukeException) {
                taskOutputMessage = UIManager.INDENT_ONE_TAB + dukeException.getMessage();
            }
        }
        return taskOutputMessage;
    }

    /**
     * Marks a task as done based on the index that the user entered and returns the result of the operation.
     *
     * @param taskIndexString The string of the original message.  To be parsed and retrieve the index as int.
     * @return Result of the operation.
     */
    private String markTaskDone(String[] taskIndexString) {
        String taskOutputMessage;
        try {
            int taskIndex = new Parser().parseTaskIndex(taskIndexString, taskList.size() - 1);
            Task task = taskList.get(taskIndex);
            task.setIsDone(true);
            taskOutputMessage = UIManager.INDENT_ONE_TAB + "Completed task " + (taskIndex + 1) + "!" + UIManager.LS +
                    UIManager.INDENT_TWO_TABS + task;
        } catch (DukeException dukeException) {
            taskOutputMessage = UIManager.INDENT_ONE_TAB + dukeException.getMessage();
        }
        return taskOutputMessage;
    }

    /**
     * Deletes a task based on the index that the user entered and returns the result of the operation.
     *
     * @param taskIndexString The string of the original message. To be parsed and retrieve the index as int.
     * @return Result of the operation.
     */
    private String deleteTask(String[] taskIndexString) {
        String taskOutputMessage;
        try {
            int taskIndex = new Parser().parseTaskIndex(taskIndexString, taskList.size() - 1);
            Task task = taskList.get(taskIndex);
            taskList.remove(taskIndex);
            taskOutputMessage = UIManager.INDENT_ONE_TAB + "I have removed the task!" + UIManager.LS +
                    UIManager.INDENT_TWO_TABS + task + UIManager.LS + UIManager.INDENT_ONE_TAB +
                    "Now you have " + taskList.size() + " task(s) in the list!";
        } catch (DukeException dukeException) {
            taskOutputMessage = UIManager.INDENT_ONE_TAB + dukeException.getMessage();
        }
        return taskOutputMessage;
    }

    /**
     * Generates a list of task(s) in the taskList that contain the keyword and returns the result of the
     * operation.
     *
     * @param message The string of the original message. To be parsed and retrieve the keyword.
     * @return Result of the operation.
     */
    private String findTask(String[] message) {
        String taskOutputMessage;
        try {
            String keyword = new Parser().parseKeyword(message);
            ArrayList<Task> foundTasks = new ArrayList<>();

            // add task that contains the keyword into a new list
            for (Task t : taskList) {
                if (t.toString().contains(keyword)) {
                    foundTasks.add(t);
                }
            }

            if (foundTasks.size() == 0) {
                taskOutputMessage = UIManager.INDENT_ONE_TAB + "There are no matched results! (*^▽^*)";
            } else {
                taskOutputMessage = UIManager.INDENT_ONE_TAB + "Found these task(s) that match the keyword \"" +
                        keyword + "\":";
                int index = 1;
                for (Task i : foundTasks) {
                    taskOutputMessage = taskOutputMessage.concat(UIManager.LS + UIManager.INDENT_TWO_TABS + index + "." + i);
                    ++index;
                }
            }

        } catch (DukeException dukeException) {
            taskOutputMessage = UIManager.INDENT_ONE_TAB + dukeException.getMessage();
        }
        return taskOutputMessage;
    }
}