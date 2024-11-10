package service;

import exceptions.*;
import model.*;
import repository.TournamentRepositoryImp;

import java.util.List;

public class TournamentService {
    private final TournamentRepositoryImp tournamentRepositoryImp;
    private TournamentPlayerService tournamentPlayerService;
    private TournamentRoundService tournamentRoundService;
    private TournamentMatchService tournamentMatchService;
    private TournamentStatusService tournamentStatusService;
    private Tournament tournament;

    public TournamentService(TournamentRepositoryImp tournamentRepositoryImp, Tournament tournament) {
        this.tournamentRepositoryImp = tournamentRepositoryImp;
        this.tournament = tournament;
        addTournament(tournament);
        initServices();
    }

    public TournamentService(TournamentRepositoryImp tournamentRepositoryImp, Integer idTournament) throws TournamentNotFoundException {
        this.tournamentRepositoryImp = tournamentRepositoryImp;
        this.tournament = findTournamentById(idTournament);
        initServices();
    }

    public Integer addTournament(Tournament tournament) throws FileProcessingException {
        return tournamentRepositoryImp.create(tournament);
    }

    public Tournament findTournamentById(Integer id) throws TournamentNotFoundException, FileProcessingException {
        tournament = tournamentRepositoryImp.find(id);
        return tournament;
    }

    public void updateTournament(Tournament tournament) throws TournamentNotFoundException, FileProcessingException {
        tournamentRepositoryImp.update(tournament);
    }

    public void deleteTournament(Integer id) throws TournamentNotFoundException, FileProcessingException {
        tournamentRepositoryImp.delete(id);
    }

    public List<Tournament> getAllTournaments() throws TournamentNotFoundException, FileProcessingException {
        return tournamentRepositoryImp.getAll();
    }

    public void registerPlayerInTournament(Player player) throws TournamentFullException, DuplicatePlayerException, TournamentNotFoundException {
        tournamentPlayerService.registerPlayer(player);
        updateTournament(tournament);
    }

    public void unsubscribePlayerFromTournament(Integer idPlayer) throws MatchNotFoundException, TournamentNotFoundException {
        tournamentPlayerService.unsubscribePlayer(idPlayer);
        updateTournament(tournament);
    }

    public void assignResultToMatch(Integer matchId, Result result) throws InvalidTournamentStatusException, MatchNotFoundException, TournamentNotFoundException, InvalidResultException {
        tournamentMatchService.assignResult(matchId, result);
        updateTournament(tournament);
    }

    public void modifyResultToMatch(Integer matchId, Result result) throws InvalidTournamentStatusException, MatchNotFoundException, TournamentNotFoundException, InvalidResultException {
        tournamentMatchService.modifyResult(matchId, result);
        updateTournament(tournament);
    }

    public void advanceTournament() throws IncompleteMatchException, InvalidTournamentStatusException, TournamentFullException, TournamentNotFoundException {
        tournamentStatusService.advanceTournament();
        updateTournament(tournament);
    }


    public Player getTournamentWinner() throws InvalidTournamentStatusException, IncompleteMatchException {
        return tournamentStatusService.getTournamentWinner();
    }

   public Tournament getTournament() {
        return tournament;
    }

    public TournamentPlayerService getTournamentPlayerService() {
        return tournamentPlayerService;
    }

    public TournamentRoundService getTournamentRoundService() {
        return tournamentRoundService;
    }

    public TournamentMatchService getTournamentMatchService() {
        return tournamentMatchService;
    }

    private void initServices() {
        tournamentPlayerService = new TournamentPlayerService(tournament);
        tournamentMatchService = new TournamentMatchService(tournament);
        tournamentRoundService = new TournamentRoundService(tournament, tournamentMatchService);
        tournamentStatusService = new TournamentStatusService(tournament, tournamentRoundService, tournamentMatchService);
    }
}