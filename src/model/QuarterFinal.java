package model;

import java.util.ArrayList;
import java.util.List;

public class QuarterFinal extends Round {

    public QuarterFinal() {}

    public QuarterFinal(Integer givenPoints) {
        super(givenPoints);
    }

    public QuarterFinal(Integer id, List<Match> matches, Integer givenPoints) {
        super(id, matches, givenPoints);
    }

    @Override
    public List<Match> generateMatches(List<Player> players) {
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < players.size(); i += 2) {
            Match match = new Match(players.get(i), players.get(i + 1));
            matches.add(match);
        }
        return matches;
    }
}