package duke.manager;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;
import duke.util.Formatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class IOManager {
    private static final String FILE_DIR = "data";
    private static final String FILE_PATH = "data/data.txt";

    // Save all the tasks in the task list into a txt file
    // Param: List<Task> taskList, the List of task to be saved from
    public static void saveTaskList(List<Task> taskList) {
        // Create a File for the given file path
        File fileDir;
        FileWriter writer;

        fileDir = new File(FILE_DIR);

        // If the directory does not exist, create it
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        // Try to create a file if it does not exist
        // Write all the task into the file
        try {
            writer = new FileWriter(FILE_PATH);
            for (Task task : taskList) {
                writer.write(task.getTaskType() + Formatter.FILE_DELIMITER + task.getIsDone() + Formatter.FILE_DELIMITER
                        + task.getDescription() + Formatter.FILE_DELIMITER + task.getDateTime() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // Load the saved task list from the txt file
    // Return: String, a reply message for the method operation
    // Param: List<task> taskList, the List of task to be loaded into
    public static String loadTaskList(List<Task> taskList) {
        // Create a File for the given file path
        File file = new File(FILE_PATH);
        String returnMessage = "";

        // Create a Scanner using the File as the source
        try {
            returnMessage = returnMessage.concat("Looking for existing file..." + System.lineSeparator());
            Scanner s = new Scanner(file);

            returnMessage = returnMessage.concat("Found file, loading saved info..." + System.lineSeparator());
            String[] taskInfos;
            String taskType;
            boolean taskIsDone;
            String taskDescription;
            String taskDateTime;

            while (s.hasNext()) {
                taskInfos = s.nextLine().split("\\|");

                taskType = taskInfos[0];
                taskIsDone = Boolean.parseBoolean(taskInfos[1]);
                taskDescription = taskInfos[2];

                Task task;

                switch(taskType) {
                case Todo.TASK_TYPE:
                    task = new Todo(taskDescription);
                    task.setIsDone(taskIsDone);
                    break;
                case Event.TASK_TYPE:
                    taskDateTime = taskInfos[3];
                    task = new Event(taskDescription, taskDateTime);
                    task.setIsDone(taskIsDone);
                    break;
                case Deadline.TASK_TYPE:
                    taskDateTime = taskInfos[3];
                    task = new Deadline(taskDescription, taskDateTime);
                    task.setIsDone(taskIsDone);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + taskType);
                }
                taskList.add(task);
            }
            returnMessage = returnMessage.concat("Successfully loaded saved info!");
        } catch (FileNotFoundException exception) {
            returnMessage = returnMessage.concat("No saved file found!");
        } catch (IllegalStateException exception) {
            returnMessage = returnMessage.concat("Unable to load saved info, file may be corrupted!");
        }
        return returnMessage;
    }
}