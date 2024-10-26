package service;


import enums.ESurface;
import model.*;
import repository.MatchRepositoryImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TournamentService {
    private final MatchRepositoryImp matchRepository;
    private final MatchService matchService;


    public TournamentService(MatchRepositoryImp matchRepository, MatchService matchService) {
        this.matchService = matchService;
        this.matchRepository = matchRepository;
    }

    public Integer addMatch(Match match) {
        return matchRepository.create(match);
    }

    public Match findMatchById(Integer id) {
        return matchRepository.find(id);
    }

    public void updateMatch(Match match) {
        matchRepository.update(match);
    }

    public void deleteMatch(Integer id) {
        matchRepository.delete(id);
    }

    public List<Match> getAllMatches() {
        return matchRepository.getAll();
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

    public List<Player> getPlayersStillCompeting(Tournament tournament) {
        Round lastRound = tournament.getRounds().get(tournament.getRounds().size() - 1);
        List<Player> playersStillCompeting = new ArrayList<>();
        MatchService matchService = new MatchService(matchRepository);
        for (Match match : lastRound.getMatches()) {
            playersStillCompeting.add(matchService.getWinner(match));
        }

        return playersStillCompeting;
    }

  /*
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
            quarterFinal.generateMatches(tournamentService.getPlayersStillCompeting(tournament));
            tournament.getRounds().add(quarterFinal);
        } else if (currentRoundIndex == 2) {
            Semifinal semiFinal = new Semifinal();
            semiFinal.generateMatches(tournamentService.getPlayersStillCompeting(tournament));
            tournament.getRounds().add(semiFinal);
        } else if (currentRoundIndex == 3) {
            Final finalRound = new Final();
            finalRound.generateMatches(tournamentService.getPlayersStillCompeting(tournament));
            tournament.getRounds().add(finalRound);
        }
    }

    QUEDA EN PENDIENTE PARA ANZALIZAR LA LOGICA SIN CONVIENE METER UN ROUND SERVICE
*/


    public void generateTournament(String name, String surface, LocalDate startDate, LocalDate endDate) {
        ESurface tournamentSurface = ESurface.valueOf(surface.toUpperCase());
        Tournament tournament = new Tournament(1, name, "Default Location", tournamentSurface, startDate, endDate, 0.0, new HashSet<>(), new ArrayList<>());
    }

}