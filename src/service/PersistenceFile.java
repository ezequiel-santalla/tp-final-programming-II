package service;

import java.io.*;

public class PersistenceFile {

    private void ensureFileExists(String filePath) {
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
                throw new RuntimeException("Error al asegurar el contenido del archivo: " + filePath, e);
            }
        }
    }

    public void createFile(String filePath) {
        File file = new File(filePath);
        ensureFileExists(filePath);
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.print("[]"); // Escribe el array vacío
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error al crear el archivo: " + filePath,e);
        }
    }
    public void writeFile(String filePath, String data) {
        ensureFileExists(filePath);
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, false))) {
            pw.println(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void addDataToFile(String filePath, String data) {
        ensureFileExists(filePath);
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
            pw.println(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readFile(String filePath) {
        ensureFileExists(filePath);
        StringBuilder contentFile = new StringBuilder();
        try (BufferedReader buff = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = buff.readLine()) != null) {
                contentFile.append(currentLine).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contentFile.toString();
    }

}
