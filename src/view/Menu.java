package view;

import model.Player;
import repository.MatchRepositoryImp;
import repository.PlayerRepositoryImp;
import service.MatchService;
import service.PersistenceFile;
import service.PlayerService;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private final MenuHandler menuHandler;
    private final List<String> principalMenuOptions;
    private final List<String> secondaryMenuOptions;

    //Estos atributos no deberian estar aca, son para probar
    PlayerRepositoryImp playerRepositoryImp = new PlayerRepositoryImp(new PersistenceFile(), "data/player.json");
    MatchService matchService = new MatchService(new MatchRepositoryImp(new PersistenceFile(), "data/match.json"));
    PlayerService playerService = new PlayerService(playerRepositoryImp,matchService);



    public Menu() {
        menuHandler = new MenuHandler();
        principalMenuOptions = new ArrayList<>();
        secondaryMenuOptions = new ArrayList<>();
    }


    public void runMenu() {
        principalMenu();
        Integer index;
        do {
            index = menuHandler.requestEntry(principalMenuOptions);
            switch (index) {
                case 1:
                    tournamentMenu();
                    break;
                case 2:
                    playersMenu();
                    break;
                case 3:
                    matchesMenu();
                    break;
                default:
                    principalMenu();
                    break;
            }
        }
        while (index != 0);
    }


    private void principalMenu() {
        principalMenuOptions.add("Administración del Torneo");
        principalMenuOptions.add("Administración de Jugadores");
        principalMenuOptions.add("Administración de Partidos");
    }

    private void tournamentMenu() {

        secondaryMenuOptions.clear();
        secondaryMenuOptions.add("Cargar datos del torneo");
        secondaryMenuOptions.add("Ver datos del torneo");
        secondaryMenuOptions.add("Modificar datos del torneo");

        Integer index;
        do {
            index = menuHandler.requestEntry(secondaryMenuOptions);
            switch (index) {
                case 1:
                    System.out.println("opcion 1");
                    break;
                case 2:
                    System.out.println("opcion 2");

                    break;
                case 3:
                    System.out.println("opcion 3");
                    break;
                default:
                    break;
            }
        } while (index!=0);
    }

    private void matchesMenu() {

        secondaryMenuOptions.clear();
        secondaryMenuOptions.add("Ver diagrama de partidos");
        secondaryMenuOptions.add("Ver resultado de partidos");
        secondaryMenuOptions.add("Cargar resultado de partido");
        Integer index;
        do {
            index = menuHandler.requestEntry(secondaryMenuOptions);
            switch (index) {
                case 1:
                    System.out.println("opcion 1");
                    break;
                case 2:
                    System.out.println("opcion 2");

                    break;
                case 3:
                    System.out.println("opcion 3");
                    break;
                default:
                    break;
            }
        } while (index!=0);
    }

    private void playersMenu() {

        secondaryMenuOptions.clear();
        secondaryMenuOptions.add("Agregar jugador");
        secondaryMenuOptions.add("Modificar jugador");
        secondaryMenuOptions.add("Ver lista de jugadores");
        secondaryMenuOptions.add("Eliminar jugador");
        Integer index;
        do {
            index = menuHandler.requestEntry(secondaryMenuOptions);
            switch (index) {
                case 1:
                    Player player = menuHandler.requestPlayerData();
                    playerService.addPlayer(player);
                    System.out.println(player);
                    break;
                case 2:
                    System.out.println("opcion 2");

                    break;
                case 3:
                    System.out.println("opcion 3");
                    break;
                default:
                    break;
            }
        } while (index!=0);
    }




}
