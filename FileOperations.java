import java.io.*;
import java.nio.file.*;

public class FileOperations {

    public static void main(String[] args) {
        String filePath = "example.txt";

        // Write to a file
        writeToFile(filePath, "Hello, World!\nThis is a sample text file.");

        // Read from a file
        String content = readFromFile(filePath);
        System.out.println("File content:\n" + content);

        // Modify (append) the file
        appendToFile(filePath, "\nAppended line.");
        String modifiedContent = readFromFile(filePath);
        System.out.println("Modified file content:\n" + modifiedContent);
    }

    public static void writeToFile(String filePath, String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static void appendToFile(String filePath, String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.APPEND)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
