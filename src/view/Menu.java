package view;

import enums.ETournamentStatus;
import exceptions.*;
import model.*;
import model.rounds.Round;
import repository.PlayerRepositoryImp;
import repository.TournamentRepositoryImp;
import service.PlayerService;
import service.TournamentService;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    private final MenuHandler menuHandler;
    private List<String> principalMenuOptions;
    private List<String> tournamentCRUDOptions;
    private List<String> tournamentAdminOptions;
    private List<String> playersOptions;
    private List<String> matchesOptions;
    private final TournamentService tournamentService;
    private final PlayerService playerService;


    public Menu() {
        this.tournamentService = new TournamentService(new TournamentRepositoryImp());
        this.playerService = new PlayerService(new PlayerRepositoryImp(), tournamentService);
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
        principalMenuOptions = Arrays.asList("Administración del Torneo", "Administración de Jugadores", "Salir");
        tournamentCRUDOptions = Arrays.asList("Administrar torneo existente", "Crear nuevo torneo", "Modificar datos del torneo", "Ver lista de torneos", "Eliminar torneo", "Volver");
        playersOptions = Arrays.asList("Agregar jugador", "Modificar jugador", "Ver lista de jugadores", "Ver información de jugador", "Eliminar jugador", "Ver Ranking", "Ver Estadísticas del Jugador", "Volver");
        matchesOptions = Arrays.asList("Ver diagrama de partidos", "Ver resultado de partidos", "Cargar resultado de partido", "Volver");
    }

    public void runMenu() {
        menuHandler.cleanScreen();
        int index;
        do {
            index = menuHandler.requestEntry(principalMenuOptions, "Menú principal");
            switch (index) {
                case 1 -> tournamentCrudMenu();
                case 2 -> playersMenu();
                case 0 -> System.out.println("\nSaliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }
        } while (index != 0);
        menuHandler.cleanScreen();
    }

    private void tournamentCrudMenu() {
        menuHandler.cleanScreen();
        int index;
        do {
            index = menuHandler.requestEntry(tournamentCRUDOptions, "Torneos ABM");
            switch (index) {
                case 1 -> tournamentAdminMenu();
                case 2 -> createTournament();
                case 3 -> modifyTournament();
                case 4 -> showTournamentsList();
                case 5 -> deleteTournament();
                case 0 -> System.out.println("\nVolviendo al menú principal...");
                default -> System.out.println("\nOpción no válida.");
            }
        } while (index != 0);
        menuHandler.cleanScreen();
    }

    private void playersMenu() {
        menuHandler.cleanScreen();
        int index;
        do {
            index = menuHandler.requestEntry(playersOptions, "Menú de Jugadores");
            switch (index) {
                case 1 -> addPlayer();
                case 2 -> modifyPlayer();
                case 3 -> showPlayersList();
                case 4 -> fetchPlayerData();
                case 5 -> confirmPlayerDeleted();
                case 6 -> showRanking();
                case 7 -> fetchPlayerStats();
                case 0 -> System.out.println("\nVolviendo al menú principal...");
                default -> System.out.println("\nOpción no válida.");
            }
        } while (index != 0);
        menuHandler.cleanScreen();
    }

    private void matchesMenu() {
        menuHandler.cleanScreen();
        int index;
        do {
            index = menuHandler.requestEntry(matchesOptions, "Menú de partidos");
            switch (index) {
                case 1 -> showTournamentMatches();
                case 2 -> showMatchResult();
                case 3 -> assignMatchResult();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (index != 0);
        menuHandler.cleanScreen();
    }

    public void setActiveTournament() throws DataEntryCancelledException {
        Integer id;
        try {
            id = menuHandler.requestID("del torneo que desea usar ");
            tournamentService.setTournamentById(id);
        } catch (TournamentNotFoundException e) {
            System.out.println("No se encontró torneo con ese ID");
            setActiveTournament();
        }
    }

    public void tournamentAdminMenu() {
        try {
            setActiveTournament();
            int index;
            do {
                menuHandler.cleanScreen();
                evaluateStatus();
                int tournamentID = tournamentService.getTournament().getIdTournament();
                index = menuHandler.requestEntry(tournamentAdminOptions, "Menú del torneo ID: " + tournamentID);
                switch (index) {
                    case 1 -> registerPlayer();
                    case 2 -> unregisterPlayer();
                    case 3 -> showPlayersListInTournament();
                    case 4 -> matchesMenu();
                    case 5 -> advanceToNextRound();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }
            } while (index != 0);
        } catch (DataEntryCancelledException e) {
            System.out.println("Entrada cancelada");
        }
        menuHandler.cleanScreen();
    }

    public void unregisterPlayer() {
        menuHandler.cleanScreen();
        printPlayersList(new ArrayList<>(tournamentService.getTournament().getPlayers()), "Jugadores registrados para dar de baja");
        Integer idPlayer;
        try {
            idPlayer = menuHandler.requestID("del jugador que desea dar de baja");
            tournamentService.unsubscribePlayerFromTournament(idPlayer);
            System.out.println("Jugador dado de baja con éxito");
        } catch (DataEntryCancelledException e) {
            System.out.println("Entrada cancelada");
        } catch (PlayerNotFoundException e) {
            System.out.println("No hay jugador con ese ID");
        } catch (TournamentNotFoundException e) {
            System.out.println("No se encontró torneo con ese ID");
        } catch (InvalidTournamentStatusException e) {
            System.out.println("El torneo ya comenzó");
        }
        menuHandler.requestPressEnter();
    }

    public void registerPlayer() {
        boolean flag = false;

        while (!flag) {
            menuHandler.cleanScreen();
            printPlayersList(getUnregisteredPlayers(), "Jugadores disponibles para registrar");
            Integer idPlayer;

            try {
                idPlayer = menuHandler.requestID("del jugador que desea registrar");

                tournamentService.registerPlayerInTournament(playerService.findPlayerById(idPlayer));
                System.out.println("Jugador registrado con éxito");
            } catch (DataEntryCancelledException e) {
                flag = true;
                System.out.println("Entrada cancelada");
            } catch (PlayerNotFoundException e) {
                System.out.println("No hay jugador con ese ID");
            } catch (TournamentFullException e) {
                flag = true;
                System.out.println("El torneo está completo");
            } catch (DuplicatePlayerException e) {
                System.out.println("El jugador ya se encuentra registrado");
            } catch (TournamentNotFoundException e) {
                flag = true;
                System.out.println("No se encontró torneo con ese ID");
            }
        }
        menuHandler.requestPressEnter();
    }

    private void advanceToNextRound() {
        try {
            tournamentService.advanceTournament();
            System.out.println("Se avanzó correctamente a la siguiente ronda del torneo");
        } catch (TournamentNotFoundException e) {
            System.out.println("No se encontró un torneo con ese ID");
        } catch (IncompleteMatchException e) {
            System.out.println("No se puede avanzar a la siguiente ronda: la ronda actual no está completa.");
        } catch (InvalidTournamentStatusException e) {
            showTournamentWinner();
        } catch (TournamentFullException e) {
            System.out.println("No hay suficientes jugadores para iniciar");
        }
        menuHandler.requestPressEnter();
    }

    public void showTournamentWinner() {
        try {
            menuHandler.cleanScreen();
            System.out.println("Ganador del torneo: " + tournamentService.getTournamentWinner());
        } catch (InvalidTournamentStatusException | IncompleteMatchException ex) {
            System.out.println("El torneo no ha finalizado");
        }
    }

    private void evaluateStatus() {
        String message = "";
        switch (tournamentService.getTournament().getStatus()) {
            case ETournamentStatus.NOT_STARTED -> message = "Iniciar torneo";
            case ETournamentStatus.IN_PROGRESS -> message = "Siguiente Ronda";
            case ETournamentStatus.FINISHED -> message = "Ver ganador";
        }
        tournamentAdminOptions = Arrays.asList("Registrar jugador", "Dar de baja jugador", "Ver jugadores del torneo", "Administrar partidos", message, "Volver");
    }

    private boolean fetchPlayerData() {
        menuHandler.cleanScreen();
        try {
            return showPlayerData(menuHandler.requestID("del jugador "));
        } catch (DataEntryCancelledException e) {
            System.out.println("Carga de datos cancelada");
            return false;
        } finally {
            menuHandler.requestPressEnter();
        }
    }

    private boolean showPlayerData(Integer playerID) {
        menuHandler.cleanScreen();
        try {
            System.out.println(playerService.findPlayerById(playerID));
            return true;
        } catch (PlayerNotFoundException e) {
            System.out.println("No se encontró jugador con ese id");
            return false;
        }
    }

    private void showRanking() {
        menuHandler.cleanScreen();
        try {
            System.out.println(playerService.showPlayerRankings());
        } catch (PlayerNotFoundException e) {
            System.out.println("No hay jugadores en el ranking");
        }
        menuHandler.requestPressEnter();
    }

    private void fetchPlayerStats() {
        menuHandler.cleanScreen();
        try {
            showPlayerStats(menuHandler.requestID("del jugador "));
        } catch (DataEntryCancelledException e) {
            System.out.println("Carga de datos cancelada");
            menuHandler.requestPressEnter();
        }
    }

    private void showPlayerStats(Integer playerID) {
        menuHandler.cleanScreen();
        try {
            System.out.println(playerService.showStatsByPlayer(playerID));
        } catch (IncompleteMatchException e) {
            System.out.println("Los partidos del torneo no están terminados");
        } catch (PlayerNotFoundException e) {
            System.out.println("No existe un jugador con el ID: " + playerID + " en la lista de jugadores");
        } catch (TournamentNotFoundException e) {
            System.out.println("No se encontró torneo con ese ID");
        }
        menuHandler.requestPressEnter();
    }

    private void addPlayer() {
        menuHandler.cleanScreen();
        Player player = null;
        try {
            player = menuHandler.requestPlayerData();
        } catch (DataEntryCancelledException e) {
            System.out.println("Se canceló la carga");
        }

        if (player != null) {
            try {
                if (playerService.getAllPlayers().contains(player)) {
                    System.out.println("\nYa hay un jugador registrado con ese DNI");
                } else {
                    System.out.println("\nJugador agregado correctamente con el ID " + playerService.addPlayer(player));
                }
            } catch (PlayerNotFoundException e) {
                System.out.println("Error en el archivo");
            }
        }
        menuHandler.requestPressEnter();
    }

    private void modifyPlayer() {
        menuHandler.cleanScreen();
        try {
            System.out.println();
            int playerID = menuHandler.requestID("del jugador ");

            if (showPlayerData(playerID)) {
                System.out.println("Ingrese los nuevos datos del jugador con ID: " + playerID + "\n");

                Player updatedPlayer = menuHandler.requestPlayerData();
                updatedPlayer.setIdPlayer(playerID);
                playerService.updatePlayer(updatedPlayer);
                System.out.println("\nJugador actualizado correctamente.");
            }
        } catch (PlayerNotFoundException e) {
            System.out.println("\nNo se encontró el jugador con ese ID.");
        } catch (DataEntryCancelledException e) {
            System.out.println("\nModificación cancelada.");
        }
        menuHandler.requestPressEnter();
    }

    private void confirmPlayerDeleted() {
        Integer playerID;
        try {
            playerID = menuHandler.requestID("del jugador ");

            if (showPlayerData(playerID)) {
                System.out.println("\nSe eliminará jugador...");
                if (menuHandler.requestConfirmation()) {

                    playerService.deletePlayer(playerID);
                    System.out.println("Jugador eliminado correctamente");
                }
            }
        } catch (PlayerNotFoundException e) {
            System.out.println("No se pudo eliminar el jugador");
        } catch (DataEntryCancelledException e) {
            System.out.println("Entrada cancelada");
        }
        menuHandler.requestPressEnter();
    }

    private void showPlayersListInTournament() {
        menuHandler.cleanScreen();
        printPlayersList(new ArrayList<>(tournamentService.getTournament().getPlayers()), "Jugadores registrados en el torneo");
        menuHandler.requestPressEnter();
    }

    private void showPlayersList() {
        menuHandler.cleanScreen();
        try {
            printPlayersList(playerService.getAllPlayers(), "Jugadores registrados");
        } catch (PlayerNotFoundException | FileProcessingException e) {
            System.out.println("No hay jugadores registrados");
        }
        menuHandler.requestPressEnter();
    }

    private void printPlayersList(List<Player> players, String title) {
        if (!players.isEmpty()) {
            System.out.println("\n" + title + ":\n");
        }

        for (Player player : players) {
            System.out.println("ID: " + player.getIdPlayer() + " - " + player.getName() + " " + player.getLastName());
        }
    }

    private void createTournament() {
        System.out.println();

        try {
            Integer tournamentID = tournamentService.addTournament(menuHandler.requestTournamentData(null));
            System.out.println("Torneo creado con el ID: " + tournamentID);
        } catch (DataEntryCancelledException e) {
            System.out.println("Carga cancelada");
        }
        menuHandler.requestPressEnter();
    }

    private void modifyTournament() {
        menuHandler.cleanScreen();
        try {
            Integer idModify = menuHandler.requestID("del torneo");

            Tournament tournament = tournamentService.findTournamentById(idModify);

            System.out.println("\nDatos del torneo a modificar:");
            showTournamentDetails(tournament);

            tournamentService.updateTournament(menuHandler.requestTournamentData(tournament));
            System.out.println("Torneo modificado con exito.");
        } catch (TournamentNotFoundException e) {
            System.out.println("Torneo no encontrado.");
        } catch (DataEntryCancelledException e) {
            System.out.println("Carga de datos cancelada.");
        }
        menuHandler.requestPressEnter();
    }


    public void showTournamentDetails(Tournament tournament) {
        System.out.println("\n" + """
    ---------------------------------------------
    |            Detalles del Torneo            |
    ---------------------------------------------
    | ID                    : %-17s |
    | Nombre                : %-17s |
    | Ubicación             : %-17s |
    | Superficie            : %-17s |
    | Fecha de Inicio       : %-17s |
    | Fecha de Finalización : %-17s |
    ---------------------------------------------
    """.formatted(
                tournament.getIdTournament(),
                tournament.getName(),
                tournament.getLocation(),
                tournament.getSurface().getDisplayName(),
                Utils.formatLocalDate(tournament.getStartingDate()),
                Utils.formatLocalDate(tournament.getEndingDate())
        ));
    }



    private void showTournamentsList() {
        menuHandler.cleanScreen();
        try {
            List<Tournament> allTournaments = tournamentService.getAllTournaments();
            if (allTournaments.isEmpty()) {
                System.out.println("No se encontraron torneos cargados");
            } else {
                printHeaderTournament();
                allTournaments.forEach(System.out::println);
                System.out.println("--------------------------------------------------------------------------------------------------------------");

            }
        } catch (Exception e) {
            System.out.println("Error al obtener la lista de torneos: " + e.getMessage());
        }
        menuHandler.requestPressEnter();
    }

    private void printHeaderTournament() {
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("| ID    | Nombre               | Lugar           | Superficie   | Inicio       | Finalización | Estado       |");
        System.out.println("--------------------------------------------------------------------------------------------------------------");
    }

    private void deleteTournament() {
        menuHandler.cleanScreen();

        try {
            List<Tournament> allTournaments = tournamentService.getAllTournaments();
            if (allTournaments.isEmpty()) {
                System.out.println("No se encontraron torneos cargados.");
            } else {
                printHeaderTournament();
                allTournaments.forEach(System.out::println);
                System.out.println("--------------------------------------------------------------------------------------------------------------");

                Integer idDelete = menuHandler.requestID("del torneo a eliminar");
                tournamentService.deleteTournament(idDelete);
                System.out.println("Torneo eliminado correctamente con el ID " + idDelete);
            }
        } catch (TournamentNotFoundException e) {
            System.out.println("Torneo no encontrado con ese ID.");
        } catch (DataEntryCancelledException e) {
            System.out.println("Operación cancelada por el usuario.");
        }
        menuHandler.requestPressEnter();
    }


    private void showTournamentMatches() {
        menuHandler.cleanScreen();
        try {
            Tournament tournament = tournamentService.getTournament();

            if (tournament != null && !tournament.getRounds().isEmpty()) {
                System.out.println("Diagrama de partidos para el torneo: " + tournament.getName());

                for (Round round : tournament.getRounds()) {
                    System.out.println("\nRonda: " + round.getClass().getSimpleName() + "\n");

                    // Encabezados de la tabla
                    System.out.printf("%-10s %-40s %-15s\n", "ID", "Jugadores", "Estado");
                    System.out.println("-".repeat(65));

                    for (Match match : round.getMatches()) {
                        String matchStatus = match.getResult() == null ? "PENDIENTE" : "FINALIZADO";

                        System.out.printf(
                                "%-10d %-40s %-15s\n",
                                match.getIdMatch(),
                                match.getPlayerOne().getName() + " " + match.getPlayerOne().getLastName() + " vs. " +
                                        match.getPlayerTwo().getName() + " " + match.getPlayerTwo().getLastName(),
                                matchStatus
                        );
                    }
                    System.out.println();
                }
            } else {
                System.out.println("Este torneo no tiene rondas o partidos registrados.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al mostrar el diagrama de partidos: " + e.getMessage());
        }
        menuHandler.requestPressEnter();
    }

    private void showMatchResult() {
        menuHandler.cleanScreen();
        try {
            Integer matchId = menuHandler.requestID("del partido ");
            Match match = tournamentService.getMatchService().findMatchById(matchId);

            if (match != null) {
                Result result = match.getResult();
                if (result != null) {
                    Player playerOne = match.getPlayerOne();
                    Player playerTwo = match.getPlayerTwo();

                    System.out.println("\nResultado del partido:\n");

                    int maxNameLength = Math.max(
                            playerOne.getName().length() + playerOne.getLastName().length() + 1,
                            playerTwo.getName().length() + playerTwo.getLastName().length() + 1
                    );
                    int nameColumnWidth = Math.max(maxNameLength, 30);
                    int setsCount = result.getSetsScore().size();
                    int setColumnWidth = 8;

                    System.out.printf("%-" + nameColumnWidth + "s", "Jugador");
                    for (int i = 1; i <= setsCount; i++) {
                        System.out.printf("| Set %d ", i);
                    }
                    System.out.println();
                    System.out.println("-".repeat(nameColumnWidth + setsCount * setColumnWidth));

                    System.out.printf("%-" + nameColumnWidth + "s", playerOne.getName() + " " + playerOne.getLastName());
                    for (SetScore setScore : result.getSetsScore()) {
                        System.out.printf("| %-6d", setScore.getPlayerOneScore());
                    }
                    System.out.println();

                    System.out.printf("%-" + nameColumnWidth + "s", playerTwo.getName() + " " + playerTwo.getLastName());
                    for (SetScore setScore : result.getSetsScore()) {
                        System.out.printf("| %-6d", setScore.getPlayerTwoScore());
                    }
                    System.out.println();

                    Player winner = tournamentService.getMatchService().getWinner(match);
                    System.out.println("\nGanador del partido: " + winner.getName() + " " + winner.getLastName());
                } else {
                    System.out.println("\nEste partido aún no tiene un resultado.");
                }
            }
        } catch (MatchNotFoundException e) {
            System.out.println("No se encontró un partido con ese ID.");
        } catch (IncompleteMatchException e) {
            System.out.println("El resultado del partido no está definido.");
        } catch (DataEntryCancelledException e) {
            System.out.println("Entrada cancelada");
        }
        menuHandler.requestPressEnter();
    }

    private void assignMatchResult() {
        menuHandler.cleanScreen();
        try {
            Integer matchId = menuHandler.requestID("del partido ");
            Result result = new Result();
            Player playerOne = tournamentService.getMatchService().findMatchById(matchId).getPlayerOne();
            Player playerTwo = tournamentService.getMatchService().findMatchById(matchId).getPlayerTwo();

            while (result.thereIsNoWinner()) {
                System.out.println("Ingrese el resultado del Set " + (result.getSetsScore().size() + 1));
                Integer playerOneScore = menuHandler.requestPlayerScore(playerOne.getName() + " " + playerOne.getLastName());
                Integer playerTwoScore = menuHandler.requestPlayerScore(playerTwo.getName() + " " + playerTwo.getLastName());

                if (Utils.validateFullScore(playerOneScore, playerTwoScore)) {
                    SetScore setScore = new SetScore();
                    setScore.setPlayerOneScore(playerOneScore);
                    setScore.setPlayerTwoScore(playerTwoScore);
                    result.addSetScore(setScore);
                } else {
                    System.out.println("El resultado no es válido. Intente nuevamente.");
                }
            }

            tournamentService.assignResultToMatch(matchId, result);
            playerService.updatePlayer(tournamentService.getMatchService().getWinner(tournamentService.getMatchService().findMatchById(matchId)));
            System.out.println("Resultado asignado correctamente al partido con ID " + matchId);
        } catch (MatchNotFoundException e) {
            System.out.println("No se encontró un partido con ese ID");
        } catch (InvalidTournamentStatusException | InvalidResultException e) {
            System.out.println("No se pudo asignar el resultado");
        } catch (TournamentNotFoundException e) {
            System.out.println("No se encontró torneo con ese ID");
        } catch (DataEntryCancelledException e) {
            System.out.println("Entrada cancelada");
        } catch (IncompleteMatchException e) {
            System.out.println("El partido no está completo");
        } catch (PlayerNotFoundException e) {
            System.out.println("No se encontró el jugador");
        }
        menuHandler.requestPressEnter();
    }

    public List<Player> getUnregisteredPlayers() {
        List<Player> unregisteredPlayers = null;

        try {
            unregisteredPlayers = playerService.getAllPlayers();
        } catch (PlayerNotFoundException e) {
            System.out.println("Jugador no encontrado");
        }
        assert unregisteredPlayers != null;
        unregisteredPlayers.removeAll(tournamentService.getTournament().getPlayers());
        return unregisteredPlayers;
    }
}
