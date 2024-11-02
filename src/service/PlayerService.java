package service;

import model.Match;
import model.Player;
import repository.PlayerRepositoryImp;

import java.util.List;

public class PlayerService {
    private final PlayerRepositoryImp playerRepository;
    private final MatchService matchService;

    public PlayerService(PlayerRepositoryImp playerRepository, MatchService matchService) {
        this.playerRepository = playerRepository;
        this.matchService = matchService;
    }

    public Integer addPlayer(Player player) {
        return playerRepository.create(player);
    }

    public Player findPlayerById(Integer id) {
        return playerRepository.find(id);
    }

    public void updatePlayer(Player player) {
        playerRepository.update(player);
    }

    public void deletePlayer(Integer id) {
        playerRepository.delete(id);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.getAll();
    }

    private Integer getMatchesWon(Integer id) {
        List<Match> matchesByPlayer = matchService.getMatchesByPlayer(id);
        Integer matchesWon = 0;

        for (Match m : matchesByPlayer) {
            int idWinner = matchService.getWinner(m).getIdPlayer();

            if (idWinner == id) {
                matchesWon++;
            }
        }
        return matchesWon;
    }

    public String showStatsByPlayer(Integer id) {
        Player player = findPlayerById(id);

        int matchesPlayed = matchService.getMatchesByPlayer(id).size();
        int matchesWon = getMatchesWon(id);
        int matchesLost = matchesPlayed - matchesWon;
        int totalPoints = player.getPoints();

        return String.format(
                """
                =================================
                        Player Statistics
                =================================
                Name: %s %s
                
                Matches Played: %d
                Matches Won:    %d
                Matches Lost:   %d
                Total Points:   %d
                =================================
                """,
                player.getName(), player.getLastName(),
                matchesPlayed, matchesWon, matchesLost, totalPoints
        );
    }

    private List<Player> getPlayerRankings() {
        List<Player> players = getAllPlayers();

        players.sort((p1, p2) -> p2.getPoints().compareTo(p1.getPoints()));

        return players;
    }

    public String showPlayerRankings() {
        List<Player> rankedPlayerList = getPlayerRankings();

        final int NAME_COLUMN_WIDTH = 29; // Width for name
        final int POINTS_COLUMN_WIDTH = 2; // Width for points

        StringBuilder rankingStr = new StringBuilder();

        rankingStr.append(
                """
                ============================================
                                  Rankings
                ============================================
                Pos | Name                          | Points
                --------------------------------------------
                """);

        for (int i = 0; i < rankedPlayerList.size(); i++) {
            Player player = rankedPlayerList.get(i);

            rankingStr.append(String.format("%-4d| %-"+NAME_COLUMN_WIDTH+"s | %"+POINTS_COLUMN_WIDTH+"d\n",
                    (i + 1), player.getName() + " " + player.getLastName(), player.getPoints()));
        }

        rankingStr.append("============================================\n");

        return rankingStr.toString();
    }
}
