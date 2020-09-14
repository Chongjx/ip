package duke.manager;

import duke.Duke;
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
    // Param: List<task> taskList, the List of task to be loaded into
    public static void loadTaskList(List<Task> taskList) {
        // Create a File for the given file path
        File file = new File(FILE_PATH);
        // Create a Scanner using the File as the source
        try {
            Duke.reply("Looking for existing file...");
            Scanner s = new Scanner(file);

            Duke.reply("Found file, loading saved info...");
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
                case "T":
                    task = new Todo(taskDescription);
                    task.setIsDone(taskIsDone);
                    break;
                case "E":
                    taskDateTime = taskInfos[3];
                    task = new Event(taskDescription, taskDateTime);
                    task.setIsDone(taskIsDone);
                    break;
                case "D":
                    taskDateTime = taskInfos[3];
                    task = new Deadline(taskDescription, taskDateTime);
                    task.setIsDone(taskIsDone);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + taskType);
                }
                taskList.add(task);
            }
            Duke.reply("Successfully loaded saved info!");
        } catch (FileNotFoundException exception) {
            Duke.reply("No saved file found!");
        } catch (IllegalStateException exception) {
            Duke.reply("Unable to load saved info, file may be corrupted!");
        }
    }
}