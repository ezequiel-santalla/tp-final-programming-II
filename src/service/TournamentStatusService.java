package service;

import enums.ETournamentStatus;
import exceptions.*;
import model.Player;
import model.Tournament;
import model.rounds.Final;


public class TournamentStatusService {
    private final Tournament tournament;
    private RoundService roundService;
    private MatchService matchService;

    public TournamentStatusService(Tournament tournament, RoundService roundService, MatchService matchService) {
        this.tournament = tournament;
        this.roundService = roundService;
        this.matchService = matchService;
    }

    public void advanceTournament() throws IncompleteMatchException, InvalidTournamentStatusException, TournamentFullException {
        switch (tournament.getStatus()) {
            case NOT_STARTED -> startTournament();
            case IN_PROGRESS -> advanceCurrentRound();
            case FINISHED -> throw new InvalidTournamentStatusException(tournament.getStatus().getMessage());
            default -> throw new IllegalStateException("Unexpected tournament status: " + tournament.getStatus());
        }
    }

    private void startTournament() throws TournamentFullException, IncompleteMatchException {
        if (tournament.getPlayers().size() == 16) {
            throw new TournamentFullException("Not enough players to start the tournament.");
        }
        tournament.setStatus(ETournamentStatus.IN_PROGRESS);
        roundService.nextRound();
    }

    private void advanceCurrentRound() throws IncompleteMatchException {
        if (!roundService.isCurrentRoundComplete()) {
            throw new IncompleteMatchException("Not all matches have been completed.");
        }
        if (!(roundService.getCurrentRound() instanceof Final)) {
            roundService.nextRound();
        }
        updateTournamentStatus();
    }

    public Player getTournamentWinner() throws InvalidTournamentStatusException, IncompleteMatchException {
        if (isTournamentFinished()) {
            return matchService.getWinner(roundService.getFinalMatch());
        }
        throw new InvalidTournamentStatusException("Tournament has not finished yet.");
    }

    private boolean isTournamentFinished() {
        return tournament.getStatus().equals(ETournamentStatus.FINISHED);
    }

    private void updateTournamentStatus() {
        if (isFinalRoundComplete()) {
            tournament.setStatus(ETournamentStatus.FINISHED);
        }
    }

    private boolean isFinalRoundComplete() {
        return !tournament.getRounds().isEmpty() &&
                roundService.getCurrentRound() instanceof Final &&
                roundService.isCurrentRoundComplete();
    }

}