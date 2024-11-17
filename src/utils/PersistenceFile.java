package utils;

import exceptions.FileProcessingException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PersistenceFile {

    private PersistenceFile() {
    }

    private static void ensureFileExists(String filePath) throws FileProcessingException {
        File file = new File(filePath);
        if (!file.exists()) {
            createFile(filePath);
        } else {
            if (file.length() == 0) {  // Verifica si el archivo está vacío
                try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                    pw.print("[]");  // Escribe un arreglo vacío
                } catch (IOException e) {
                    throw new FileProcessingException("Error securing the file content: " + filePath);
                }
            }
        }
    }

    public static void createFile(String filePath) throws FileProcessingException {
        File file = new File(filePath);
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.print("[]"); // Writes the empty array
            pw.close();
        } catch (FileNotFoundException e) {
            throw new FileProcessingException("Error creating the file: " + filePath);
        }
    }

    public static void writeFile(String filePath, String data) throws FileProcessingException {
        ensureFileExists(filePath);
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, false))) {
            pw.println(data);
        } catch (IOException e) {
            throw new FileProcessingException("Error overwriting the file: " + filePath);
        }
    }


    public static String readFile(String filePath) throws FileProcessingException {
        ensureFileExists(filePath);
        StringBuilder contentFile = new StringBuilder();
        try (BufferedReader buff = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = buff.readLine()) != null) {
                contentFile.append(currentLine).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new FileProcessingException("Error reading the file: " + filePath);
        }
        return contentFile.toString();
    }

    public static void saveUnserializableContent(String fileName, String invalidJsonString) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filePath = "data/backUp/" + timestamp + "_" + fileName.toLowerCase() + ".json";
        File backupDir = new File("data/backUp/");
        if (!backupDir.exists()) {
            boolean dirCreated = backupDir.mkdirs(); // Crea el directorio y cualquier directorio padre necesario.
            if (!dirCreated) {
                throw new FileProcessingException("Failed to create backup directory.");
            }
        }
        try {
            writeFile(filePath, invalidJsonString);
        } catch (FileProcessingException e) {
            throw new FileProcessingException("Could not save the copy of the invalid file. " + e.getMessage());
        }
    }
}
