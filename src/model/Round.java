package model;


import service.TournamentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Round {
    private Integer id;
    private List<Match> matches;
    private Integer givenPoints;

    public Round(){
    }

    public Round(Integer id, List<Match> matches, Integer givenPoints, Double givenMoney) {
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
        return String.format(
                "+-----------------------------+\n" +
                        "|          Round             |\n" +
                        "+-----------------------------+\n" +
                        "| id           | %-12d |\n" +
                        "| Matches      | %-12s |\n" +
                        "| Given Points | %-12d |\n" +
                        "+-----------------------------+",
                id, matches, givenPoints
        );
    }

    public abstract List<Match> generateMatches(List<Player> players);

    public abstract Integer pointsEarned();

}
