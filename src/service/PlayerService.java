package service;

import exceptions.IncompleteMatchException;
import exceptions.PlayerNotFoundException;
import model.Match;
import model.Player;
import repository.PlayerRepositoryImp;

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

    private Integer getMatchesWon(Integer id) throws IncompleteMatchException {
        List<Match> matchesByPlayer = tournamentService.getTournamentMatchService().getMatchesByPlayer(id);
        Integer matchesWon = 0;

        for (Match m : matchesByPlayer) {
            int idWinner = tournamentService.getTournamentMatchService().getWinner(m).getIdPlayer();

            if (idWinner == id) {
                matchesWon++;
            }
        }
        return matchesWon;
    }

    public String showStatsByPlayer(Integer id) throws IncompleteMatchException, PlayerNotFoundException {
        Player player = findPlayerById(id);

        int matchesPlayed = tournamentService.getTournamentMatchService().getMatchesByPlayer(id).size();
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
            | Won/Lost Percentage : %.2f%%   |
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
