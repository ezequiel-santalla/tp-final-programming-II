package service;

import java.io.*;

public class PersistenceFile {

    public void createFile(String filePath) {
        File file = new File(filePath);
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeFile(String filePath, String data) {
        File file = new File(filePath);
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.println(data);
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addDataToFile(String filePath, String data) throws FileNotFoundException, IOException{
        File file = new File(filePath);
            try(PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
                pw.println(data);
            }
    }

    public String readFile(String filePath){
        StringBuilder contentFile = new StringBuilder();
        File file = new File(filePath);

        try {
            BufferedReader buff = new BufferedReader(new FileReader(file));
            String currentLine = buff.readLine();
            while(currentLine!=null){
                contentFile.append(currentLine);
                currentLine = buff.readLine();
            }
            buff.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contentFile.toString();
    }

}
