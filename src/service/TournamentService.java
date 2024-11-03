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
import java.util.Map;

public class TournamentService {
    private final TournamentRepositoryImp tournamentRepositoryImp;
    private final MatchService matchService;
    private Tournament tournament;


    public TournamentService(TournamentRepositoryImp tournamentRepositoryImp, MatchService matchService) {
        this.matchService = matchService;
        this.tournamentRepositoryImp = tournamentRepositoryImp;
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
        for (Map.Entry<Round, List<Match>> entry : tournament.getRounds().entrySet()) {
            for (Match match : entry.getValue()) {
                Player player = matchService.getWinner(match);
                player.setPoints(player.getPoints() + entry.getKey().getGivenPoints());
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

    public List<Player> getPlayersStillCompeting() throws IncompleteMatchException {
        // Obtener todas las rondas
        List<Round> rounds = new ArrayList<>(this.tournament.getRounds().keySet());

        // Verificar si hay rondas disponibles
        if (rounds.isEmpty()) {
            throw new IncompleteMatchException("No hay rondas disponibles.");
        }

        // Obtener la última ronda
        Round lastRound = rounds.getLast();

        // Obtener los partidos de la última ronda
        List<Match> lastRoundMatches = this.tournament.getRounds().get(lastRound);

        List<Player> playersStillCompeting = new ArrayList<>();
        for (Match match : lastRoundMatches) {
            Player winner = matchService.getWinner(match);
            if (winner != null) { // Asegúrate de que el ganador no sea null
                playersStillCompeting.add(winner);
            }
        }
        return playersStillCompeting;
    }


    public void generateNextRound() throws IncompleteMatchException {
        int numberOfRounds = tournament.getRounds().size();
        Round nextRound = switch (numberOfRounds) {
            case 0 -> new FirstRound();
            case 1 -> new QuarterFinal();
            case 2 -> new Semifinal();
            default -> new Final();
        };
        tournament.getRounds().put(nextRound, new ArrayList<>());
        generateMatches(getPlayersStillCompeting(), nextRound);
    }


    public void generateMatches(List<Player> playerList, Round currentRound) {
        for (int i = 0; i < playerList.size(); i += 2) {
            tournament.getRounds().get(currentRound).add(new Match(playerList.get(i), playerList.get(i + 1)));
        }
    }


    public void generateTournament(String name, String location, ESurface surface, LocalDate startDate, LocalDate endDate) {
        this.tournament = new Tournament(name, location, surface, startDate, endDate);
        tournamentRepositoryImp.create(tournament);
    }

    public Tournament getTournament() {
        return tournament;
    }


}