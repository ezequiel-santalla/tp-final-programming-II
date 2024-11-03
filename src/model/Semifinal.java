package model;

import java.util.ArrayList;
import java.util.List;

public class Semifinal extends Round {

    public Semifinal() {
    }

    public Semifinal(Integer givenPoints) {
        super(givenPoints);
    }

    public Semifinal(Integer id, List<Match> matches, Integer givenPoints) {
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