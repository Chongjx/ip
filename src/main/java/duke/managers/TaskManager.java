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

    /** Output message for the operations. */
    private String taskOutputMessage;

    /** UIManager handler. */
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
     * @param commandType Type of command to execute.
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
        case COMMAND_FIND:
            findTask(taskInfo);
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
     * Adds new task into the task list.
     *
     * @param message The string of the original message. To be parsed and retrieve the task description and date time
     *                info for Event and Deadline task.
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
     * Lists the events and deadlines that are on the same date.
     *
     * @param message The string of the original message. To be parsed and get the keyword.
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
     * Marks a task as done based on the index that the user entered.
     *
     * @param taskIndexString The string of the original message.  To be parsed and retrieve the index as int.
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
     * Deletes a task based on the index that the user entered.
     *
     * @param taskIndexString The string of the original message. To be parsed and retrieve the index as int.
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

    /**
     * Lists all the task(s) in the taskList that contain the keyword.
     *
     * @param message The string of the original message. To be parsed and retrieve the keyword.
     */
    private void findTask(String[] message) {
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
                taskOutputMessage = UIManager.INDENT_ONE_TAB + "Found these task(s):";
                int index = 1;
                for (Task i : foundTasks) {
                    taskOutputMessage = taskOutputMessage.concat(UIManager.LS + UIManager.INDENT_TWO_TABS + index + "." + i);
                    ++index;
                }
            }

        } catch (DukeException dukeException) {
            taskOutputMessage = UIManager.INDENT_ONE_TAB + dukeException.getMessage();
        }
        uiManager.prints(taskOutputMessage);
    }
}