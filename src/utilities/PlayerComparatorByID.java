package utilities;

import model.Player;

import java.util.Comparator;

public class PlayerComparatorByID implements Comparator<Player> {
    @Override
    public int compare(Player player1, Player player2) {
        System.out.println(player1.getIdPlayer()+"   "+player2.getIdPlayer());

        return player1.getIdPlayer().compareTo(player2.getIdPlayer());
    }
}
