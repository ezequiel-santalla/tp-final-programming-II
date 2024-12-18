package model.rounds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "roundType")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = FirstRound.class, name = "firstRound"),
            @JsonSubTypes.Type(value = QuarterFinal.class, name = "quarterFinal"),
            @JsonSubTypes.Type(value = Semifinal.class, name = "semifinal"),
            @JsonSubTypes.Type(value = Final.class, name = "final")
    })
    public abstract class Round {

    @JsonProperty("matches")
    protected List<Match> matches;
    @JsonProperty("givenPoints")
    private Integer givenPoints;

    protected Round() {
        matches = new ArrayList<>();
    }

    protected Round(Integer givenPoints) {
        this.givenPoints = givenPoints;
        matches = new ArrayList<>();
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
        return Objects.equals(matches, round.matches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matches);
    }


    @Override
    public String toString() {
        return "------------------------------------\n" +
                "|        Detalles del Torneo        |\n" +
                "------------------------------------\n" +
                "| Partidos              : " + matches + "\n" +
                "| Puntaje               : " + givenPoints + "\n";
    }

    public abstract void generateMatches(List<Player> players);

}
