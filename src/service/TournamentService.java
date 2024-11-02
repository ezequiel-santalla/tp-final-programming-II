package service;


import enums.ESurface;
import exceptions.TournamentNotFoundException;
import model.*;
import repository.MatchRepositoryImp;
import repository.TournamentRepositoryImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TournamentService {
    private TournamentRepositoryImp tournamentRepositoryImp;
    private final MatchService matchService;
    private MatchRepositoryImp matchRepositoryImp;
    private Tournament tournament;


    public TournamentService(TournamentRepositoryImp tournamentRepositoryImp, MatchService matchService, Tournament tournament) {
        this.tournament = tournament;
        this.matchService = matchService;
        this.tournamentRepositoryImp = tournamentRepositoryImp;
    }

    public Integer addTournament(Tournament tournament) {
        return tournamentRepositoryImp.create(tournament);
    }

    public Tournament findTournamentById(Integer id) {
        Tournament tournament = tournamentRepositoryImp.find(id);
        if (tournament == null) {
            throw new TournamentNotFoundException("Tournament with ID " + id + " not found.");
        }
        return tournament;
    }

    public void updateTournament(Tournament tournament) {
        Tournament existingTournament = tournamentRepositoryImp.find(tournament.getId());
        if (existingTournament == null) {
            throw new TournamentNotFoundException("Tournament with ID " + tournament.getId() + " not found.");
        }
        tournamentRepositoryImp.update(tournament);
    }

    public void deleteTournament(Integer id) {
        Tournament existingTournament = tournamentRepositoryImp.find(id);
        if (existingTournament == null) {
            throw new TournamentNotFoundException("Tournament with ID " + id + " not found.");
        }
        tournamentRepositoryImp.delete(id);
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepositoryImp.getAll();
    }

    public void assignPoints(List<Round> rounds) {
        for (Round round : rounds) {
            for (Match match : round.getMatches()) {
                Player winner = matchService.getWinner(match);
                Player loser = matchService.getLoser(match);

                if (winner != null && loser != null) {
                    assignPointsToLoser(round, loser);
                    assignPointsToWinner(round, winner);
                }
            }
        }
    }

    public void assignPointsToLoser(Round round, Player loser) {
        Integer points = calculatePointsForLoser(round);
        if (points != null) {
            loser.setPoints(points);
        }
    }

    public void assignPointsToWinner(Round round, Player winner) {
        if (round instanceof Final) {
            Integer points = calculatePointsForWinner((Final) round);
            if (points != null) {
                winner.setPoints(points);
            }
        }
    }

    public Integer calculatePointsForLoser(Round round) {
        FirstRound firstRound = new FirstRound();
        QuarterFinal quarterFinal = new QuarterFinal();
        Semifinal semifinal = new Semifinal();
        Final finalRound = new Final();

        int points = 0;

        if (round.getId().equals(firstRound.getId())) {
            points = firstRound.pointsEarned();
        } else if (round.getId().equals(quarterFinal.getId())) {
            points = firstRound.pointsEarned() + quarterFinal.pointsEarned();
        } else if (round.getId().equals(semifinal.getId())) {
            points = firstRound.pointsEarned() + quarterFinal.pointsEarned() + semifinal.pointsEarned();
        } else if (round.getId().equals(finalRound.getId())) {
            points = firstRound.pointsEarned() + quarterFinal.pointsEarned() + semifinal.pointsEarned() + finalRound.pointsEarned();
        }

        return points;
    }

    public  Integer calculatePointsForWinner(Final finalRound) {
        FirstRound firstRound = new FirstRound();
        QuarterFinal quarterFinal = new QuarterFinal();

        return firstRound.pointsEarned() + quarterFinal.pointsEarned() + (finalRound.pointsEarned() * 2);
    }

    private boolean validatePlayersCount(Tournament tournament) {
        return tournament.getPlayers().size() == 16;
    }

    public void registerPlayer(Tournament tournament, Player player) {
        if (tournament.getPlayers().size() < 16) {
            tournament.getPlayers().add(player);
        } else {
            throw new IllegalStateException("Tournament is full.");
        }
    }

    public List<Player> getPlayersStillCompeting() {
        Round lastRound = this.tournament.getRounds().getLast();
        List<Player> playersStillCompeting = new ArrayList<>();
        for (Match match : lastRound.getMatches()) {
            playersStillCompeting.add(matchService.getWinner(match));
        }
        return playersStillCompeting;
    }

    public void generateNextRound() {
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



    /*
    SEGUNDA ALTERNATIVA(LA MENOS FACTIBLE)
    public void generateRound(Tournament tournament) {
        if (tournament.getRounds().isEmpty()) {
            // se genera la primera ronda aleatoria
            FirstRound firstRound = new FirstRound();
            ArrayList<Player> players = new ArrayList<>(tournament.getPlayers());
            firstRound.generateMatches(players);
            tournament.getRounds().add(firstRound);
        } else {
            // se organiza por fixture predefinido
           Round lastRound = tournament.getRounds().get(tournament.getRounds().size() - 1);
           Round nextRound = lastRound.generateNextRound();
           tournament.getRounds().add(nextRound);
        }
    }

    public void generateNextRound(Tournament tournament) {

        int currentRoundIndex = tournament.getRounds().size();

        if (currentRoundIndex == 0) {
            FirstRound firstRound = new FirstRound();
            ArrayList<Player> players = new ArrayList<>(tournament.getPlayers());
            firstRound.generateMatches(players);
            tournament.getRounds().add(firstRound);
        } else if (currentRoundIndex == 1) {
            QuarterFinal quarterFinal = new QuarterFinal();
            quarterFinal.generateMatches(getPlayersStillCompeting(tournament));
            tournament.getRounds().add(quarterFinal);
        } else if (currentRoundIndex == 2) {
            Semifinal semiFinal = new Semifinal();
            semiFinal.generateMatches(getPlayersStillCompeting(tournament));
            tournament.getRounds().add(semiFinal);
        } else if (currentRoundIndex == 3) {
            Final finalRound = new Final();
            finalRound.generateMatches(getPlayersStillCompeting(tournament));
            tournament.getRounds().add(finalRound);
        }
    }*/

    public void generateTournament(String name, String surface, LocalDate startDate, LocalDate endDate) {
        ESurface tournamentSurface = ESurface.valueOf(surface.toUpperCase());
        this.tournament = new Tournament(1, name, "Default Location", tournamentSurface, startDate, endDate, new HashSet<>(), new ArrayList<>());
    }

    public Tournament getTournament() {
        return tournament;
    }

}