package test;

import enums.ESurface;
import exceptions.InvalidResultException;
import exceptions.TournamentNotFoundException;
import model.Player;
import model.Result;
import model.SetScore;
import model.Tournament;
import repository.PlayerRepositoryImp;
import repository.TournamentRepositoryImp;
import service.PlayerService;
import service.TournamentService;
import utils.Utils;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class DataInitializer {


    private final List<Player> players = new ArrayList<>();
    private final List<SetScore> possibleScores = new ArrayList<>();
    private TournamentService tournamentService;
    private PlayerService playerService;

    public DataInitializer() {
        createPlayers();
        createSetScores();
        createTournamentService();
        createPlayerService();
    }


    public Player getPlayer(Integer index) {
        return players.get(index);
    }

    public Result getRandomResult() {
        Result result = new Result();
        try {
            result.addSetScore(possibleScores.get(Utils.random(0, possibleScores.size())));
            result.addSetScore(possibleScores.get(Utils.random(0, possibleScores.size())));
            if (result.thereIsNoWinner()) {
                result.addSetScore(possibleScores.get(Utils.random(0, possibleScores.size())));
            }
        } catch (InvalidResultException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private void createPlayers() {

        players.add(new Player("42044093", "Marcos", "Moreno", "Argentino", LocalDate.of(1990, Month.APRIL, 22)));
        players.add(new Player("38011234", "Carlos", "Fernández", "Argentino", LocalDate.of(1992, Month.MARCH, 15)));
        players.add(new Player("41023567", "Luis", "Gómez", "Uruguayo", LocalDate.of(1991, Month.JANUARY, 10)));
        players.add(new Player("45078945", "Juan", "Pérez", "Argentino", LocalDate.of(1989, Month.JULY, 28)));
        players.add(new Player("39045678", "Miguel", "López", "Chileno", LocalDate.of(1993, Month.SEPTEMBER, 5)));
        players.add(new Player("40012345", "Tomás", "Martínez", "Argentino", LocalDate.of(1995, Month.JUNE, 21)));
        players.add(new Player("42087654", "Joaquín", "Ruiz", "Paraguayo", LocalDate.of(1987, Month.MAY, 8)));
        players.add(new Player("43098765", "Matías", "Ríos", "Argentino", LocalDate.of(1994, Month.AUGUST, 13)));
        players.add(new Player("46054321", "Martín", "Sosa", "Uruguayo", LocalDate.of(1996, Month.OCTOBER, 2)));
        players.add(new Player("47067890", "Cristian", "Nuñez", "Argentino", LocalDate.of(1991, Month.DECEMBER, 30)));
        players.add(new Player("44034567", "Agustín", "Duarte", "Chileno", LocalDate.of(1990, Month.FEBRUARY, 18)));
        players.add(new Player("48012345", "Nicolás", "Álvarez", "Argentino", LocalDate.of(1997, Month.NOVEMBER, 9)));
        players.add(new Player("49054321", "Rodrigo", "Vega", "Argentino", LocalDate.of(1994, Month.MARCH, 23)));
        players.add(new Player("50067890", "Hernán", "Castro", "Uruguayo", LocalDate.of(1988, Month.SEPTEMBER, 19)));
        players.add(new Player("51023456", "Francisco", "Silva", "Paraguayo", LocalDate.of(1986, Month.APRIL, 4)));
        players.add(new Player("52034567", "Diego", "Ortiz", "Chileno", LocalDate.of(1992, Month.MAY, 6)));
        players.add(new Player("53045678", "Sebastián", "Morales", "Argentino", LocalDate.of(1993, Month.JULY, 12)));
        players.add(new Player("54056789", "Gabriel", "Luna", "Uruguayo", LocalDate.of(1991, Month.JUNE, 15)));
        players.add(new Player("55067890", "Andrés", "Herrera", "Paraguayo", LocalDate.of(1994, Month.DECEMBER, 2)));
        players.add(new Player("56078901", "Facundo", "Sánchez", "Argentino", LocalDate.of(1995, Month.OCTOBER, 24)));

    }

    private void createSetScores() {

        try {
            possibleScores.add(new SetScore(6, 4));
            possibleScores.add(new SetScore(6, 3));
            possibleScores.add(new SetScore(6, 2));
            possibleScores.add(new SetScore(6, 1));
            possibleScores.add(new SetScore(6, 0));
            possibleScores.add(new SetScore(7, 6));
            possibleScores.add(new SetScore(7, 5));
            possibleScores.add(new SetScore(4, 6));
            possibleScores.add(new SetScore(3, 6));
            possibleScores.add(new SetScore(2, 6));
            possibleScores.add(new SetScore(1, 6));
            possibleScores.add(new SetScore(0, 6));
            possibleScores.add(new SetScore(5, 7));
            possibleScores.add(new SetScore(6, 7));

        } catch (
                InvalidResultException e) {
            System.out.println("Resultado invalido");
        }
    }

    private void createTournamentService() {
        TournamentRepositoryImp tournamentRepositoryImp = new TournamentRepositoryImp();
        Tournament tournament = new Tournament("torneo", "MDP", ESurface.CARPET, LocalDate.of(2024, 2, 2), LocalDate.of(2024, 2, 6));
        try {
            tournamentService = new TournamentService(tournamentRepositoryImp, 13);
        } catch (TournamentNotFoundException e) {
            System.out.println("No se encontro un torneo con ese id" + e.getMessage());
            tournamentService = new TournamentService(tournamentRepositoryImp, tournament);
        }

    }

    private void createPlayerService() {
        PlayerRepositoryImp playerRepositoryImp = new PlayerRepositoryImp();
        setPlayerService(new PlayerService(playerRepositoryImp, getTournamentService()));
    }

    public TournamentService getTournamentService() {
        return tournamentService;
    }

    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<SetScore> getPossibleScores() {
        return possibleScores;
    }
}
