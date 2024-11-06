package service;

import enums.ETournamentStatus;
import exceptions.*;
import model.*;
import model.rounds.*;
import repository.TournamentRepositoryImp;

import java.util.ArrayList;
import java.util.List;

public class TournamentService {
    private final TournamentRepositoryImp tournamentRepositoryImp;
    private Tournament tournament;

    public TournamentService(TournamentRepositoryImp tournamentRepositoryImp, Tournament tournament) {
        this.tournamentRepositoryImp = tournamentRepositoryImp;
        this.tournament = tournament;
        addTournament(tournament);
    }

    public TournamentService(TournamentRepositoryImp tournamentRepositoryImp, Integer idTournament) throws TournamentNotFoundException {
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

    public void registerPlayer(Player player) throws TournamentFullException {
        if (tournament.getPlayers().size() < 16) {
            tournament.getPlayers().add(player);
        } else {
            throw new TournamentFullException("Tournament is full");
        }
    }

    private boolean allMatchesCompleted() {
        for (Match match : getCurrentRound().getMatches()) {
            if (match.getResult() == null || !match.getResult().thereIsAWinner()) {
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
                Player winner = getWinner(match);
                playersStillCompeting.add(winner);
            }
        }

        return playersStillCompeting;
    }

    private void nextRound() throws IncompleteMatchException, InvalidTournamentStatusException {
        // Agregar la ronda actual al torneo
        Round nextRound = generateNextRound();
        nextRound.generateMatches(getPlayersStillCompeting());
        tournament.getRounds().add(nextRound);
        nextRound.setId(tournament.getRounds().size());
        // Comprobar si esta es la ronda final y actualizar el estado según corresponda
        if (nextRound instanceof Final && allMatchesCompleted()) {
            tournament.setStatus(ETournamentStatus.FINISHED);
        }
    }


    private Round generateNextRound() throws InvalidTournamentStatusException {

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
            default -> throw new InvalidTournamentStatusException("Can not create more rounds");
        }
    }

    public void advanceTournament() throws IncompleteMatchException, InvalidTournamentStatusException, TournamentFullException {
        switch (tournament.getStatus()) {
            case NOT_STARTED -> {
                if (tournament.getPlayers().size() < 16) {
                    throw new TournamentFullException("Not enough players to start the tournament.");
                }
                tournament.setStatus(ETournamentStatus.IN_PROGRESS);
                nextRound();
            }
            case IN_PROGRESS -> {
                if (!allMatchesCompleted()) {
                    throw new IncompleteMatchException("Not all matches have been completed.");
                }
                nextRound();
            }
            case FINISHED -> throw new InvalidTournamentStatusException(tournament.getStatus().getMessage());
            default -> throw new IllegalStateException("Unexpected tournament status: " + tournament.getStatus());
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

    public Player getTournamentWinner() throws InvalidTournamentStatusException, IncompleteMatchException {
        if (tournament.getStatus().equals(ETournamentStatus.FINISHED)) {
            return getWinner(getCurrentRound().getMatches().getLast());
        }
        throw new InvalidTournamentStatusException("Tournament has not finished yet.");
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

    public void assignResultToMatch(Integer matchId, Result result) throws MatchNotFoundException, IncompleteMatchException, InvalidTournamentStatusException {
        if(tournament.getStatus().equals(ETournamentStatus.FINISHED)){
            throw new InvalidTournamentStatusException(tournament.getStatus().getMessage());
        }
        Match match = findMatchById(matchId);
        if (match == null) {
            throw new MatchNotFoundException("Match not found with ID: " + matchId);
        }

        // Asignar el resultado al partido
        match.setResult(result);

        updateTournamentStatus();
        // Asignar puntos al ganador
        Player winner = getWinner(match);
        winner.setPoints(winner.getPoints() + getCurrentRound().getGivenPoints());

    }

    private Match findMatchById(Integer matchId) throws InvalidTournamentStatusException {
        for (Round round : tournament.getRounds()) {
            for (Match match : round.getMatches()) {
                if (match.getIdMatch().equals(matchId)) {
                    if(!round.equals(getCurrentRound())){
                        throw new InvalidTournamentStatusException("The match belongs to a finished round");
                    }
                    return match;
                }
            }
        }
        return null; // Si no se encuentra el partido
    }

    private Round getCurrentRound() {
        return tournament.getRounds().getLast(); // Asumiendo que la última ronda es la actual
    }

    private void updateTournamentStatus() {
        if (!tournament.getRounds().isEmpty() && getCurrentRound() instanceof Final && allMatchesCompleted()) {
            tournament.setStatus(ETournamentStatus.FINISHED);
        }
    }

}