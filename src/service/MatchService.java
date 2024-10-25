package service;

import model.Match;
import model.Player;
import repository.MatchRepositoryImp;

import java.util.ArrayList;
import java.util.List;

public class MatchService {

    private final MatchRepositoryImp matchRepository;
    public MatchService(MatchRepositoryImp matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Integer addMatch(Match match) {
        Integer id = null;
        id = matchRepository.create(match);

        return id;
    }

    public Match findMatchById(Integer id) {
        return matchRepository.find(id);
    }

    public void updateMatch(Match match) {
        matchRepository.update(match);
    }

    public void deleteMatch(Integer id) {
        matchRepository.delete(id);
    }

    public List<Match> getAllMatches() {
        return matchRepository.getAll();
    }

    public List<Match> getMatchesByPlayer(Integer idPlayer) {
        List<Match> matches = matchRepository.getAll();
        List<Match> playerMatches = new ArrayList<>();
        for (Match match : matches) {
            if (match.getPlayerOne().getId().equals(idPlayer) ||
                    match.getPlayerTwo().getId().equals(idPlayer)) {
                playerMatches.add(match);
            }
        }
        return playerMatches;
    }

    public Player getWinner(Match match) {
        Player winnerPlayer = null;
        if (match.getResult().getSetsWonPlayerOne() == 2) {
            winnerPlayer = match.getPlayerOne();
        } else if (match.getResult().getSetsWonPlayerTwo() == 2) {
            winnerPlayer = match.getPlayerTwo();
        }
        return winnerPlayer;
    }

}
