package model;

import java.util.List;

public class Final extends Round {

    public Final(Integer id, List<Match> matches, Integer givenPoints, Double givenMoney) {
        super(id, matches, givenPoints, givenMoney);
    }

    @Override
    public void generateMatches(List<Player> players) {
        // Lógica para la final, donde solo habrá un partido entre dos jugadores
        if (players.size() == 2) {
            Match finalMatch = new Match(players.get(0), players.get(1));
            getMatches().add(finalMatch);
        }
    }

    @Override
    public void updatePoints() {
      /*  for (Match match : getMatches()) {
            Player winner = match.getWinner();
            winner.addPoints(5);
        }*/
    }
}
