package service;

import exceptions.DuplicatePlayerException;
import exceptions.MatchNotFoundException;
import exceptions.TournamentFullException;
import model.Player;
import model.Tournament;

import java.util.Iterator;


public class TournamentPlayerService {
    private final Tournament tournament;

    public TournamentPlayerService(Tournament tournament) {
        this.tournament = tournament;
    }


    public void registerPlayer(Player player) throws TournamentFullException, DuplicatePlayerException {
        if (tournament.getPlayers().size() < 16) {
            if (!tournament.getPlayers().add(player)) {
                throw new DuplicatePlayerException(player.getDni());
            }
        } else {
            throw new TournamentFullException("Tournament is full");
        }
    }

    public void unsubscribePlayer(Integer idPlayer) throws MatchNotFoundException {

        Iterator<Player> iterator = tournament.getPlayers().iterator();
        boolean playerFound = false;

        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getIdPlayer().equals(idPlayer)) {
                iterator.remove();
                playerFound = true;
                break;
            }
        }
        if (!playerFound) {
            throw new MatchNotFoundException(idPlayer);
        }
    }
}