package service;


import model.*;
import repository.MatchRepositoryImp;

import java.util.List;

public class TournamentService {
    private MatchRepositoryImp matchRepository;
    private MatchService matchService;

    public TournamentService(){}

    public TournamentService(MatchRepositoryImp matchRepository, MatchService matchService) {
        this.matchService = matchService;
        this.matchRepository = matchRepository;
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
}