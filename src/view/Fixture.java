package view;

import model.Match;
import model.Player;
import model.Tournament;

import java.util.List;

public class Fixture {

    private Tournament tournament;

    public Fixture(Tournament tournament) {


    }


    public static void displayTournament(Tournament tournament) {

        List<Match> matches = tournament.getRounds().getLast().getMatches();

        int totalRounds = (int) (Math.log(matches.size()) / Math.log(2)) + 1;


        // Calcular el ancho de cada cuadro

        int boxWidth = 15; // Ancho de cada cuadro

        int spacing = 5;   // Espacio entre cuadros


        // Mostrar la primera ronda

        for (Match match : matches) {

            System.out.printf("%" + (boxWidth / 2 + spacing) + "s", createBox(match.getPlayerOne()));

            System.out.printf("%" + (boxWidth / 2 + spacing) + "s", createBox(match.getPlayerTwo()));

            System.out.println();

        }


        // Mostrar las líneas de conexión y los ganadores

        for (int i = 0; i < matches.size(); i += 2) {

            if (i + 1 < matches.size()) {

                System.out.printf("%" + (boxWidth / 2 + spacing) + "s", "   |   ");

                System.out.printf("%" + (boxWidth / 2 + spacing) + "s", "   |   ");

                System.out.println();

                System.out.printf("%" + (boxWidth / 2 + spacing) + "s", "   |   ");

                System.out.printf("%" + (boxWidth / 2 + spacing) + "s", "   |   ");

                System.out.println();

                System.out.printf("%" + (boxWidth / 2 + spacing) + "s", "Winner");

                System.out.printf("%" + (boxWidth / 2 + spacing) + "s", "Winner");

                System.out.println();

            }

        }


        // Aquí puedes agregar lógica para las siguientes rondas

    }


    private static String createBox(Player player) {

        String box = "+--------------------------------------------------+\n";

        box += "| " + player.getName() + " "+player.getLastName()+" |\n";

        box += "+--------------------------------------------------+";

        return box;

    }

}
