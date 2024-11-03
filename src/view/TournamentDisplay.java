package view;

import model.Match;
import model.Player;
import model.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TournamentDisplay {

    public void showHorizontalFixture(List<Match> matches) {
        // Ordenamos los partidos por su id
        Collections.sort(matches);

        // Calculamos el total de rondas
        int totalRounds = (int) (Math.log(matches.size()) / Math.log(2)) + 1;

        // Crear una lista de listas para almacenar los partidos de cada ronda
        List<List<Match>> rounds = new ArrayList<>();

        // Dividimos los partidos en rondas
        int matchIndex = 0;
        for (int i = 0; i < totalRounds; i++) {
            int numMatches = (int) Math.pow(2, totalRounds - i - 1);
            List<Match> round = new ArrayList<>();
            for (int j = 0; j < numMatches && matchIndex < matches.size(); j++) {
                round.add(matches.get(matchIndex++));
            }
            rounds.add(round);
        }

        // Llamada a función recursiva para mostrar el fixture en formato árbol
        displayBracket(rounds, 0, 0, (int) Math.pow(2, totalRounds) - 1);
    }

    private void displayBracket(List<List<Match>> rounds, int roundIndex, int start, int end) {
        if (roundIndex >= rounds.size()) return;

        int middle = (start + end) / 2;
        int space = (int) Math.pow(2, rounds.size() - roundIndex - 1) - 1;

        // Mostrar partidos en la ronda actual
        int matchIndex = 0;
        for (int i = start; i <= end; i += (space + 1) * 2) {
            if (matchIndex < rounds.get(roundIndex).size()) {
                System.out.printf("%" + (space * 2) + "s", "");
                System.out.print(formatMatch(rounds.get(roundIndex).get(matchIndex++)));
                System.out.printf("%" + (space * 2) + "s", "");
            }
            System.out.print("\t\t");
        }

        System.out.println("\n");

        // Llamada recursiva para mostrar la siguiente ronda
        displayBracket(rounds, roundIndex + 1, start + space + 1, end - space - 1);
    }

    private String formatMatch(Match match) {
        String playerOneName = match.getPlayerOne().getName();
        String playerTwoName = match.getPlayerTwo().getName();
        String result = (match.getResult() != null) ? match.getResult().toString() : "Sin resultado";

        return "[" + playerOneName + " vs " + playerTwoName + " | " + result + "]";
    }
}
