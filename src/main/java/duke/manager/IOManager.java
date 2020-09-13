package duke.manager;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class IOManager {

    private static final String FILE_PATH = "data/data.txt";

    private static void printFileContents() throws FileNotFoundException {
        // Create a File for the given file path
        File file = new File(FILE_PATH);
        // Create a Scanner using the File as the source
        Scanner s = new Scanner(file);

        while (s.hasNext()) {
            System.out.println(s.nextLine());
        }
    }

    private static void writeToFile(String textToAdd) throws IOException {
        FileWriter fileWriter = new FileWriter(FILE_PATH, true);
        fileWriter.write(textToAdd);
        fileWriter.close();
    }

    public static void main(String[] args) {
        try {
            printFileContents();
        } catch (FileNotFoundException e) {
            System.out.println("File not found, creating file...");
            File file = new File(FILE_PATH);
            // Create the file
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}