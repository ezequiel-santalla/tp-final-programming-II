package test;

import exceptions.*;
import model.Player;
import service.TournamentService;

public class TestTournament {

    public TestTournament() {
        addPlayers();
    }

    DataInitializer dataInitializer = new DataInitializer();
    TournamentService tournamentService = dataInitializer.tournamentService;

    public void nextRound(){
        try {
            tournamentService.advanceTournament();
        } catch (IncompleteMatchException e) {
            System.out.println(e.getMessage());
        } catch (InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
        } catch (TournamentFullException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateTournament(){
        try {
            tournamentService.updateTournament(tournamentService.getTournament());
        } catch (TournamentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void assignResultToMatch(Integer idMatch){
        try {
            tournamentService.assignResultToMatch(idMatch, dataInitializer.getRandomResult());
        } catch (MatchNotFoundException | IncompleteMatchException | InvalidTournamentStatusException e) {
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

    public void addPlayers() {
        for (int i = 0; i < 20; i++) {
            dataInitializer.playerService.addPlayer(dataInitializer.getPlayer(i));
        }
    }

}
