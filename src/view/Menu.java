package view;

import enums.ESurface;
import exceptions.IncompleteMatchException;
import exceptions.PlayerNotFoundException;
import exceptions.TournamentNotFoundException;
import model.Player;
import model.Tournament;
import repository.PlayerRepositoryImp;
import repository.TournamentRepositoryImp;
import service.PlayerService;
import service.TournamentService;

import java.time.LocalDate;
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

    public Menu() {
        PlayerRepositoryImp playerRepositoryImp = new PlayerRepositoryImp();
        TournamentRepositoryImp tournamentRepositoryImp = new TournamentRepositoryImp();
        Tournament tournament = new Tournament("Torneo", "MdP", ESurface.CARPET, LocalDate.of(2024,11,16), LocalDate.of(2024,12,16));
        this.tournamentService = new TournamentService(tournamentRepositoryImp, tournament);
        this.playerService = new PlayerService(playerRepositoryImp, tournamentService);
        this.menuHandler = new MenuHandler();
        initMenuOptions();
    }
    public Menu(TournamentService tournamentService, PlayerService playerService) {
        this.tournamentService = tournamentService;
        this.playerService = playerService;
        this.menuHandler = new MenuHandler();
        initMenuOptions();
    }

    private void initMenuOptions() {
        // Menú principal y submenús definidos solo una vez
        principalMenuOptions = Arrays.asList("Administración del Torneo", "Administración de Jugadores", "Administración de Partidos", "Salir");
        tournamentOptions = Arrays.asList("Cargar datos del torneo", "Ver datos del torneo", "Modificar datos del torneo", "Ver lista de torneos", "Eliminar torneo", "Volver");
        playersOptions = Arrays.asList("Agregar jugador", "Modificar jugador", "Ver lista de jugadores", "Ver información de jugador", "Eliminar jugador", "Ver Ranking", "Ver Estadísticas del Jugador", "Volver");
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
                case 0 -> System.out.println("\nSaliendo del programa...");
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
                    System.out.println();
                    try {
                        tournamentService.addTournament(menuHandler.requestTournamentData(null));
                    } catch (Exception e) {
                        System.out.println("Error al cargar los datos del torneo: " + e.getMessage());
                    }
                }
              /*
                    System.out.println();
                    try {
                        Tournament tournament = tournamentService.getTournament();
                        System.out.println(tournament.toString());
                    } catch (Exception e) {
                        System.out.println("Error al obtener los datos del torneo: " + e.getMessage());
                    }*/
                case 2 -> {
                    System.out.println();
                    Integer id = menuHandler.requestID();
                    try {
                        Tournament tournament = tournamentService.findTournamentById(id);
                        if (tournament == null) {
                            System.out.println("Torneo no encontrado");
                        } else {
                            System.out.println(tournament.toString());
                        }
                    } catch (Exception e) {
                        System.out.println("Error al obtener los datos del torneo: " + e.getMessage());
                    }
                }

                case 3 -> {
                    System.out.println();
                    Integer idModify = menuHandler.requestID();
                    try {
                        Tournament tournament = tournamentService.findTournamentById(idModify);
                        if (tournament == null) {
                            System.out.println("Torneo no encontrado");
                        } else {
                            tournamentService.updateTournament(menuHandler.requestTournamentData(tournament));
                        }
                    } catch (Exception e) {
                        System.out.println("Error al modificar los datos del torneo: " + e.getMessage());
                    }
                }
                case 4 -> {
                   /*  MOSTRAR LISTA DE TORNEOS
                    System.out.println();
                    List<Tournament> allTournaments = tournamentService.getAllTournaments();
                    if (allTournaments.isEmpty()) {
                        System.out.println("No se encontraron torneos cargados");
                    } else {
                        allTournaments.forEach(t -> System.out.println(t.toString()));
                    }
                    */

                }
                case 5 -> {
                    System.out.println();
                        Integer idDelete = menuHandler.requestID();
                        try {
                            tournamentService.deleteTournament(idDelete);
                            System.out.println("Torneo eliminado correctamente con el ID "+idDelete);
                        } catch (Exception e) {
                            System.out.println("Error al eliminar el torneo: " + e.getMessage());
                        }
                }
                case 0 -> System.out.println("\nVolviendo al menú principal...");
                default -> System.out.println("\nOpción no válida.");
            }
        } while (index != 0);
    }
    private void playersMenu() {
        int index;
        do {
            index = menuHandler.requestEntry(playersOptions);
            switch (index) {
                case 1 -> addPlayer();
                case 2 -> modifyPlayer();
                case 3 -> showPlayersList();
                case 4 -> showPlayerData(menuHandler.requestID());
                case 5 -> confirmPlayerDeleted(menuHandler.requestID());
                case 6 -> showRanking();
                case 7 -> showPlayerStats();
                case 0 -> System.out.println("\nVolviendo al menú principal...");
                default -> System.out.println("\nOpción no válida.");
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

    private void showRanking() {
        try {
            System.out.println(playerService.showPlayerRankings());
        } catch (PlayerNotFoundException e) {
            System.out.println("No hay jugadores en el ranking");
        }
    }

    private void showPlayerStats() {
        try {
            System.out.println(playerService.showStatsByPlayer(menuHandler.requestID()));
        } catch (IncompleteMatchException e) {
            System.out.println("Los partidos del torneo no están terminados");
        } catch (PlayerNotFoundException e) {
            System.out.println("No hay jugadores para mostrar sus estadísticas");
        }
    }

    private void addPlayer() {
        System.out.println();
        Player player = menuHandler.requestPlayerData();

        if (player != null) {
            try {
                if(playerService.getAllPlayers().contains(player)){
                    System.out.println("\nYa hay un jugador registrado con ese DNI");
                }else{
                    System.out.println("\nJugador agregado correctamente con el ID "+playerService.addPlayer(player));
                }
            } catch (PlayerNotFoundException e) {
                System.out.println("Error en el archivo");
            }
        }
    }

    private void modifyPlayer() {
        try {
            System.out.println();
            int playerID = menuHandler.requestID();

            showPlayerData(playerID);
            System.out.println("Ingrese los nuevos datos del jugador con ID: " + playerID + "\n");

            Player updatedPlayer = menuHandler.requestPlayerData();
            updatedPlayer.setIdPlayer(playerID);
            playerService.updatePlayer(updatedPlayer);
            System.out.println("\nJugador actualizado correctamente.");
        } catch (Exception e) {
            System.out.println("\nOcurrió un error al modificar el jugador: " + e.getMessage());
        }
    }

    private void confirmPlayerDeleted(Integer playerID) {
        if (showPlayerData(playerID)) {
            System.out.println("\nSe eliminará jugador...");
            if (menuHandler.requestConfirmation()) {
                try {
                    playerService.deletePlayer(playerID);
                    System.out.println("Jugador eliminado correctamente");
                } catch (PlayerNotFoundException e) {
                    System.out.println("No se pudo eliminar el jugador");
                }
            }
        }
    }

    private void showPlayersList() {
        try {
            if (!playerService.getAllPlayers().isEmpty()) {
                System.out.println("\nJugadores registrados:\n");
            }

            for (Player player : playerService.getAllPlayers()) {
                System.out.println("ID: " + player.getIdPlayer() + " - " + player.getName() + " " + player.getLastName());
            }
        } catch (PlayerNotFoundException e) {
            System.out.println("No hay jugadores cargados");
        }
    }
}
