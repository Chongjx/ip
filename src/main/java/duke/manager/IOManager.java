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

/**
 * IOManager to manage saving and loading of data to and fro txt file
 */
public class IOManager {
    /** Static final variables for the file path */
    private static final String FILE_DIR = "data";
    private static final String FILE_PATH = "data/data.txt";

    /**
     * Saves all the task(s) in the task list into a txt file.
     * Creates the folder and txt file if they are missing.
     *
     * @param taskList A list of Task.
     */
    public static void saveTaskList(List<Task> taskList) {
        // Create a File for the given file path
        File fileDir;
        FileWriter writer;

        fileDir = new File(FILE_DIR);

        // If the directory does not exist, create it
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        // Try to create a file if it does not exist, prints stack trace if there is exception
        try {
            writer = new FileWriter(FILE_PATH);
            for (Task task : taskList) { // Write all the task into the txt file
                writer.write(task.getTaskType() + Formatter.FILE_DELIMITER + task.getIsDone() + Formatter.FILE_DELIMITER
                        + task.getDescription() + Formatter.FILE_DELIMITER + task.getDateTime() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Loads the saved data from the txt file into the task list if it exists.
     *
     * @param taskList List of task to be loaded into.
     * @return An outcome message of this method operation.
     */
    public static String loadTaskList(List<Task> taskList) {
        // Create a File for the given file path
        File file = new File(FILE_PATH);
        String returnMessage = "";

        // Load the saved data
        try {
            returnMessage = returnMessage.concat("Looking for existing file..." + System.lineSeparator());
            Scanner s = new Scanner(file);

            returnMessage = returnMessage.concat("Found file, loading saved info..." + System.lineSeparator());
            String[] taskInfos;
            String taskType;
            boolean taskIsDone;
            String taskDescription;
            String taskDateTime;

            // Read all the line till the last line in the file
            while (s.hasNext()) {
                taskInfos = s.nextLine().split("\\|");

                taskType = taskInfos[0];
                taskIsDone = Boolean.parseBoolean(taskInfos[1]);
                taskDescription = taskInfos[2];

                Task task;

                // Create the task and add into taskList
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