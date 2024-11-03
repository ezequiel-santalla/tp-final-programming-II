package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Round {
    private Integer id;
    protected List<Match> matches;
    private Integer givenPoints;

    protected Round() {
        matches = new ArrayList<>();
    }

    protected Round(Integer givenPoints) {
        this.givenPoints = givenPoints;
        matches = new ArrayList<>();
    }

    protected Round(Integer id, List<Match> matches, Integer givenPoints) {
        this.id = id;
        this.matches = matches;
        this.givenPoints = givenPoints;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Integer getGivenPoints() {
        return givenPoints;
    }

    public void setGivenPoints(Integer givenPoints) {
        this.givenPoints = givenPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Round round)) return false;
        return Objects.equals(id, round.id) && Objects.equals(matches, round.matches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matches);
    }


    @Override
    public String toString() {
        return "------------------------------------\n" +
                "|        Detalles del Torneo        |\n" +
                "------------------------------------\n" +
                "| ID                    : " + id + "\n" +
                "| Partidos              : " + matches + "\n" +
                "| Puntaje               : " + givenPoints + "\n";
    }

    public abstract List<Match> generateMatches(List<Player> players);

}
