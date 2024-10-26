package service;

import exceptions.FileProcessingException;
import exceptions.IncompleteMatchException;
import exceptions.MatchNotFoundException;
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

    public Integer addMatch(Match match) throws FileProcessingException {
        return matchRepository.create(match);
    }

    public Match findMatchById(Integer id) throws MatchNotFoundException, FileProcessingException {
        return matchRepository.find(id);
    }

    public void updateMatch(Match match) throws MatchNotFoundException, FileProcessingException {
        matchRepository.update(match);
    }

    public void deleteMatch(Integer id) throws MatchNotFoundException, FileProcessingException {
        matchRepository.delete(id);
    }

    public List<Match> getAllMatches() throws MatchNotFoundException, FileProcessingException {
        return matchRepository.getAll();
    }

    public List<Match> getMatchesByPlayer(Integer idPlayer) throws MatchNotFoundException, FileProcessingException {
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

    public Player getWinner(Match match) throws IncompleteMatchException {
        if(match.getResult()==null){
            throw new IncompleteMatchException("El partido no ha finalizado o no se cargó el resultado");
        }
        if (match.getResult().getSetsWonPlayerOne() == 2) {
            return match.getPlayerOne();
        } else if (match.getResult().getSetsWonPlayerTwo() == 2) {
            return match.getPlayerTwo();
        }
        throw new IncompleteMatchException("No hay un ganador definido");
    }

    public Player getLoser(Match match) {
        Player loserPlayer = null;
        if (match.getResult().getSetsWonPlayerOne() == 2) {
            loserPlayer = match.getPlayerTwo();
        } else if (match.getResult().getSetsWonPlayerTwo() == 2) {
            loserPlayer = match.getPlayerOne();
        }
        return loserPlayer;
    }
}
