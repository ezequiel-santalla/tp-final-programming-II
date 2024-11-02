package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utilities {

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


    // Metodo para formatear LocalDate a String en formato dd/MM/yyyy
    public static String formatLocalDate(LocalDate date) {
        if (date == null) {
            return null; // O puedes lanzar una excepción si prefieres
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    // Metodo para convertir String en formato dd/MM/yyyy a LocalDate
    public static LocalDate parseLocalDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            // Maneja la excepción si el formato es incorrecto
            System.out.println("Fecha no válida: " + dateString);
            return null;
        }
    }
    public static boolean isValidDateFormat(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return false; // Retorna false si el String es nulo o vacío
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            // Intenta parsear el String. Si se puede, el formato es válido.
            LocalDate date = LocalDate.parse(dateString, formatter);
            return true; // Formato válido
        } catch (DateTimeParseException e) {
            return false; // Formato inválido
        }
    }
}
