package model;

import java.util.ArrayList;
import java.util.List;

public class Final extends Round {

    public Final() {
        super();
    }

    public Final(Integer givenPoints) {
        super(givenPoints);
    }

    public Final(Integer id, List<Match> matches, Integer givenPoints) {
        super(id, matches, givenPoints);
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

}
