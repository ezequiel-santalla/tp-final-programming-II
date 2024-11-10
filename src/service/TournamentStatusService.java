package service;

import enums.ETournamentStatus;
import exceptions.*;
import model.Player;
import model.Tournament;
import model.rounds.Final;


public class TournamentStatusService {
    private final Tournament tournament;
    private TournamentRoundService tournamentRoundService;
    private TournamentMatchService tournamentMatchService;

    public TournamentStatusService(Tournament tournament, TournamentRoundService tournamentRoundService, TournamentMatchService tournamentMatchService) {
        this.tournament = tournament;
        this.tournamentRoundService = tournamentRoundService;
        this.tournamentMatchService = tournamentMatchService;
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
        tournamentRoundService.nextRound();
    }

    private void advanceCurrentRound() throws IncompleteMatchException {
        if (!tournamentRoundService.isCurrentRoundComplete()) {
            throw new IncompleteMatchException("Not all matches have been completed.");
        }
        if (!(tournamentRoundService.getCurrentRound() instanceof Final)) {
            tournamentRoundService.nextRound();
        }
        updateTournamentStatus();
    }

    public Player getTournamentWinner() throws InvalidTournamentStatusException, IncompleteMatchException {
        if (isTournamentFinished()) {
            return tournamentMatchService.getWinner(tournamentRoundService.getFinalMatch());
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
                tournamentRoundService.getCurrentRound() instanceof Final &&
                tournamentRoundService.isCurrentRoundComplete();
    }

}