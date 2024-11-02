package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FirstRound extends Round {

    public FirstRound() {
    }

    public FirstRound(Integer id, List<Match> matches, Integer givenPoints, Double givenMoney) {
        super(id, matches, givenPoints, givenMoney);
    }


    @Override
    public List<Match> generateMatches(List<Player> players) {
        for (int i = 0; i < players.size(); i += 2) {
            Match match = new Match(players.get(i), players.get(i + 1));
            matches.add(match);
        }
        return matches;
    }

    @Override
    public Integer pointsEarned() {
        return 90;
    }


}
