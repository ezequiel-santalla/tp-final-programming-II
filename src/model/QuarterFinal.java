package model;

import java.util.List;

public class QuarterFinal extends Round {

    public QuarterFinal(Integer id, List<Match> matches, Integer givenPoints, Double givenMoney) {
        super(id, matches, givenPoints, givenMoney);
    }

    @Override
    public void generateMatches(List<Player> players) {
        for (int i = 0; i < players.size(); i += 2) {
            Match match = new Match(players.get(i), players.get(i + 1));
            getMatches().add(match);
        }
    }

    @Override
    public void updatePoints() {
      /*  for (Match match : getMatches()) {
            Player winner = match.getWinner();
            winner.addPoints(2);
        }*/
    }
}
