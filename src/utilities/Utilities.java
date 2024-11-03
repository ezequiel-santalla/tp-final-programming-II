package utilities;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utilities {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Utilities() {
    }

    public static boolean isNumericString(String input) {
        return input != null && input.matches("\\d+");
    }

    public static boolean isValidateDni(String dni){
       return isNumericString(dni) && dni.length()==8 && dni.charAt(0)!='0';
    }

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;  // El nombre no debe estar vacío ni ser nulo
        }
        String regex = "^[a-zA-Z ]+$";

        return name.matches(regex);
    }

    public static String toFormatName(String name){
        return name.substring(0,1).toUpperCase()+name.substring(1).toLowerCase();
    }


    // formatea LocalDate a String en formato dd/MM/yyyy
    public static String formatLocalDate(LocalDate date) {
        if (date == null) {
            return null; // O puedes lanzar una excepción si prefieres
        }
        return date.format(formatter);
    }

    // convierte String en formato dd/MM/yyyy a LocalDate
    public static LocalDate parseLocalDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            // Maneja la excepción si el formato es incorrecto
            return null;
        }
    }

    public static boolean isValidDateFormat(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return false; // Retorna false si el String es nulo o vacío
        }
        try {
            // Intenta parsear el String. Si se puede, el formato es válido.
            LocalDate.parse(dateString, formatter);
            return true; // Formato válido
        } catch (DateTimeParseException e) {
            return false; // Formato inválido
        }
    }
    public static void cleanConsole(){
        try {
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getRandomNumber() {
        return (int) (Math.random() * 3) + 1; // Genera un número entre 1 y 3
    }
}
