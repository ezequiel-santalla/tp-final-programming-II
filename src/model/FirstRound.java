package model;

import java.util.List;

public class FirstRound extends Round {

    public FirstRound() {
    }

    public FirstRound(Integer id, List<Match> matches, Integer givenPoints, Double givenMoney) {
        super(id, matches, givenPoints, givenMoney);
    }


    @Override
    public void generateMatches(List<Player> players) {
        for (int i = 0; i < players.size(); i += 2) {
            Match match = new Match(players.get(i), players.get(i + 1)); // Empareja jugadores de 2 en 2
            getMatches().add(match);
        }
    }

    @Override
    public void updatePoints() {
        /*for (Match match : getMatches()) {
            Player winner = match.getWinner();
            winner.addPoints(1);
        }*/
    }


}

