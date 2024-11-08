package test;

import enums.ETournamentStatus;
import exceptions.*;
import model.Match;
import model.Player;
import service.TournamentService;
import utilities.Utilities;

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
                System.out.println("Ronda actual: " + tournamentService.getCurrentRound().getClass().getSimpleName());
            } else {
                System.out.println("El torneo no ha iniciado aun");
            }
            tournamentService.advanceTournament();
            System.out.println("Siguiente Ronda: " + tournamentService.getCurrentRound().getClass().getSimpleName());
        } catch (IncompleteMatchException e) {
            System.out.println(e.getMessage());
        } catch (InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
        } catch (TournamentFullException e) {
            System.out.println(e.getMessage());
        }
        saveChanges();
    }

    public boolean registerPlayerInTournament(Player player) {
        try {
            tournamentService.registerPlayer(player);
            System.out.println("Jugador registrado: " + getPlayerData(player));
            return true;

        } catch (TournamentFullException e) {
            System.out.println(e.getMessage());
        } catch (DuplicatePlayerException e) {
            System.out.println(e.getMessage());
        }
        saveChanges();
        return false;
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
            for (Player players : getUpdatedPlayersList()) {
                flag = registerPlayerInTournament(players);
                if (!flag) {
                    break;
                }
            }
        }
    }

    public Player getRandomUnregisteredPlayer() {
        return getUnregisteredPlayers().get(Utilities.random(0, getUnregisteredPlayers().size()));
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
        for(Match match : tournamentService.getCurrentRound().getMatches()){
            assignResultToMatch(match.getIdMatch());
        }
    }

    public void assignResultToMatch(Integer idMatch) {
        try {
            tournamentService.assignResultToMatch(idMatch, dataInitializer.getRandomResult());
        } catch (MatchNotFoundException | IncompleteMatchException | InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
        }
        saveChanges();
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
