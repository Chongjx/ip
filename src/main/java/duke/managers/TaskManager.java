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
 * Stores all the tasks into an ArrayList and manages the adding, listing, deleting and marking task as done operations.
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
        COMMAND_EXIT,
        COMMAND_UNRECOGNIZED,
        COMMAND_MISSING,
        COMMAND_INIT
    }

    /** User task list */
    private final List<Task> taskList;

    /** Output message for the operations */
    private String taskOutputMessage;

    /** Handler for an UIManager */
    private final UIManager uiManager;
    /**
     * Creates a new ArrayList to store the tasks. Holds a uiManager handler.
     */
    public TaskManager(UIManager uiManager) {
        this.uiManager = uiManager;
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
     * Executes the function for the respective commands.
     *
     * @param commandType Type of command to execute
     * @param taskInfo Task information for the command.
     */
    public void handleTask(CommandType commandType, String[] taskInfo) {
        switch (commandType) {
        case COMMAND_ADD_TODO:
        case COMMAND_ADD_EVENT:
        case COMMAND_ADD_DEADLINE:
            addTask(taskInfo, commandType);
            break;
        case COMMAND_LIST:
            listTask();
            break;
        case COMMAND_ON:
            listTaskOnDate(taskInfo);
            break;
        case COMMAND_MARK_DONE:
            markTaskDone(taskInfo);
            break;
        case COMMAND_DELETE:
            deleteTask(taskInfo);
            break;
        case COMMAND_UNRECOGNIZED:
        case COMMAND_MISSING:
        case COMMAND_INIT:
        case COMMAND_EXIT:
            uiManager.printsDefaultMessage(commandType);
            break;
        default:
            break;
        }
    }

    /**
     * Creates new task and add to the task list if the info are valid.
     *
     * @param message The whole string of the original message. To validated and retrieve the description of the task
     *               and date time info for Event and Deadline task.
     * @param addTaskType The type of task to be created.
     */
    private void addTask(String[] message, CommandType addTaskType) {
        String[] taskInfo;
        LocalDateTime dateTimeInfo;
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
        uiManager.prints(taskOutputMessage);
    }

    /**
     * Lists all the task(s) in the taskList.
     */
    private void listTask() {
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
        uiManager.prints(taskOutputMessage);
    }

    /**
     * List the events and deadlines that are on the same date.
     *
     * @param message Contains the date info.
     */
    private void listTaskOnDate(String[] message) {
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
                            UIManager.INDENT_ONE_TAB + "Here is the list of event/deadline(s) on " +
                                    taskDate.format(DateTimeManager.DATE_FORMAT) + ": ";
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
        uiManager.prints(taskOutputMessage);
    }

    /**
     * Prints the task that is marked done by the user.
     *
     * @param taskIndexString The task index in string. To be validated and retrieve the index as int.
     */
    private void markTaskDone(String[] taskIndexString) {
        try {
            int taskIndex = new Parser().parseTaskIndex(taskIndexString, taskList.size() - 1);
            Task task = taskList.get(taskIndex);
            task.setIsDone(true);
            taskOutputMessage = UIManager.INDENT_ONE_TAB + "Completed task " + (taskIndex + 1) + "!" + UIManager.LS +
                    UIManager.INDENT_TWO_TABS + task;
        } catch (DukeException dukeException) {
            taskOutputMessage = UIManager.INDENT_ONE_TAB + dukeException.getMessage();
        }
        uiManager.prints(taskOutputMessage);
    }

    /**
     * Delete task based on the index that the user entered.
     *
     * @param taskIndexString The task index in string. To be validated and retrieve the index as int.
     */
    private void deleteTask(String[] taskIndexString) {
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
        uiManager.prints(taskOutputMessage);
    }
}