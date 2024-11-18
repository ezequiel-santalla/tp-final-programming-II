package service;

import exceptions.FileProcessingException;
import exceptions.IncompleteMatchException;
import exceptions.PlayerNotFoundException;
import exceptions.TournamentNotFoundException;
import model.Match;
import model.Player;
import model.Tournament;
import model.rounds.Round;
import repository.PlayerRepositoryImp;

import java.util.ArrayList;
import java.util.List;

public class PlayerService {
    private final PlayerRepositoryImp playerRepository;
    private final TournamentService tournamentService;

    public PlayerService(PlayerRepositoryImp playerRepository, TournamentService tournamentService) {
        this.playerRepository = playerRepository;
        this.tournamentService = tournamentService;
    }


    public Integer addPlayer(Player player) {
        return playerRepository.create(player);
    }

    public Player findPlayerById(Integer id) throws PlayerNotFoundException {
        return playerRepository.find(id);
    }

    public void updatePlayer(Player player) throws PlayerNotFoundException {
        playerRepository.update(player);
    }

    public void deletePlayer(Integer id) throws PlayerNotFoundException {
        playerRepository.delete(id);
    }

    public List<Player> getAllPlayers() throws PlayerNotFoundException {
        return playerRepository.getAll();
    }

    private Integer getMatchesWon(Integer id) throws TournamentNotFoundException {
        List<Match> matchesByPlayer = getMatchesByPlayer(id);
        Integer matchesWon = 0;

        for (Match m : matchesByPlayer) {
            int idWinner = 0;
            try {
                idWinner = getWinner(m).getIdPlayer();
            } catch (IncompleteMatchException e) {

            }

            if (idWinner == id) {
                matchesWon++;
            }
        }
        return matchesWon;
    }


    public Player getWinner(Match match) throws IncompleteMatchException {
        if (match.getResult() == null) {
            throw new IncompleteMatchException("The match has not finished or the result was not loaded.");
        }

        // Check if player one has won two sets
        if (match.getResult().getSetsWonPlayerOne() == 2) {
            return match.getPlayerOne();
        }
        // Check if player two has won two sets
        else if (match.getResult().getSetsWonPlayerTwo() == 2) {
            return match.getPlayerTwo();
        }

        // If there is no defined winner...
        throw new IncompleteMatchException("There is no defined winner.");
    }

    public List<Match> getMatchesByPlayer(Integer idPlayer) throws FileProcessingException, TournamentNotFoundException {

        List<Match> playerMatches = new ArrayList<>();
        for (Tournament tournament : tournamentService.getAllTournaments())
            for (Round round : tournament.getRounds()) {
                for (Match match : round.getMatches()) {
                    if (match.getPlayerOne().getIdPlayer().equals(idPlayer) || match.getPlayerTwo().getIdPlayer().equals(idPlayer)) {
                        playerMatches.add(match);
                    }
                }
            }
        return playerMatches;
    }


    public String showStatsByPlayer(Integer id) throws IncompleteMatchException, PlayerNotFoundException, TournamentNotFoundException {
        Player player = findPlayerById(id);

        int matchesPlayed = getMatchesByPlayer(id).size();
        int matchesWon = getMatchesWon(id);
        int matchesLost = matchesPlayed - matchesWon;
        double percentageWon = (matchesPlayed > 0) ? ((double) matchesWon / matchesPlayed) * 100 : 0;
        int totalPoints = player.getPoints();

        int maxNameLength = 24;

        String formattedName = String.format("%s %s", player.getName(), player.getLastName());
        if (formattedName.length() > maxNameLength) {
            formattedName = formattedName.substring(0, maxNameLength - 3) + "...";
        }

        int padding = maxNameLength - formattedName.length();

        return String.format("\n" +
                """
                        ---------------------------------
                        |        Player Statistics      |
                        ---------------------------------
                        | Name: %s%s|
                        |                               |
                        | Matches Played      : %-7d |
                        | Matches Won         : %-7d |
                        | Matches Lost        : %-7d |
                        | Won/Lost Percentage : %-7.2f%% |
                        | Total Points        : %-7d |
                        ---------------------------------
                        """, formattedName, " ".repeat(padding), matchesPlayed, matchesWon, matchesLost, percentageWon, totalPoints
        );
    }

    private List<Player> getPlayerRankings() throws PlayerNotFoundException {
        List<Player> players = getAllPlayers();

        players.sort((p1, p2) -> p2.getPoints().compareTo(p1.getPoints()));

        return players;
    }

    public String showPlayerRankings() throws PlayerNotFoundException {
        List<Player> rankedPlayerList = getPlayerRankings();

        final int NAME_COLUMN_WIDTH = 25;
        final int POINTS_COLUMN_WIDTH = 6;

        StringBuilder rankingStr = new StringBuilder();

        rankingStr.append("\n" +
                """
                        --------------------------------------------
                        |                  Ranking                 |
                        --------------------------------------------
                        | Pos | Name                      | Points |
                        --------------------------------------------
                        """);

        for (int i = 0; i < rankedPlayerList.size(); i++) {
            Player player = rankedPlayerList.get(i);

            rankingStr.append(String.format("| %-4d| %-" + NAME_COLUMN_WIDTH + "s | %-" + POINTS_COLUMN_WIDTH + "d |\n",
                    (i + 1), player.getName() + " " + player.getLastName(), player.getPoints()));
        }

        rankingStr.append("--------------------------------------------\n");

        return rankingStr.toString();
    }
}
