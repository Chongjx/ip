package duke.managers;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Manages the saving and loading of task list data.
 */
public class IOManager {
    /** Default file path if the user doesn't provide the file name. */
    private static final String FILE_DIR = "data";
    private static final String FILE_PATH = "data/data.txt";
    private static final char FILE_STRING_DELIMITER = '|';

    private final UIManager uiManager;

    public IOManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    /**
     * Saves all the task(s) in the task list into a txt file.
     * Creates the folder and txt file if they are missing.
     *
     * @param taskList A list of Task.
     */
    public void saveTaskList(List<Task> taskList) {
        // Create a File for the given file path
        File fileDir;
        FileWriter writer;

        fileDir = new File(FILE_DIR);

        // If the directory does not exist, create it
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        // Save all the tasks in the task list into a text file
        try {
            writer = new FileWriter(FILE_PATH);
            for (Task task : taskList) {
                writer.write(task.getTaskType() + FILE_STRING_DELIMITER + task.getIsDone() + FILE_STRING_DELIMITER
                        + task.getDescription() + FILE_STRING_DELIMITER + task.getDateTime() + UIManager.LS);
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
     */
    public void loadTaskList(List<Task> taskList) {
        // Create a File for the given file path
        File file = new File(FILE_PATH);
        String loadTaskMessage = "";

        // Load the saved data
        try {
            loadTaskMessage = loadTaskMessage.concat(UIManager.INDENT_ONE_TAB + "Looking for existing file..." +
                    UIManager.LS);
            Scanner s = new Scanner(file);

            loadTaskMessage = loadTaskMessage.concat(UIManager.INDENT_ONE_TAB + "Found file, loading saved info..." +
                    UIManager.LS);
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

                // Create the respective task and add into taskList
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
                    throw new IllegalStateException(UIManager.INDENT_ONE_TAB + "Unexpected value: " + taskType);
                }
                taskList.add(task);
            }
            loadTaskMessage = loadTaskMessage.concat(UIManager.INDENT_ONE_TAB + "Successfully loaded saved info!");
        } catch (FileNotFoundException exception) {
            loadTaskMessage = loadTaskMessage.concat(UIManager.INDENT_ONE_TAB + "No saved file found!");
        } catch (IllegalStateException exception) {
            loadTaskMessage = loadTaskMessage.concat(UIManager.INDENT_ONE_TAB + "Unable to load saved info, file may be corrupted!");
        }
        uiManager.prints(loadTaskMessage);
    }
}