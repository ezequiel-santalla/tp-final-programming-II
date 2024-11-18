package service;

import enums.ETournamentStatus;
import exceptions.*;
import model.Match;
import model.Player;
import model.Result;
import model.Tournament;
import model.rounds.*;

import java.util.ArrayList;
import java.util.List;

public class MatchService {
    private final Tournament tournament;

    public MatchService(Tournament tournament) {
        this.tournament = tournament;
    }


    public Player getWinner(Match match) throws IncompleteMatchException {
        if (match.getResult() == null) {
            throw new IncompleteMatchException("The match has not finished or the result was not loaded.");
        }

        // Check if player one has won two sets
        if (match.getResult().getSetsWonPlayerOne() == 2) {
            return match.getPlayerOne();
        }
        // Check if player two has won two sets
        else if (match.getResult().getSetsWonPlayerTwo() == 2) {
            return match.getPlayerTwo();
        }

        // If there is no defined winner...
        throw new IncompleteMatchException("There is no defined winner.");
    }


    public void assignResult(Integer matchId, Result result) throws MatchNotFoundException, InvalidTournamentStatusException, InvalidResultException, IncompleteMatchException {
        validateResult(matchId, result, true);
        Match match = findMatchById(matchId);
        match.setResult(result);
        assignPoints(match);
    }

    public void modifyResult(Integer matchId, Result result) throws MatchNotFoundException, InvalidTournamentStatusException, InvalidResultException, IncompleteMatchException {
        validateResult(matchId, result, false);
        Match match = findMatchById(matchId);
        resetPoints(match);
        match.setResult(result);
        assignPoints(match);
    }

    public void resetPoints(Match match) throws IncompleteMatchException {
        getWinner(match).setPoints(getWinner(match).getPoints() - tournament.getRounds().getLast().getGivenPoints());
    }

    public void assignPoints(Match match) throws IncompleteMatchException {
        getWinner(match).setPoints(getWinner(match).getPoints() + tournament.getRounds().getLast().getGivenPoints());
    }

    private void validateResult(Integer matchId, Result result, boolean isAssigning) throws MatchNotFoundException, InvalidTournamentStatusException, InvalidResultException {
        if (result == null) {
            throw new InvalidResultException("The result cannot be null.");
        }

        Match match = findMatchById(matchId);

        // Check if the match exists
        if (match == null) {
            throw new MatchNotFoundException("Match not found with ID: " + matchId);
        }

        // If assigning a result, check if it already has one
        if (isAssigning && match.getResult() != null && !match.getResult().thereIsNoWinner()) {
            throw new InvalidTournamentStatusException("The match already has a result assigned.");
        }

        // The match must belong to the current round.
        if (!tournament.getRounds().getLast().getMatches().contains(match)) {
            throw new InvalidTournamentStatusException("The match belongs to a completed round.");
        }

        // The tournament must not have ended
        if (tournament.getStatus().equals(ETournamentStatus.FINISHED)) {
            throw new InvalidTournamentStatusException(tournament.getStatus().getMessage());
        }
    }

    public Match findMatchById(Integer matchId) throws MatchNotFoundException {

        for (Round round : tournament.getRounds()) {
            for (Match match : round.getMatches()) {
                if (match.getIdMatch().equals(matchId)) {
                    return match;
                }
            }
        }
        throw new MatchNotFoundException(matchId);
    }

}