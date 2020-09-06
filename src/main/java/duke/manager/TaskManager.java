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

    public TaskManager() {
        taskList = new ArrayList<>();
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    // Validate the info for adding task
    public String[] validateTaskInfo(String[] message, String identifier) throws DukeException {
        String[] processedString = new String[2];

        try {
            // if the message is empty, throw missing description exception
            if (message[1].equals("")) {
                throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
            } else if (!identifier.equals("")) {
                // split message by identifier,if missing identifier throw missing identifier exception
                if (!message[1].contains(identifier)) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_IDENTIFIER);
                }
                // split message based on identifier
                processedString = message[1].split(identifier, 2);
                // if missing description or datetime, throw respective exception
                if (processedString[0].equals("")) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
                } else if (processedString[1].equals("") || processedString[1].equals(" ")) {
                    throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DATETIME);
                }
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            // extra handling in the the event that the message is empty and cannot be split
            throw new DukeException(DukeException.ExceptionType.EXCEPTION_MISSING_DESCRIPTION);
        }
        return processedString;
    }

    // Add task
    public String addTask(String[] message, String command) {
        String returnMessage;
        String[] taskInfo;
        try {
            Task newTask = null;
            returnMessage = Formatter.formatTwoTabs + "Added ";
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
            returnMessage += newTask + "!" + System.lineSeparator() + Formatter.formatTwoTabs + "Now you have " + taskList.size() +
                    " task(s) in the list";
        } catch (DukeException e) {
            returnMessage = Formatter.formatTwoTabs + e.getMessage();
        }
        return  returnMessage;
    }

    // List task
    public String listTask() {
        String returnMessage;
        // print out default message if there are no task, else print the list of tasks
        if (taskList.size() == 0) {
            returnMessage = Formatter.formatTwoTabs + "There are currently no task in the list! (*^▽^*)";
        } else {
            returnMessage = Formatter.formatTwoTabs + "Here is your list of task(s):";
            int index = 1;
            for (Task i : taskList) {
                returnMessage += System.lineSeparator() + Formatter.formatFourTabs + index + "." + i;
                ++index;
            }
        }
        return returnMessage;
    }

    // Mark tasks that are done
    public String markTaskDone(String[] taskIndexString) {
        String returnMessage;
        // Get the task index to be marked completed
        try {
            int taskIndex = Integer.parseInt(taskIndexString[1]);
            Task task = taskList.get(taskIndex - 1);
            task.setIsDone(true);
            returnMessage = Formatter.formatTwoTabs + "Completed task " + taskIndex + "!" + System.lineSeparator() + Formatter.formatFourTabs + task;
        } catch (NumberFormatException exception) {
            returnMessage = Formatter.formatTwoTabs + "Invalid input, cannot convert to integer!";
        } catch (ArrayIndexOutOfBoundsException exception) {
            returnMessage = Formatter.formatTwoTabs + "You did not enter any value!";
        } catch (IndexOutOfBoundsException exception) {
            returnMessage = Formatter.formatTwoTabs + "Ops, you have entered an invalid task number! You have " + taskList.size() + " task(s)!";
        }
        return returnMessage;
    }
}
