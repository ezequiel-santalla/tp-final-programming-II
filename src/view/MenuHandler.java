package view;

import model.Player;
import utilities.Utilities;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private List<String> menuOptions;
    private final StringBuilder textToPrint = new StringBuilder();
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        textToPrint.setLength(0);
        textToPrint.append("\n\n");
        for (int i = 0; i < menuOptions.size(); i++) {
            textToPrint.append("\n").append(i + 1).append(" - ").append(menuOptions.get(i));
        }
        textToPrint.append("\n0 - Salir");
        textToPrint.append("\n\nIngrese el número de la opción elegida: ");
        System.out.println(textToPrint);
    }


    public Integer requestEntry(List<String> menuOptions) {
        this.menuOptions = menuOptions;
        String selectedIndex;
        Integer index = -1;
        boolean isAValidNumber = false;

        do {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            showMenu();
            selectedIndex = scanner.nextLine();
            if (Utilities.isNumericString(selectedIndex)) {
                index = Integer.parseInt(selectedIndex);
                if (index <= (menuOptions.size()) && index >= 0)
                    isAValidNumber = true;
            }
            if (!isAValidNumber) {
                System.out.println("No ingresó un número válido");
                System.out.println("Presione <ENTER> para reintentar...");
                scanner.nextLine();
            }

        } while (!isAValidNumber && index != 0);
        return index;
    }

    private String requestDni() {
        String dataInput;
        boolean flag = false;

        do {
            System.out.println("Ingrese el dni: ");
            dataInput = scanner.nextLine();
            if (Utilities.isValidateDni(dataInput)) {
                flag = true;
            } else {
                System.out.println("El dni no es válido");
            }
        } while (!flag);
        return dataInput;
    }

    private String requestAlphabeticInput(String dataMessage) {
        String dataInput;
        boolean flag = false;
        do {
            System.out.println("Ingrese " + dataMessage + ": ");
            dataInput = scanner.nextLine();
            if (Utilities.isValidName(dataInput)) {
                flag = true;
            } else {
                System.out.println("El dato ingresado no es válido");
            }
        } while (!flag);
        return dataInput;
    }

    private LocalDate requestDate(String dataMessage){
        String dataInput;
        boolean flag = false;
        do {
            System.out.println("Ingrese la fecha "+dataMessage+" <dd/MM/aaaa>: ");
            dataInput = scanner.nextLine();
            if (Utilities.isValidDateFormat(dataInput)) {
                flag = true;
            } else {
                System.out.println("No es una fecha válida");
            }
        } while (!flag);
        return Utilities.parseLocalDate(dataInput);
    }


    public Player requestPlayerData() {
        Player player = new Player();
        player.setDni(requestDni());
        player.setName(Utilities.toFormatName(requestAlphabeticInput("el nombre")));
        player.setLastName(Utilities.toFormatName(requestAlphabeticInput("el apellido")));
        player.setNationality(Utilities.toFormatName(requestAlphabeticInput("la nacionalidad")));
        player.setDateOfBirth(requestDate("de nacimiento"));

        return player;
    }
}