package view;

import enums.ESurface;
import exceptions.*;
import model.*;
import model.rounds.Round;
import repository.PlayerRepositoryImp;
import repository.TournamentRepositoryImp;
import service.MatchService;
import service.PlayerService;
import service.TournamentService;

import java.time.LocalDate;
import java.util.ArrayList;
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
        Tournament tournament = new Tournament("Torneo", "MdP", ESurface.CARPET, LocalDate.of(2024, 11, 16), LocalDate.of(2024, 12, 16));
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
                case 1 -> createTournament();
                case 2 -> showTournamentByID();
                case 3 -> modifyTournament();
                case 4 -> showTournamentsList();
                case 5 -> deleteTournament();
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
                case 1 -> showTournamentMatches();
                case 2 -> showMatchResult();
                case 3 -> assignMatchResult();
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
                if (playerService.getAllPlayers().contains(player)) {
                    System.out.println("\nYa hay un jugador registrado con ese DNI");
                } else {
                    System.out.println("\nJugador agregado correctamente con el ID " + playerService.addPlayer(player));
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

    private void createTournament() {
        System.out.println();
        try {
            tournamentService.addTournament(menuHandler.requestTournamentData(null));
        } catch (Exception e) {
            System.out.println("Error al cargar los datos del torneo: " + e.getMessage());
        }
    }

    private void showTournamentByID() {
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

    private void modifyTournament() {
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

    private void showTournamentsList() {
        try {
            List<Tournament> allTournaments = tournamentService.getAllTournaments();
            if (allTournaments.isEmpty()) {
                System.out.println("No se encontraron torneos cargados");
            } else {
                allTournaments.forEach(t -> System.out.println(t));
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la lista de torneos: " + e.getMessage());
        }


    }

    private void deleteTournament() {
        System.out.println();
        Integer idDelete = menuHandler.requestID();
        try {
            tournamentService.deleteTournament(idDelete);
            System.out.println("Torneo eliminado correctamente con el ID " + idDelete);
        } catch (Exception e) {
            System.out.println("Error al eliminar el torneo: " + e.getMessage());
        }
    }

    private void showTournamentMatches() {
        try {
            Integer tournamentId = menuHandler.requestID();
            Tournament tournament = tournamentService.findTournamentById(tournamentId);
            // Verificar si el torneo tiene rondas y partidos
            if (tournament != null && !tournament.getRounds().isEmpty()) {
                System.out.println("Diagrama de partidos para el torneo: " + tournament.getName());
                for (Round round : tournament.getRounds()) {
                    System.out.println("\nRonda: " + round.getClass().getSimpleName());
                    for (Match match : round.getMatches()) {
                        System.out.println("Partido ID: " + match.getIdMatch() + " - Jugador 1: "
                                + match.getPlayerOne().getName() + " vs Jugador 2: "
                                + match.getPlayerTwo().getName());
                    }
                }
            } else {
                System.out.println("Este torneo no tiene rondas o partidos registrados.");
            }
        } catch (TournamentNotFoundException e) {
            System.out.println("No se encontró un torneo con ese ID.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error al mostrar el diagrama de partidos: " + e.getMessage());
        }
    }

    private void showMatchResult() {
        try {
            Integer tournamentId = menuHandler.requestID();
            Integer matchId = menuHandler.requestID();
            Match match = tournamentService.getTournamentMatchService().findMatchById(matchId);

            if (match != null) {
                Result result = match.getResult(); // obtengo el resultado del partido
                if (result != null) {
                    System.out.println("Resultado del partido con ID " + matchId + ":");
                    for (int i = 0; i < result.getSetsScore().size(); i++) {
                        SetScore setScore = result.getSetsScore().get(i);
                        System.out.println("Set " + (i + 1) + " - Jugador 1: " + setScore.getPlayerOneScore()
                                + " vs Jugador 2: " + setScore.getPlayerTwoScore());
                    }

                    // determinar y mostrar el ganador
                    Player winner = tournamentService.getTournamentMatchService().getWinner(match);
                    System.out.println("Ganador del partido: " + winner.getName() + " " + winner.getLastName());
                } else {
                    System.out.println("Este partido aún no tiene un resultado.");
                }
            } else {
                System.out.println("No se encontro un partido con ese ID.");
            }
        } catch (MatchNotFoundException e) {
            System.out.println("No se encontro un partido con ese ID.");
        } catch (Exception e) {
            System.out.println("Ocurrio un error al mostrar el resultado del partido: " + e.getMessage());
        }
    }



    private void assignMatchResult() {
        try {
            Integer tournamentId = menuHandler.requestID();
            Integer matchId = menuHandler.requestID();
            List<SetScore> setScores = new ArrayList<>(); // almaceno los resultados de los sets

            // pido el puntaje de los jugadores para cada set
            for (int i = 1; i <= 3; i++) {
                System.out.println("Ingrese el puntaje para el Set " + i);
                Integer playerOneScore = menuHandler.requestPlayerScore(1);
                Integer playerTwoScore = menuHandler.requestPlayerScore(2);

                // creo un SetScore con los puntajes de los jugadores
                SetScore setScore = new SetScore(playerOneScore, playerTwoScore);
                setScores.add(setScore);
            }
            // asigno los resultados
            Result result = new Result(setScores);
            tournamentService.getTournamentMatchService().assignResult(matchId, result);
            System.out.println("Resultado asignado correctamente al partido con ID " + matchId + " en el torneo con ID " + tournamentId);
        } catch (MatchNotFoundException e) {
            System.out.println("No se encontro un partido con ese ID.");
        } catch (InvalidTournamentStatusException | InvalidResultException e) {
            System.out.println("No se pudo asignar el resultado: " + e.getMessage());
        }
    }

}
