package service;

import enums.ETournamentStatus;
import exceptions.*;
import model.Match;
import model.Player;
import model.Tournament;
import model.rounds.*;

import java.util.ArrayList;
import java.util.List;

public class RoundService {
    private final Tournament tournament;
    private final MatchService matchService;

    public RoundService(Tournament tournament, MatchService matchService) {
        this.tournament = tournament;
        this.matchService = matchService;
    }

    public boolean isCurrentRoundComplete() {
        for (Match match : getCurrentRound().getMatches()) {
            if (match.getResult() == null || match.getResult().thereIsNoWinner()) {
                return false; // Algún partido no está completo
            }
        }
        return true; // Todos los partidos están completos
    }


    public List<Player> getPlayersStillCompeting() throws IncompleteMatchException {
        List<Player> playersStillCompeting = new ArrayList<>();

        // Verificar si es la primera ronda
        if (this.tournament.getRounds().isEmpty()) {
            // Devuelve todos los jugadores en el torneo
            playersStillCompeting.addAll(this.tournament.getPlayers());
        } else {
            // Obtiene los ganadores de la última ronda
            for (Match match : getCurrentRound().getMatches()) {
                Player winner = matchService.getWinner(match);
                playersStillCompeting.add(winner);
            }
        }

        return playersStillCompeting;
    }

    public void nextRound() throws IncompleteMatchException {
        Round nextRound = generateNextRound();
        nextRound.generateMatches(getPlayersStillCompeting());
        // Add next round to tournament
        tournament.getRounds().add(nextRound);
        // Check if this is the final round and update the status accordingly
        if (nextRound instanceof Final && isCurrentRoundComplete()) {
            tournament.setStatus(ETournamentStatus.FINISHED);
        }
    }

    public Round generateNextRound() {

        int currentRoundIndex = tournament.getRounds().size();
        // Determine the current round based on the current round index
        switch (currentRoundIndex) {
            case 0 -> {
                return new FirstRound(10);
            }
            case 1 -> {
                return new QuarterFinal(20);
            }
            case 2 -> {
                return new Semifinal(30);
            }
            case 3 -> {
                return new Final(50);
            }
            default -> throw new IllegalArgumentException("Can not create more rounds");
        }
    }

    public Round getCurrentRound() {
        if (tournament.getRounds().isEmpty()) {
            throw new IllegalStateException("No current round available.");
        }
        return tournament.getRounds().getLast(); // Asumiendo que la última ronda es la actual
    }

    public Match getFinalMatch() {
        return getCurrentRound().getMatches().getLast();
    }


}