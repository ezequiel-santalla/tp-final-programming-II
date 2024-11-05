package model.rounds;

import model.Match;
import model.Player;

import java.util.List;

public class QuarterFinal extends Round {

    public QuarterFinal() {
        super();
    }

    public QuarterFinal(Integer givenPoints) {
        super(givenPoints);
    }

    public QuarterFinal(Integer id, List<Match> matches, Integer givenPoints) {
        super(id, matches, givenPoints);
    }

    @Override
    public void generateMatches(List<Player> players) {
        int id = 8;
        for (int i = 0; i < players.size(); i += 2) {
            matches.add(new Match(++id, players.get(i), players.get(i + 1), null));
        }
    }
}