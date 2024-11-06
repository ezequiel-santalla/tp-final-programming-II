import enums.ESurface;
import exceptions.*;
import model.*;
import repository.PlayerRepositoryImp;
import repository.TournamentRepositoryImp;
import service.TournamentService;
import service.PlayerService;
import utilities.Utilities;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        TournamentRepositoryImp tournamentRepositoryImp = new TournamentRepositoryImp();
        Tournament tournament = new Tournament("torneo", "MDP", ESurface.CARPET, LocalDate.of(2024, 2, 2), LocalDate.of(2024, 2, 6));
        TournamentService tournamentService = null;
        try {
            tournamentService = new TournamentService(tournamentRepositoryImp, 1);
        } catch (TournamentNotFoundException e) {
            tournamentService = new TournamentService(tournamentRepositoryImp, tournament);
            System.out.println("No se encontro un torneo con ese id" + e.getMessage());
        }
        PlayerRepositoryImp playerRepositoryImp = new PlayerRepositoryImp();
        PlayerService playerService = new PlayerService(playerRepositoryImp, tournamentService);



        Player playerOne = new Player(1, "42044093", "Marcos", "Moreno", "Argentino", LocalDate.of(1990, Month.APRIL, 22), 200);
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
        Player playerSixteen = new Player(16, "23444565", "Fernando", "Silva", "Paraguayo", LocalDate.of(1986, Month.APRIL, 4), 225);

        //Match match = new Match(playerOne, playerTwo);
        //matchService.addMatch(match);

        //System.out.println(playerService.showPlayerRankings());
        //System.out.println(playerService.showStatsByPlayer(1));
/*
        playerService.addPlayer(playerOne);
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
        //Menu menu = new Menu(tournamentService, playerService);
        // menu.runMenu();

/*
        try {
            tournamentService.registerPlayer(playerOne);
            tournamentService.registerPlayer(playerTwo);
            tournamentService.registerPlayer(playerThree);
            tournamentService.registerPlayer(playerFour);
            tournamentService.registerPlayer(playerFive);
            tournamentService.registerPlayer(playerSix);
            tournamentService.registerPlayer(playerSeven);
            tournamentService.registerPlayer(playerEight);
            tournamentService.registerPlayer(playerNine);
            tournamentService.registerPlayer(playerTen);
            tournamentService.registerPlayer(playerEleven);
            tournamentService.registerPlayer(playerTwelve);
            tournamentService.registerPlayer(playerThirteen);
            tournamentService.registerPlayer(playerFourteen);
            tournamentService.registerPlayer(playerFifteen);
            tournamentService.registerPlayer(playerSixteen);
        } catch (TournamentFullException e) {
            //System.out.println("Ya no se pueden registrar mas jugadores");
        }*/

        List<SetScore> possibleScores = new ArrayList<>();
        Result result = new Result();

        try {
            possibleScores.add(new SetScore(6,4));
            possibleScores.add(new SetScore(6,3));
            possibleScores.add(new SetScore(6,2));
            possibleScores.add(new SetScore(6,1));
            possibleScores.add(new SetScore(6,0));
            possibleScores.add(new SetScore(7,6));
            possibleScores.add(new SetScore(7,5));
            possibleScores.add(new SetScore(4,6));
            possibleScores.add(new SetScore(3,6));
            possibleScores.add(new SetScore(2,6));
            possibleScores.add(new SetScore(1,6));
            possibleScores.add(new SetScore(0,6));
            possibleScores.add(new SetScore(5,7));
            possibleScores.add(new SetScore(6,7));

            while(!result.thereIsAWinner()) {
                result.addSetScore(possibleScores.get(Utilities.random(0, possibleScores.size())));
            }
        } catch (InvalidResultException e) {
            System.out.println("Resultado invalido");        }


  /*     for (Match match : tournamentService.getTournament().getRounds().getLast().getMatches()) {
            match.setResult(randomResults.get(Utilities.random(0,randomResults.size())));
        }


*/


        try {
            tournamentService.assignResultToMatch(15, result);
        } catch (MatchNotFoundException | IncompleteMatchException | InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
            }

        try {
            System.out.println("Ganador del torneo: \n"+tournamentService.getTournamentWinner());
        } catch (IncompleteMatchException e) {
            System.out.println("Los partidos no han finalizado");
        } catch (InvalidTournamentStatusException e) {
            System.out.println("EL torneo no ha finalizado"+e.getMessage());
        }




        try {
            tournamentService.advanceTournament();
        } catch (IncompleteMatchException e) {
            System.out.println("Los partidos no han finalizado");
        } catch (InvalidTournamentStatusException e) {
            System.out.println(e.getMessage());
        } catch (TournamentFullException e) {
            System.out.println("La cantidad de jugadores esta completa");
        }




        try {
            tournamentService.updateTournament(tournamentService.getTournament());
        } catch (TournamentNotFoundException e) {
            System.out.println("No se pudo actualizar el torneo");
        }





    }
}