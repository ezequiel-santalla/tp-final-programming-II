package utils;

import enums.ESurface;
import exceptions.InvalidResultException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;

public class Utils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Random random = new Random();
    private Utils() {
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

    public static Integer random(Integer min, Integer max) {
        return random.nextInt(max - min) + min; // Genera un número entre 0 (incluido) y 4 (excluido)
    }

    public static boolean isValidSurface(String surface) {
        if (surface == null || surface.isEmpty()) {
            return false;
        }
        try {
            ESurface.valueOf(surface.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    public static boolean validateFullScore(Integer playerOneScore, Integer playerTwoScore) throws InvalidResultException {

        // Validate than scores are not negative
        if (playerOneScore < 0 || playerTwoScore < 0) {
            throw new InvalidResultException("Score cannot be negative");
        }

        // Conditions to validate score
        boolean playerOneWinsWithSix = playerOneScore == 6 && playerTwoScore <= 4;
        boolean playerTwoWinsWithSix = playerTwoScore == 6 && playerOneScore <= 4;

        boolean playerOneWinsWithSeven = playerOneScore == 7 && (playerTwoScore == 5 || playerTwoScore == 6);
        boolean playerTwoWinsWithSeven = playerTwoScore == 7 && (playerOneScore == 5 || playerOneScore == 6);

        // Returns true if any of the win conditions are valid
        return playerOneWinsWithSix || playerTwoWinsWithSix || playerOneWinsWithSeven || playerTwoWinsWithSeven;
    }

    public static boolean validatePartialScore(Integer score) {
        return score >= 0 && score <= 7;
    }
}
