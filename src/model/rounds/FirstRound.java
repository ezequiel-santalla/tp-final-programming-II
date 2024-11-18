package model.rounds;

import model.Match;
import model.Player;

import java.util.List;

public class FirstRound extends Round {

    public FirstRound() {
        super();
    }

    public FirstRound(Integer givenPoints) {
        super(givenPoints);
    }

    @Override
    public void generateMatches(List<Player> players) {
        int id = 0;
        for (int i = 0; i < players.size(); i += 2) {
            matches.add(new Match(++id, players.get(i), players.get(i + 1), null));
        }
    }

}
