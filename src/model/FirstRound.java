package model;

import java.util.List;

public class FirstRound extends Round {

    public FirstRound() {
        super();
    }

    public FirstRound(Integer givenPoints) {
        super(givenPoints);
    }

    public FirstRound(Integer id, List<Match> matches, Integer givenPoints) {
        super(id, matches, givenPoints);
    }

    @Override
    public List<Match> generateMatches(List<Player> players) {
        Integer id = 0;
        for (int i = 0; i < players.size(); i += 2) {
            matches.add(new Match(++id, players.get(i), players.get(i + 1), null));
        }
        return matches;
    }

}
