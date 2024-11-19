package view;

import enums.ESurface;
import exceptions.DataEntryCancelledException;
import model.Player;
import model.Tournament;
import utils.Utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private List<String> menuOptions;
    private final StringBuilder textToPrint = new StringBuilder();
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu(List<String> menuOptions, String headerName) {

        printMenuHeader(headerName);
        for (int i = 0; i < menuOptions.size() - 1; i++) {
            System.out.printf("| %2d - %-31s |\n", i + 1, menuOptions.get(i));
        }
        System.out.printf("| %2d - %-31s |\n", 0, menuOptions.getLast());

        System.out.println("----------------------------------------");
        System.out.print("\nSeleccione una opción: ");
    }

    public void printMenuHeader(String header) {
        int totalWidth = 40; // Ancho total incluyendo los bordes
        int contentWidth = totalWidth - 2; // Espacio entre los bordes
        int padding = (contentWidth - header.length()) / 2; // Espacios a la izquierda y derecha
        String spacesLeft = " ".repeat(Math.max(0, padding)); // Espacios a la izquierda
        String spacesRight = " ".repeat(Math.max(0, contentWidth - header.length() - padding)); // Ajuste a la derecha

        System.out.println("\n" + "-".repeat(totalWidth)); // Línea superior
        System.out.println("|" + spacesLeft + header + spacesRight + "|"); // Línea con texto centrado
        System.out.println("-".repeat(totalWidth)); // Línea inferior
    }



    public Integer requestEntry(List<String> menuOptions, String headerTittle) {
        String selectedIndex;
        Integer index = -1;
        boolean isValid = false;

        do {
            showMenu(menuOptions, headerTittle);
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

    public Integer requestID(String dataMessage) throws DataEntryCancelledException {
        String dataInput;
        boolean flag = false;

        do {
            System.out.print("\nIngrese el ID " + dataMessage + " (o '0' para cancelar): ");
            dataInput = scanner.nextLine();
            if (dataInput.equals("0")) {
                throw new DataEntryCancelledException();
            }
            if (Utils.isNumericString(dataInput)) {
                flag = true;
            } else {
                System.out.println("El ID no es válido");
            }
        } while (!flag);
        return Integer.parseInt(dataInput);
    }

    public String requestDni() throws DataEntryCancelledException {
        String dataInput;
        boolean flag = false;

        do {
            System.out.print("Ingrese el DNI (o '0' para cancelar): ");
            dataInput = scanner.nextLine();
            if (dataInput.equals("0")) {
                throw new DataEntryCancelledException();
            }
            if (Utils.isValidateDni(dataInput)) {
                flag = true;
            } else {
                System.out.println("El DNI no es válido");
            }
        } while (!flag);
        return dataInput;
    }

    public String requestAlphabeticInput(String dataMessage) throws DataEntryCancelledException {
        String dataInput;
        boolean flag = false;
        do {
            System.out.print("Ingrese " + dataMessage + " (o '0' para cancelar): ");
            dataInput = scanner.nextLine();
            if (dataInput.equals("0")) {
                throw new DataEntryCancelledException();
            }
            if (Utils.isValidName(dataInput)) {
                flag = true;
            } else {
                System.out.println("El dato ingresado no es válido");
            }
        } while (!flag);
        return dataInput;
    }

    public LocalDate requestDate(String dataMessage) throws DataEntryCancelledException {
        String dataInput;
        boolean flag = false;
        do {
            System.out.print("Ingrese la fecha " + dataMessage + " <dd/MM/aaaa> (o '0' para cancelar): ");
            dataInput = scanner.nextLine();
            if (dataInput.equals("0")) {
                throw new DataEntryCancelledException();
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

    private LocalDate requestEndingDate(LocalDate startingDate) throws DataEntryCancelledException {
        LocalDate endingDate = null;
        boolean flag = false;
        while (!flag) {
            endingDate = requestDate("la fecha de finalización del torneo");
            if (endingDate == null) {
                flag = true;
                System.out.println("Carga de datos cancelada.");
            }
            if (startingDate.isAfter(endingDate)) {
                System.out.println("La fecha de finalización debe ser posterior a la fecha de inicio.");
            } else {
                flag = true;
            }
        }
        return endingDate;
    }

    public Player requestPlayerData() throws DataEntryCancelledException {
        Player player = new Player();

        String dni = requestDni();
        if (dni == null) {
            throw new DataEntryCancelledException();
        }
        player.setDni(dni);

        String name = requestAlphabeticInput("el nombre");
        if (name == null) {
            throw new DataEntryCancelledException();
        }
        player.setName(Utils.toFormatName(name));

        String lastName = requestAlphabeticInput("el apellido");
        if (lastName == null) {
            throw new DataEntryCancelledException();
        }
        player.setLastName(Utils.toFormatName(lastName));

        String nationality = requestAlphabeticInput("la nacionalidad");
        if (nationality == null) {
            throw new DataEntryCancelledException();
        }
        player.setNationality(Utils.toFormatName(nationality));

        LocalDate dateOfBirth = requestDate("de nacimiento");
        if (dateOfBirth == null) {
            throw new DataEntryCancelledException();
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


    public Tournament requestTournamentData(Tournament tournament) throws DataEntryCancelledException {
        if (tournament == null) {
            tournament = new Tournament(); // Crear un nuevo torneo si es null
        }

        tournament.setName(requestStringInput("el nombre del torneo"));
        tournament.setLocation(requestStringInput("la ubicación del torneo"));
        tournament.setStartingDate(requestDate("la fecha del torneo"));
        tournament.setEndingDate(requestEndingDate(tournament.getStartingDate()));
        tournament.setSurface(requestSurface());

        return tournament;
    }

    private String requestStringInput(String prompt) throws DataEntryCancelledException {
        String input = requestAlphabeticInput(prompt);
        if (input == null) {
            throw new DataEntryCancelledException();
        }
        return Utils.toFormatName(input);
    }


    private ESurface requestSurface() throws DataEntryCancelledException {
        String dataInput;
        while (true) {
            System.out.print("Ingrese la superficie del torneo (CARPET, CLAY, GRASS, HARD) o '0' para cancelar: ");
            dataInput = scanner.nextLine().trim();

            if (dataInput.equals("0")) {
                throw new DataEntryCancelledException();
            }

            if (Utils.isValidSurface(dataInput)) {
                return ESurface.valueOf(dataInput.toUpperCase()); // Devuelve la superficie válida
            } else {
                System.out.println("La superficie ingresada no es válida. Intente nuevamente.");
            }
        }
    }

    public Integer requestPlayerScore(String playerName) {
        Integer score = null;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Ingrese el puntaje del Jugador " + playerName + ": ");
                score = Integer.parseInt(scanner.nextLine());
                if (Utils.validatePartialScore(score)) {
                    validInput = true;
                } else {
                    System.out.println("El puntaje no es valido. Intenta nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa un numero válido para el puntaje.");
            }
        }
        return score;
    }

    public void requestPressEnter() {
        System.out.print("\nPresione <<Enter>> para continuar...");
        scanner.nextLine();
        cleanScreen();
    }

    public void cleanScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

}