import test.TestTournament;

public class Main {
    public static void main(String[] args) {

        TestTournament testTournament = new TestTournament();

        //testTournament.registerPlayerInTournament(testTournament.getRandomUnregisteredPlayer());
        // testTournament.registerDuplicatedlayer();
         //testTournament.fillTournamentWithPlayers();


        testTournament.nextRound();
        testTournament.finalizeAllMatchOfCurrentRound();

    }
}