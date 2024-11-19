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
        return input != null && input.matches("\\d+") && input.length() < 10;
    }

    public static boolean isValidateDni(String dni){
       return isNumericString(dni) && dni.length()==8 && dni.charAt(0)!='0';
    }

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-Z ]+$";

        return name.matches(regex);
    }

    public static String toFormatName(String name){
        return name.substring(0,1).toUpperCase()+name.substring(1).toLowerCase();
    }

    public static String formatLocalDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(formatter);
    }

    public static LocalDate parseLocalDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static boolean isValidDateFormat(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static Integer random(Integer min, Integer max) {
        return random.nextInt(max - min) + min;
    }

    public static boolean isValidSurface(String surface) {
        if (surface == null || surface.isEmpty()) {
            return false;
        }
        try {
            ESurface.valueOf(surface);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    public static boolean validateFullScore(Integer playerOneScore, Integer playerTwoScore) throws InvalidResultException {
        if (playerOneScore < 0 || playerTwoScore < 0) {
            throw new InvalidResultException("Score cannot be negative");
        }

        boolean playerOneWinsWithSix = playerOneScore == 6 && playerTwoScore <= 4;
        boolean playerTwoWinsWithSix = playerTwoScore == 6 && playerOneScore <= 4;

        boolean playerOneWinsWithSeven = playerOneScore == 7 && (playerTwoScore == 5 || playerTwoScore == 6);
        boolean playerTwoWinsWithSeven = playerTwoScore == 7 && (playerOneScore == 5 || playerOneScore == 6);

        return playerOneWinsWithSix || playerTwoWinsWithSix || playerOneWinsWithSeven || playerTwoWinsWithSeven;
    }

    public static boolean validatePartialScore(Integer score) {
        return score >= 0 && score <= 7;
    }
}
