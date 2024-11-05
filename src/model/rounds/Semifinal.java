package model.rounds;

import model.Match;
import model.Player;

import java.util.List;

public class Semifinal extends Round {

    public Semifinal() {
        super();
    }

    public Semifinal(Integer givenPoints) {
        super(givenPoints);
    }

    public Semifinal(Integer id, List<Match> matches, Integer givenPoints) {
        super(id, matches, givenPoints);
    }

    @Override
    public void generateMatches(List<Player> players) {
        int id = 12;
        for (int i = 0; i < players.size(); i += 2) {
            matches.add(new Match(++id, players.get(i), players.get(i + 1), null));
        }
    }
}