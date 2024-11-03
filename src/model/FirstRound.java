package model;

import java.util.List;

public class FirstRound extends Round {

    public FirstRound() {
    }

    public FirstRound(Integer givenPoints) {
        super(givenPoints);
    }

    public FirstRound(Integer id, List<Match> matches, Integer givenPoints) {
        super(id, matches, givenPoints);
    }

    @Override
    public List<Match> generateMatches(List<Player> players) {
        for (int i = 0; i < players.size(); i += 2) {
            Match match = new Match(players.get(i), players.get(i + 1));
            matches.add(match);
        }
        return matches;
    }

}
