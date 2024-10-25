import model.Match;
import model.Player;
import repository.MatchRepositoryImp;
import service.MatchService;
import service.PersistenceFile;

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
        matchService.addMatch(match);
    }
}