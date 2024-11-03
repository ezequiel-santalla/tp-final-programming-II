package service;

import enums.ESurface;
import exceptions.FileProcessingException;
import exceptions.IncompleteMatchException;
import exceptions.TournamentNotFoundException;
import model.*;
import repository.TournamentRepositoryImp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TournamentService {
    private final TournamentRepositoryImp tournamentRepositoryImp;
    private final MatchService matchService;
    private Tournament tournament;


    public TournamentService(TournamentRepositoryImp tournamentRepositoryImp, MatchService matchService) {
        this.matchService = matchService;
        this.tournamentRepositoryImp = tournamentRepositoryImp;
    }


    public Integer addTournament(Tournament tournament) throws FileProcessingException{
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

    public List<Tournament> getAllTournaments() throws TournamentNotFoundException, FileProcessingException{
        return tournamentRepositoryImp.getAll();
    }


    public void assignPoints() throws IncompleteMatchException {
        for (Round round : tournament.getRounds()) {
            for (Match match : round.getMatches()) {
                Player player = matchService.getWinner(match);
                // se puede simplificar agregando addPoints() en Player
                player.setPoints(player.getPoints() + round.getGivenPoints());
            }
        }
    }


    public void registerPlayer(Player player) {
        if (tournament.getPlayers().size() < 16) {
            tournament.getPlayers().add(player);
        } else {
            throw new IllegalStateException("Tournament is full");
        }
    }

    public List<Player> getPlayersStillCompeting() throws IncompleteMatchException{
        Round lastRound = this.tournament.getRounds().getLast();
        List<Player> playersStillCompeting = new ArrayList<>();
        for (Match match : lastRound.getMatches()) {
            playersStillCompeting.add(matchService.getWinner(match));
        }
        return playersStillCompeting;
    }

    public void generateNextRound() throws IncompleteMatchException{
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

    public void generateTournament(String name, String surface, LocalDate startDate, LocalDate endDate) {
        ESurface tournamentSurface = ESurface.valueOf(surface);
        this.tournament = new Tournament(1, name, "Default Location", tournamentSurface, startDate, endDate);
    }

    public Tournament getTournament() {
        return tournament;
    }

}