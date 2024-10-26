package model;

import java.util.ArrayList;
import java.util.List;

public class Final extends Round {

    public Final() {
    }

    public Final(Integer id, List<Match> matches, Integer givenPoints, Double givenMoney) {
        super(id, matches, givenPoints, givenMoney);
    }

    @Override
    public List<Match> generateMatches(List<Player> players) {
        List<Match> matches = new ArrayList<>();
        if (players.size() == 2) {
            Match finalMatch = new Match(players.get(0), players.get(1));
            matches.add(finalMatch);
        }
        return matches;
    }

    @Override
    public Integer pointsEarned() {
        return 1200;
    }
}
