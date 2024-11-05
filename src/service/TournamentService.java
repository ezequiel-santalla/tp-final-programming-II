package service;

import enums.ETournamentStatus;
import exceptions.*;
import model.*;
import model.round.*;
import repository.TournamentRepositoryImp;

import java.util.ArrayList;
import java.util.List;

public class TournamentService {
    private final TournamentRepositoryImp tournamentRepositoryImp;
    private final PlayerService playerService;
    private Tournament tournament;

    public TournamentService(PlayerService playerService, TournamentRepositoryImp tournamentRepositoryImp, Tournament tournament) {
        this.playerService = playerService;
        this.tournamentRepositoryImp = tournamentRepositoryImp;
        this.tournament = tournament;
        addTournament(tournament);
    }

    public TournamentService(PlayerService playerService, TournamentRepositoryImp tournamentRepositoryImp, Integer idTournament) throws TournamentNotFoundException {
        this.playerService = playerService;
        this.tournamentRepositoryImp = tournamentRepositoryImp;
        this.tournament = findTournamentById(idTournament);
    }

    public Integer addTournament(Tournament tournament) throws FileProcessingException {
        return tournamentRepositoryImp.create(tournament);
    }

    public Tournament findTournamentById(Integer id) throws TournamentNotFoundException, FileProcessingException {
        tournament = tournamentRepositoryImp.find(id);
        return tournament;
    }

    public void updateTournament(Tournament tournament) throws TournamentNotFoundException, FileProcessingException {
        tournamentRepositoryImp.update(tournament);
    }

    public void deleteTournament(Integer id) throws TournamentNotFoundException, FileProcessingException {
        tournamentRepositoryImp.delete(id);
    }

    public List<Tournament> getAllTournaments() throws TournamentNotFoundException, FileProcessingException {
        return tournamentRepositoryImp.getAll();
    }


    public void assignPoints() throws IncompleteMatchException {
        for (Round round : tournament.getRounds()) {
            for (Match match : round.getMatches()) {
                Player player = getWinner(match);
                player.setPoints(player.getPoints() + round.getGivenPoints());
            }
        }
    }

    public void registerPlayer(Player player) throws TournamentFullException {
        if (tournament.getPlayers().size() < 16) {
            tournament.getPlayers().add(player);
        } else {
            throw new TournamentFullException("Tournament is full");
        }
    }

    private boolean allMatchesCompleted() {
        for (Match match : tournament.getRounds().getLast().getMatches()) {
            System.out.println("Evaluando si los partidos estan completos");
            if (match.getResult() == null || match.getResult().getSetsWonPlayerOne() == 0 && match.getResult().getSetsWonPlayerTwo() == 0) {
                return false; // Algún partido no está completo
            }
        }
        return true; // Todos los partidos están completos
    }


    public List<Player> getPlayersStillCompeting() throws IncompleteMatchException {
        List<Player> playersStillCompeting = new ArrayList<>();

        // Verificar si es la primera ronda
        if (this.tournament.getRounds().size() == 0) {
            // Devuelve todos los jugadores en el torneo
            playersStillCompeting.addAll(this.tournament.getPlayers());
        } else {
            // Obtiene los ganadores de la última ronda
            for (Match match : getCurrentRound().getMatches()) {
                Player winner = getWinner(match);
                playersStillCompeting.add(winner);
            }
        }

        return playersStillCompeting;
    }

    private void generateNextRound() throws IncompleteMatchException {
        // Solo generar la siguiente ronda si hay jugadores suficientes
        if (tournament.getPlayers().size() < 16) {
            throw new IncompleteMatchException("Not enough players to generate the next round.");
        }

        Round currentRound = getCurrentRound();

        // Solo generar partidos si no hay una ronda existente
        if (currentRound.getMatches().isEmpty()) {
            currentRound.generateMatches(getPlayersStillCompeting());
        }

        // Agregar la ronda actual al torneo
        tournament.getRounds().add(currentRound);
        currentRound.setId(tournament.getRounds().size());
        // Comprobar si esta es la ronda final y actualizar el estado según corresponda
        if (currentRound instanceof Final) {
            tournament.setStatus(ETournamentStatus.FINISHED);
        }
    }

    public void advanceTournament() throws IncompleteMatchException, TournamentFinishedException, TournamentFullException {
        switch (tournament.getStatus()) {
            case NOT_STARTED -> {
                if (tournament.getPlayers().size() < 16) {
                    throw new TournamentFullException("Not enough players to start the tournament.");
                }
                tournament.setStatus(ETournamentStatus.IN_PROGRESS);
                generateNextRound();
            }
            case IN_PROGRESS -> {
                if (!allMatchesCompleted()) {
                    throw new IncompleteMatchException("Not all matches have been completed.");
                }
                generateNextRound();
            }
            case FINISHED -> throw new TournamentFinishedException(ETournamentStatus.FINISHED.getMessage());
            default -> throw new IllegalStateException("Unexpected tournament status: " + tournament.getStatus());
        }
    }

    private Round getCurrentRound() {
        int currentRoundIndex = tournament.getRounds().size();
        // Determine the current round based on the current round index
        switch (currentRoundIndex) {
            case 0 -> {
                return new FirstRound();
            }
            case 1 -> {
                return new QuarterFinal();
            }
            case 2 -> {
                return new Semifinal();
            }
            case 3 -> {
                return new Final();
            }
            default -> throw new IllegalStateException("Unexpected number of rounds: " + currentRoundIndex);
        }

    }

    public Tournament getTournament() {
        return tournament;
    }


    public List<Match> getMatchesByPlayer(Integer idPlayer) throws FileProcessingException {

        List<Match> playerMatches = new ArrayList<>();
        for (Round round : this.tournament.getRounds()) {
            for (Match match : round.getMatches()) {
                if (match.getPlayerOne().getIdPlayer().equals(idPlayer) ||
                        match.getPlayerTwo().getIdPlayer().equals(idPlayer)) {
                    playerMatches.add(match);
                }
            }
        }
        return playerMatches;
    }

    public Player getWinner(Match match) throws IncompleteMatchException {
        if (match.getResult() == null) {
            throw new IncompleteMatchException("The match has not finished or the result was not loaded.");
        }
        if (match.getResult().getSetsWonPlayerOne() == 2) {
            return match.getPlayerOne();
        } else if (match.getResult().getSetsWonPlayerTwo() == 2) {
            return match.getPlayerTwo();
        }
        throw new IncompleteMatchException("There is no defined winner.");
    }

}