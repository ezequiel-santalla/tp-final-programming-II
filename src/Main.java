import model.Match;
import model.Player;
import model.Result;
import repository.MatchRepositoryImp;
import repository.PlayerRepositoryImp;
import service.MatchService;
import service.PersistenceFile;
import service.PlayerService;

import java.time.LocalDate;
import java.time.Month;
public class Main {
    public static void main(String[] args) {
        PersistenceFile persistence = new PersistenceFile();
        MatchRepositoryImp matchRepo = new MatchRepositoryImp(persistence, "match.json");
        MatchService matchService = new MatchService(matchRepo);
        Player playerOne = new Player(1, "Marcos", "Moreno", "Argentino",  LocalDate.of(1990, Month.APRIL,22), 200);
        Player playerTwo = new Player(2, "Diego", "Farfan", "Argentino",  LocalDate.of(1997, Month.DECEMBER,1), 350);
       Match match = new Match(playerOne,playerTwo);
        //matchService.addMatch(match);

        PersistenceFile persistencePlayer = new PersistenceFile();
        PlayerRepositoryImp playerRepositoryImp = new PlayerRepositoryImp(persistencePlayer, "player.json");
        PlayerService playerService = new PlayerService(playerRepositoryImp, matchService);
        //playerService.addPlayer(playerOne);
        //playerService.addPlayer(playerTwo);
        //System.out.println(playerService.showPlayerRankings());
        //System.out.println(playerService.showStatsByPlayer(1));
        match.setResult(new Result(2,2));
        System.out.println(matchService.getWinner(match));
    }
}