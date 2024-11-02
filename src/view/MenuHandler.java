package view;

import model.Player;
import service.Utilities;

import java.util.List;
import java.util.Scanner;

public class MenuHandler {
    private List<String> menuOptions;
    private final StringBuilder textToPrint = new StringBuilder();
    private final Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        textToPrint.setLength(0);
        textToPrint.append("Ingrese el número de la opción elegida\n\n");
        for (int i = 0; i < menuOptions.size(); i++) {
            textToPrint.append("\n").append(i + 1).append(" - ").append(menuOptions.get(i));
        }
        textToPrint.append("\n0 - Salir");
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

        } while (!isAValidNumber&&index!=0);
        return index;
    }

    public Player requestPlayerData(){
        Player player = new Player();
        String dataInput;
        boolean flag = false;

        do {
            System.out.println("Ingrese el dni: ");
            dataInput = scanner.nextLine();
            if (Utilities.isValidateDni(dataInput)) {
                player.setDni(dataInput);
                flag = true;
            } else {
                System.out.println("El dni no es válido");
            }
        } while (!flag);

        flag=false;

        do {
            System.out.println("Ingrese el nombre: ");
            dataInput = scanner.nextLine();
            if (Utilities.isValidName(dataInput)) {
                player.setName(Utilities.toFormatName(dataInput));
                flag = true;
            } else {
                System.out.println("El nombre no es válido");
            }
        } while (!flag);


        flag=false;

        do {
            System.out.println("Ingrese el apellido: ");
            dataInput = scanner.nextLine();
            if (Utilities.isValidName(dataInput)) {
                player.setLastName(Utilities.toFormatName(dataInput));
                flag = true;
            } else {
                System.out.println("El apellido no es válido");
            }
        } while (!flag);

        flag=false;

        do {
            System.out.println("Ingrese la nacionalidad: ");
            dataInput = scanner.nextLine();
            if (Utilities.isValidName(dataInput)) {
                player.setNationality(Utilities.toFormatName(dataInput));
                flag = true;
            } else {
                System.out.println("El dato ingresado no es válido");
            }
        } while (!flag);

        flag=false;


        do {
            System.out.println("Ingrese la fecha de nacimiento <dd/MM/aaaa>: ");
            dataInput = scanner.nextLine();
            if (Utilities.isValidDateFormat(dataInput)) {
                player.setDateOfBirth(Utilities.parseLocalDate(dataInput));
                flag = true;
            } else {
                System.out.println("No es una fecha válida");
            }
        } while (!flag);



        return player;
    }
}