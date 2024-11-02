import enums.ESurface;
import exceptions.IncompleteMatchException;
import exceptions.InvalidResultException;
import model.Match;
import model.Player;
import model.Result;
import model.Tournament;
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
        MatchRepositoryImp matchRepo = new MatchRepositoryImp(persistence, "data/match.json");
        MatchService matchService = new MatchService(matchRepo);
        Player playerOne = new Player(1, "42044093", "Marcos", "Moreno", "Argentino",  LocalDate.of(1990, Month.APRIL,22), 200);
        Player playerTwo = new Player(2, "38011234", "Lucía", "Fernández", "Argentina", LocalDate.of(1992, Month.MARCH, 15), 180);
        Player playerThree = new Player(3, "41023567", "Carlos", "Gómez", "Uruguayo", LocalDate.of(1991, Month.JANUARY, 10), 220);
        Player playerFour = new Player(4, "45078945", "Ana", "Pérez", "Argentina", LocalDate.of(1989, Month.JULY, 28), 210);
        Player playerFive = new Player(5, "39045678", "Miguel", "López", "Chileno", LocalDate.of(1993, Month.SEPTEMBER, 5), 190);
        Player playerSix = new Player(6, "40012345", "Sofía", "Martínez", "Argentina", LocalDate.of(1995, Month.JUNE, 21), 175);
        Player playerSeven = new Player(7, "42087654", "Joaquín", "Ruiz", "Paraguayo", LocalDate.of(1987, Month.MAY, 8), 205);
        Player playerEight = new Player(8, "43098765", "Valentina", "Ríos", "Argentina", LocalDate.of(1994, Month.AUGUST, 13), 185);
        Player playerNine = new Player(9, "46054321", "Martín", "Sosa", "Uruguayo", LocalDate.of(1996, Month.OCTOBER, 2), 170);
        Player playerTen = new Player(10, "47067890", "Carolina", "Nuñez", "Argentina", LocalDate.of(1991, Month.DECEMBER, 30), 195);
        Player playerEleven = new Player(11, "44034567", "Tomás", "Duarte", "Chileno", LocalDate.of(1990, Month.FEBRUARY, 18), 215);
        Player playerTwelve = new Player(12, "48012345", "Paula", "Álvarez", "Argentina", LocalDate.of(1997, Month.NOVEMBER, 9), 165);
        Player playerThirteen = new Player(13, "49054321", "Agustín", "Vega", "Argentino", LocalDate.of(1994, Month.MARCH, 23), 200);
        Player playerFourteen = new Player(14, "50067890", "Camila", "Castro", "Uruguaya", LocalDate.of(1988, Month.SEPTEMBER, 19), 190);
        Player playerFifteen = new Player(15, "51023456", "Francisco", "Silva", "Paraguayo", LocalDate.of(1986, Month.APRIL, 4), 225);

        Match match = new Match(playerOne,playerTwo);
        matchService.addMatch(match);

        PersistenceFile persistencePlayer = new PersistenceFile();
        PlayerRepositoryImp playerRepositoryImp = new PlayerRepositoryImp(persistencePlayer, "data/player.json");
        PlayerService playerService = new PlayerService(playerRepositoryImp, matchService);
        //System.out.println(playerService.showPlayerRankings());
        //System.out.println(playerService.showStatsByPlayer(1));

        /*playerService.addPlayer(playerOne);
        playerService.addPlayer(playerTwo);
        playerService.addPlayer(playerThree);
        playerService.addPlayer(playerFour);
        playerService.addPlayer(playerFive);
        playerService.addPlayer(playerSix);
        playerService.addPlayer(playerSeven);
        playerService.addPlayer(playerEight);
        playerService.addPlayer(playerNine);
        playerService.addPlayer(playerTen);
        playerService.addPlayer(playerEleven);
        playerService.addPlayer(playerTwelve);
        playerService.addPlayer(playerThirteen);
        playerService.addPlayer(playerFourteen);
        playerService.addPlayer(playerFifteen);*/
        /*
        try {
            match.setResult(new Result(null, 0));
        } catch (InvalidResultException e){

        }catch (IncompleteMatchException e){
            System.out.println("result"+ match.getResult());

        }

        System.out.println("result"+ match.getResult());
        System.out.println(matchService.getWinner(match));

         */
    }
}