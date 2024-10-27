package service;

import exceptions.FileProcessingException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PersistenceFile {

    private void ensureFileExists(String filePath) throws FileProcessingException {
        File file = new File(filePath);
        if (!file.exists()) {
            createFile(filePath);
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                if (br.readLine() == null) {  // Si el archivo está vacío
                    try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                        pw.print("[]");  // Escribe el array vacío
                    }
                }
            } catch (IOException e) {
                throw new FileProcessingException("Error al asegurar el contenido del archivo: " + filePath);
            }
        }
    }

    public void createFile(String filePath) throws FileProcessingException {
        File file = new File(filePath);
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.print("[]"); // Escribe el array vacío
            pw.close();
        } catch (FileNotFoundException e) {
            throw new FileProcessingException("Error al crear el archivo: " + filePath);
        }
    }

    public void writeFile(String filePath, String data) throws FileProcessingException {
        ensureFileExists(filePath);
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, false))) {
            pw.println(data);
        } catch (IOException e) {
            throw new FileProcessingException("Error al sobreescribir el archivo: " + filePath);
        }
    }


    public void addDataToFile(String filePath, String data) throws FileProcessingException {
        ensureFileExists(filePath);
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
            pw.println(data);
        } catch (IOException e) {
            throw new FileProcessingException("Error al agregar datos al archivo: " + filePath);
        }
    }

    public String readFile(String filePath) throws FileProcessingException {
        ensureFileExists(filePath);
        StringBuilder contentFile = new StringBuilder();
        try (BufferedReader buff = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = buff.readLine()) != null) {
                contentFile.append(currentLine).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new FileProcessingException("Error al leer el archivo: " + filePath);
        }
        return contentFile.toString();
    }

    public void saveUnserializableContent(String fileName, String invalidJsonString) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filePath = "data/backUp/" + timestamp + "_" + fileName + ".json";
        File backupDir = new File("data/backUp/");
        if (!backupDir.exists()) {
            backupDir.mkdirs(); // Crea el directorio y cualquier directorio padre necesario
        } try {
            writeFile(filePath, invalidJsonString);
        } catch (FileProcessingException e){
            throw new FileProcessingException("No se pudo guardar la copia del archivo inválido. "+e.getMessage());
        }
    }
}
