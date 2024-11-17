package view;

import enums.ESurface;
import exceptions.TournamentNotFoundException;
import model.Player;
import model.Tournament;
import service.TournamentService;
import utilities.Utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private List<String> menuOptions;
    private final StringBuilder textToPrint = new StringBuilder();
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu(List<String> menuOptions) {
        System.out.println("\n----------------------------------------");
        System.out.println("|               Menú                   |");
        System.out.println("----------------------------------------");

        for (int i = 0; i < menuOptions.size() - 1; i++) {
            System.out.printf("| %2d - %-31s |\n", i + 1, menuOptions.get(i));
        }
        System.out.printf("| %2d - %-31s |\n", 0, menuOptions.getLast());

        System.out.println("----------------------------------------");
        System.out.print("\nSeleccione una opción: ");
    }


    public Integer requestEntry(List<String> menuOptions) {
        String selectedIndex;
        Integer index = -1;
        boolean isValid = false;

        do {
            showMenu(menuOptions);
            selectedIndex = scanner.nextLine();
            if (Utils.isNumericString(selectedIndex)) {
                index = Integer.parseInt(selectedIndex);
                if (index >= 0 && index < menuOptions.size()) {
                    isValid = true;
                }
            }
            if (!isValid) {
                System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (!isValid);
        return index;
    }

    public Integer requestID() {
        String dataInput;
        boolean flag = false;

        do {
            System.out.print("Ingrese el ID (o '0' para cancelar): ");
            dataInput = scanner.nextLine();
            System.out.println();
            if (dataInput.equals("0")) {
                return null; // Cancela la carga
            }
            if (Utils.isNumericString(dataInput)) {
                flag = true;
            } else {
                System.out.println("El ID no es válido");
            }
        } while (!flag);
        return Integer.parseInt(dataInput);
    }

    public String requestDni() {
        String dataInput;
        boolean flag = false;

        do {
            System.out.print("\nIngrese el dni (o '0' para cancelar): ");
            dataInput = scanner.nextLine();
            if (dataInput.equals("0")) {
                return null; // Cancela la carga
            }
            if (Utils.isValidateDni(dataInput)) {
                flag = true;
            } else {
                System.out.println("El dni no es válido");
            }
        } while (!flag);
        return dataInput;
    }

    public String requestAlphabeticInput(String dataMessage) {
        String dataInput;
        boolean flag = false;
        do {
            System.out.print("Ingrese " + dataMessage + " (o '0' para cancelar): ");
            dataInput = scanner.nextLine();
            if (dataInput.equals("0")) {
                return null; // Cancela la carga
            }
            if (Utils.isValidName(dataInput)) {
                flag = true;
            } else {
                System.out.println("El dato ingresado no es válido");
            }
        } while (!flag);
        return dataInput;
    }

    public LocalDate requestDate(String dataMessage) {
        String dataInput;
        boolean flag = false;
        do {
            System.out.print("Ingrese la fecha " + dataMessage + " <dd/MM/aaaa> (o '0' para cancelar): ");
            dataInput = scanner.nextLine();
            if (dataInput.equals("0")) {
                return null; // Cancela la carga
            }
            if (Utils.isValidDateFormat(dataInput)) {
                flag = true;
            } else {
                System.out.println("No es una fecha válida");
                System.out.println();
            }
        } while (!flag);
        return Utils.parseLocalDate(dataInput);
    }

    public Player requestPlayerData() {
        Player player = new Player();

        String dni = requestDni();
        if (dni == null) {
            System.out.println("Carga de datos cancelada.");
            return null; // Cancela la carga y retorna null
        }
        player.setDni(dni);

        String name = requestAlphabeticInput("el nombre");
        if (name == null) {
            System.out.println("Carga de datos cancelada.");
            return null;
        }
        player.setName(Utils.toFormatName(name));

        String lastName = requestAlphabeticInput("el apellido");
        if (lastName == null) {
            System.out.println("Carga de datos cancelada.");
            return null;
        }
        player.setLastName(Utils.toFormatName(lastName));

        String nationality = requestAlphabeticInput("la nacionalidad");
        if (nationality == null) {
            System.out.println("Carga de datos cancelada.");
            return null;
        }
        player.setNationality(Utils.toFormatName(nationality));

        LocalDate dateOfBirth = requestDate("de nacimiento");
        if (dateOfBirth == null) {
            System.out.println("Carga de datos cancelada.");
            return null;
        }
        player.setDateOfBirth(dateOfBirth);
        player.setPoints(0);

        return player;
    }

    public Boolean requestConfirmation() {
        String dataInput;
        boolean flag = false;

        System.out.print("Ingrese 'S' para confirmar u otra tecla para cancelar: ");
        dataInput = scanner.nextLine();
        if (dataInput.equalsIgnoreCase("s")) {
            flag = true;
        }
        return flag;
    }

    public Tournament requestTournamentData() {
        Tournament tournament = new Tournament();

        String name = requestAlphabeticInput("el nombre del torneo");
        if (name == null) {
            System.out.println("Carga de datos cancelada.");
            return null;
        }
        tournament.setName(Utils.toFormatName(name));

        String location = requestAlphabeticInput("la ubicación del torneo");
        if (location == null) {
            System.out.println("Carga de datos cancelada.");
            return null;
        }
        tournament.setLocation(Utils.toFormatName(location));

        LocalDate date = requestDate("la fecha del torneo");
        if (date == null) {
            System.out.println("Carga de datos cancelada.");
            return null;
        }
        tournament.setStartingDate(date);

        LocalDate endingDate = requestDate("la fecha de finalización del torneo");
        if (endingDate == null) {
            System.out.println("Carga de datos cancelada.");
            return null;
        }
        tournament.setEndingDate(endingDate);

        ESurface surface = requestSurface();
        if (surface == null) {
            System.out.println("Carga de datos cancelada.");
            return null;
        }
        tournament.setSurface(surface);

        return tournament;
    }

    private ESurface requestSurface() {
        String dataInput;
        while (true) {
            System.out.print("Ingrese la superficie del torneo (CARPET, CLAY, GRASS, HARD) o '0' para cancelar: ");
            dataInput = scanner.nextLine().trim();

            if (dataInput.equals("0")) {
                System.out.println("Carga de superficie cancelada.");
                return null; // Cancela la carga
            }

            if (Utils.isValidSurface(dataInput)) {
                return ESurface.valueOf(dataInput.toUpperCase()); // Devuelve la superficie válida
            } else {
                System.out.println("La superficie ingresada no es válida. Intente nuevamente.");
            }
        }
    }

}