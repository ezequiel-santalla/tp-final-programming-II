package test;

import enums.ETournamentStatus;
import exceptions.*;
import model.Match;
import model.Player;
import model.Result;
import model.SetScore;
import service.TournamentService;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TestTournament {

    public TestTournament() {
        savePlayers();
    }

    DataInitializer dataInitializer = new DataInitializer();
    TournamentService tournamentService = dataInitializer.getTournamentService();
    List<Player> playersFromJson;

    public void nextRound() {
        try {
            if (!tournamentService.getTournament().getStatus().equals(ETournamentStatus.NOT_STARTED)) {
                System.out.println("Ronda actual: " + tournamentService.getTournamentRoundService().getCurrentRound().getClass().getSimpleName());
            } else {
                System.out.println("El torneo no ha iniciado aun");
            }
            tournamentService.advanceTournament();
            System.out.println("Siguiente Ronda: " + tournamentService.getTournamentRoundService().getCurrentRound().getClass().getSimpleName());
        } catch (IncompleteMatchException e) {
            System.out.println(e.getMessage());
        } catch (InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
        } catch (TournamentFullException e) {
            System.out.println(e.getMessage());
        } catch (TournamentNotFoundException e) {
            System.out.println(e.getMessage());
        }
        saveChanges();
    }



    public boolean registerPlayerInTournament(Player player) {
        try {
            tournamentService.registerPlayerInTournament(player);
            System.out.println("Jugador registrado: " + getPlayerData(player));
            return true;

        } catch (TournamentFullException e) {
            System.out.println(e.getMessage());
        } catch (DuplicatePlayerException e) {
            System.out.println(e.getMessage());
        } catch (TournamentNotFoundException e) {
            System.out.println(e.getMessage());
        }
        saveChanges();
        return false;
    }

    public void unsubscribePlayerFromTournament(Integer idPlayer){
        try {
            tournamentService.unsubscribePlayerFromTournament(idPlayer);
            System.out.println("Jugador dado de baja. ID: "+idPlayer);
        } catch (MatchNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TournamentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    public String getPlayerData(Player player) {
        return "ID: " + player.getIdPlayer() + " " + player.getName() + " " + player.getLastName();
    }

    public void registerDuplicatedlayer() {
        if (!getRegisteredPayersInTournament().isEmpty()) {
            registerPlayerInTournament(getRegisteredPayersInTournament().getLast());
        } else {
            System.out.println("No hay jugadores registrados");
        }
    }

    public void fillTournamentWithPlayers() {
        boolean flag = true;
        while (flag) {
            for (Player players : getUnregisteredPlayers()) {
                flag = registerPlayerInTournament(players);
                if (!flag) {
                    break;
                }
            }
        }
    }



    public Player getRandomUnregisteredPlayer() {
        return getUnregisteredPlayers().get(Utils.random(0, getUnregisteredPlayers().size()));
    }

    public List<Player> getUnregisteredPlayers() {
        List<Player> unregisteredPlayers = null;

        unregisteredPlayers = getUpdatedPlayersList();
        unregisteredPlayers.removeAll(getRegisteredPayersInTournament());
        return unregisteredPlayers;
    }


    public List<Player> getRegisteredPayersInTournament() {
        return new ArrayList<>(tournamentService.getTournament().getPlayers());
    }

    public void finalizeAllMatchOfCurrentRound(){
        for(Match match : tournamentService.getTournamentRoundService().getCurrentRound().getMatches()){
            assignRandomResultToMatch(match.getIdMatch());
        }
    }

    public void assignInvalidResultToMatch(Integer idMatch){
        try {
            Result result = new Result();
            try {
                List<SetScore> setsScore = List.of(
                        new SetScore(4, 4),
                        new SetScore(0, -3),
                        new SetScore(7, 5)
                );
            } catch (InvalidResultException e) {
                System.out.println(e.getMessage());
            }
            tournamentService.assignResultToMatch(idMatch, result);
        } catch (MatchNotFoundException | InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
        } catch (TournamentNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvalidResultException e) {
            System.out.println(e.getMessage());

        }
        saveChanges();
    }

    public void assignUndefinedResultToMatch(Integer idMatch){
        try {
            Result result = new Result();
            try {
                List<SetScore> setsScore = List.of(
                        new SetScore(3, 6),
                        new SetScore(6, 2)
                );
            } catch (InvalidResultException e) {
                System.out.println(e.getMessage());
            }
            tournamentService.assignResultToMatch(idMatch, result);
        } catch (MatchNotFoundException | InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
        } catch (TournamentNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvalidResultException e) {
            System.out.println(e.getMessage());
        }
        saveChanges();
    }

    public void assignTooManyResults (Integer idMatch){
        try {
            Result result = new Result();
            try {
                List<SetScore> setsScore = List.of(
                        new SetScore(6, 0),
                        new SetScore(4, 6),
                        new SetScore(7, 6),
                        new SetScore(5, 7)
                );
            } catch (InvalidResultException e) {
                System.out.println(e.getMessage());
            }
            tournamentService.assignResultToMatch(idMatch, result);
        } catch (MatchNotFoundException | InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
        } catch (TournamentNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (InvalidResultException e) {
            System.out.println(e.getMessage());
        }
        saveChanges();
    }



    public void assignRandomResultToMatch(Integer idMatch) {
        try {
            tournamentService.assignResultToMatch(idMatch, dataInitializer.getRandomResult());
        } catch (MatchNotFoundException | InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
        } catch (TournamentNotFoundException e) {
            System.out.println(e.getMessage());
        }catch (InvalidResultException e) {
            System.out.println(e.getMessage());
        }
        saveChanges();
    }

    public void modifyResultToMatch(Integer idMatch, Result result){
        try {
            tournamentService.modifyResultToMatch(idMatch, result);
        } catch (InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
        } catch (MatchNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TournamentNotFoundException e) {
            System.out.println(e.getMessage());
        }catch (InvalidResultException e) {
            System.out.println(e.getMessage());
        }
    }

    public Player getTournamentWinner() {
        try {
            return tournamentService.getTournamentWinner();
        } catch (IncompleteMatchException e) {
            System.out.println(e.getMessage());
        } catch (
                InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Player> getUpdatedPlayersList() {
        List<Player> players = new ArrayList<>();
        try {
            players = dataInitializer.getPlayerService().getAllPlayers();
        } catch (PlayerNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return players;
    }

    public void savePlayers() {
        for (int i = 0; i < 20; i++) {
            dataInitializer.getPlayerService().addPlayer(dataInitializer.getPlayer(i));
        }
    }

    public void saveChanges() {
        try {
            tournamentService.updateTournament(getTournamentService().getTournament());
        } catch (TournamentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public DataInitializer getDataInitializer() {
        return dataInitializer;
    }

    public void setDataInitializer(DataInitializer dataInitializer) {
        this.dataInitializer = dataInitializer;
    }

    public TournamentService getTournamentService() {
        return tournamentService;
    }

    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }
}
