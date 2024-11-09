package model.rounds;

import model.Match;
import model.Player;

import java.util.List;

public class Final extends Round {

    public Final() {
        super();
    }

    public Final(Integer givenPoints) {
        super(givenPoints);
    }

    public Final(List<Match> matches, Integer givenPoints) {
        super(matches, givenPoints);
    }

    @Override
    public void generateMatches(List<Player> players) {
        int id = 14;
        for (int i = 0; i < players.size(); i += 2) {
            matches.add(new Match(++id, players.get(i), players.get(i + 1), null));
        }
    }

}
