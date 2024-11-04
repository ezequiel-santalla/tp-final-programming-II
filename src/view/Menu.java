package view;

import exceptions.PlayerNotFoundException;
import model.Player;
import service.PlayerService;
import service.TournamentService;

import java.util.Arrays;
import java.util.List;

public class Menu {

    private final MenuHandler menuHandler;
    private List<String> principalMenuOptions;
    private List<String> tournamentOptions;
    private List<String> playersOptions;
    private List<String> matchesOptions;
    private final TournamentService tournamentService;
    private final PlayerService playerService;

    public Menu(TournamentService tournamentService, PlayerService playerService) {
        this.tournamentService = tournamentService;
        this.playerService = playerService;
        this.menuHandler = new MenuHandler();
        initMenuOptions();
    }

    private void initMenuOptions() {
        // Menú principal y submenús definidos solo una vez
        principalMenuOptions = Arrays.asList("Administración del Torneo", "Administración de Jugadores", "Administración de Partidos", "Salir");
        tournamentOptions = Arrays.asList("Cargar datos del torneo", "Ver datos del torneo", "Modificar datos del torneo", "Volver");
        playersOptions = Arrays.asList("Agregar jugador", "Modificar jugador", "Ver lista de jugadores", "Ver información de jugador", "Eliminar jugador", "Volver");
        matchesOptions = Arrays.asList("Ver diagrama de partidos", "Ver resultado de partidos", "Cargar resultado de partido", "Volver");
    }

    public void runMenu() {
        int index;
        do {
            index = menuHandler.requestEntry(principalMenuOptions);
            switch (index) {
                case 1 -> tournamentMenu();
                case 2 -> playersMenu();
                case 3 -> matchesMenu();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }
        } while (index != 0);
    }

    private void tournamentMenu() {
        int index;
        do {
            index = menuHandler.requestEntry(tournamentOptions);
            switch (index) {
                case 1 -> {
                    //implementar carga de datos del torneo
                }
                case 2 -> {
                    //implementar ver datos del torneo
                }
                case 3 -> {
                    //implementar modificar datos del torneo
                }
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (index != 0);
    }

    private void playersMenu() {
        int index;
        do {
            index = menuHandler.requestEntry(playersOptions);
            switch (index) {
                case 1 -> {
                    Player player = menuHandler.requestPlayerData();
                    if (player != null) {
                        playerService.addPlayer(player);
                        System.out.println("Jugador agregado correctamente.");
                    }
                }
                case 2 -> { //implementar modificar jugador
                }
                case 3 -> showPlayersList();
                case 4 -> showPlayerData(menuHandler.requestID());
                case 5 -> confirmPlayerDeleted(menuHandler.requestID());
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (index != 0);
    }

    private void matchesMenu() {
        int index;
        do {
            index = menuHandler.requestEntry(matchesOptions);
            switch (index) {
                case 1 -> {
                    //implementar ver diagrama de partidos
                }
                case 2 -> {
                    //implementar ver resultado de partido
                }
                case 3 ->{
                    //implementar cargar resultado de partido
                }
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (index != 0);
    }

    private boolean showPlayerData(Integer playerID) {
        try {
            System.out.println(playerService.findPlayerById(playerID));
            return true;
        } catch (PlayerNotFoundException e) {
            System.out.println("No se encontró jugador con ese id");
            return false;
        }
    }

    private void confirmPlayerDeleted(Integer playerID) {

        if (showPlayerData(playerID)) {
            System.out.println("Se eliminrá jugador");
            if (menuHandler.requestConfirmation()) {
                try {
                    playerService.deletePlayer(playerID);
                    System.out.println("Jugador eliminado");
                } catch (PlayerNotFoundException e) {
                    System.out.println("No se pudo emininar el jugador");
                }
            }
        }
    }


    private void showPlayersList() {
        try {
            for (Player player : playerService.getAllPlayers()) {
                System.out.println("ID: " + player.getIdPlayer() + " - " + player.getName() + " " + player.getLastName());
            }
        } catch (PlayerNotFoundException e) {
            System.out.println("No hay jugadores cargados");
        }
    }
}
