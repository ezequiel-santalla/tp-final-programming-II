package service;

import exceptions.*;
import model.*;
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
        Tournament existingTournament = tournamentRepositoryImp.find(id);
        if (existingTournament == null) {
            throw new TournamentNotFoundException("Tournament with ID " + id + " not found.");
        }
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

    public List<Player> getPlayersStillCompeting() throws IncompleteMatchException {
        Round lastRound = this.tournament.getRounds().getLast();
        List<Player> playersStillCompeting = new ArrayList<>();

        // Verificar si es la primera ronda
        if (this.tournament.getRounds().size() == 1) {
            // Devuelve todos los jugadores en el torneo
            playersStillCompeting.addAll(this.tournament.getPlayers());
        } else {
            // Si no es la primera ronda obtiene los ganadores de la ultima ronda
            for (Match match : lastRound.getMatches()) {
                Player winner = getWinner(match);
                if (winner != null) { // verifica que el ganador no sea null
                    playersStillCompeting.add(winner);
                }
            }
        }

        return playersStillCompeting;
    }

    public void generateNextRound() throws IncompleteMatchException {
        switch (tournament.getRounds().size()) {
            case 0:
                tournament.getRounds().add(new FirstRound());
                break;
            case 1:
                tournament.getRounds().add(new QuarterFinal());
                break;
            case 2:
                tournament.getRounds().add(new Semifinal());
                break;
            default:
                tournament.getRounds().add(new Final());
                break;
        }
        // Generar los partidos de la nueva ronda y añadirla al torneo
        tournament.getRounds().getLast().generateMatches(getPlayersStillCompeting());
    }

    public Tournament getTournament() {
        return tournament;
    }


    public List<Match> getMatchesByPlayer(Integer idPlayer) throws MatchNotFoundException, FileProcessingException {

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