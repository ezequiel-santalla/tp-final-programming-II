import test.TestTournament;
import view.Menu;

public class Main {
    public static void main(String[] args) {

        //TestTournament testTournament = new TestTournament();


            // Get a random player who is not registered for the tournament and try to register him
        //testTournament.registerPlayerInTournament(testTournament.getRandomUnregisteredPlayer());


            // Try to register a player who already exists in the tournament
        // testTournament.registerDuplicatedlayer();


            // Register the players needed to complete the tournament
        //testTournament.fillTournamentWithPlayers();

            // Remove a player from tournament by ID
       // testTournament.unsubscribePlayerFromTournament(10);

            // Try to generate the next round in the tournament
        //
        // testTournament.nextRound();

       // testTournament.modifyResultToMatch(15, testTournament.getDataInitializer().getRandomResult());

            // Assign results to all matches in the current round
        //testTournament.finalizeAllMatchOfCurrentRound();

        new Menu().runMenu();
    }
}