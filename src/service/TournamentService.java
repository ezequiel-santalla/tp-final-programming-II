package service;

import exceptions.*;
import model.*;
import repository.TournamentRepositoryImp;

import java.util.List;

public class TournamentService {
    private final TournamentRepositoryImp tournamentRepositoryImp;
    private TournamentPlayerService tournamentPlayerService;
    private RoundService roundService;
    private MatchService matchService;
    private TournamentStatusService tournamentStatusService;
    private Tournament tournament;

    public TournamentService(TournamentRepositoryImp tournamentRepositoryImp) {
        this.tournamentRepositoryImp = tournamentRepositoryImp;
    }

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
        this.tournament = tournament;
        initServices();
        return tournamentRepositoryImp.create(tournament);
    }

    public Tournament findTournamentById(Integer id) throws TournamentNotFoundException, FileProcessingException {
        tournament = tournamentRepositoryImp.find(id);
        initServices();
        return tournament;
    }

    public void updateTournament(Tournament tournament) throws TournamentNotFoundException, FileProcessingException {

        if (this.tournament == null) {
            throw new TournamentNotFoundException("No tournament selected for update.");
        }
        if (tournament == null) {
            throw new IllegalArgumentException("Tournament cannot be null.");
        }
        this.setTournament(tournament);
        tournamentRepositoryImp.update(tournament);
    }

    public void deleteTournament(Integer id) throws TournamentNotFoundException, FileProcessingException {
        tournamentRepositoryImp.delete(id);
    }

    public List<Tournament> getAllTournaments() throws TournamentNotFoundException, FileProcessingException {
        return tournamentRepositoryImp.getAll();
    }

    public void registerPlayerInTournament(Player player) throws TournamentFullException, DuplicatePlayerException, TournamentNotFoundException {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }
        tournamentPlayerService.registerPlayer(player);
        updateTournament(tournament);
    }

    public void unsubscribePlayerFromTournament(Integer idPlayer) throws TournamentNotFoundException, PlayerNotFoundException {
        tournamentPlayerService.unsubscribePlayer(idPlayer);
        updateTournament(tournament);
    }

    public void assignResultToMatch(Integer matchId, Result result) throws InvalidTournamentStatusException, MatchNotFoundException, TournamentNotFoundException, InvalidResultException {
        matchService.assignResult(matchId, result);
        updateTournament(tournament);
    }

    public void modifyResultToMatch(Integer matchId, Result result) throws InvalidTournamentStatusException, MatchNotFoundException, TournamentNotFoundException, InvalidResultException {
        matchService.modifyResult(matchId, result);
        updateTournament(tournament);
    }

    public void advanceTournament() throws IncompleteMatchException, InvalidTournamentStatusException, TournamentFullException, TournamentNotFoundException {
        tournamentStatusService.advanceTournament();
        updateTournament(tournament);
    }


    public Player getTournamentWinner() throws InvalidTournamentStatusException, IncompleteMatchException {
        return tournamentStatusService.getTournamentWinner();
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        initServices();
    }

    public void setTournamentById(Integer idTournament) throws TournamentNotFoundException {
        setTournament(findTournamentById(idTournament));
    }

    public Tournament getTournament() {
        return tournament;
    }

    public TournamentPlayerService getTournamentPlayerService() {
        return tournamentPlayerService;
    }

    public RoundService getTournamentRoundService() {
        return roundService;
    }

    public MatchService getTournamentMatchService() {
        return matchService;
    }

    private void initServices() {
        tournamentPlayerService = new TournamentPlayerService(tournament);
        matchService = new MatchService(tournament);
        roundService = new RoundService(tournament, matchService);
        tournamentStatusService = new TournamentStatusService(tournament, roundService, matchService);
    }
}