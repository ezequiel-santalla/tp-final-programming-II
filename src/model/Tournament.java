package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.ESurface;

import java.time.LocalDate;
import java.util.*;

public class Tournament implements Comparable<Tournament> {
    @JsonProperty("idTournament")
    private Integer idTournament;
    @JsonProperty("name")
    private String name;
    @JsonProperty("location")
    private String location;
    @JsonProperty("surface")
    private ESurface surface;
    @JsonProperty("startingDate")
    private LocalDate startingDate;
    @JsonProperty("endingDate")
    private LocalDate endingDate;
    @JsonProperty("players")
    private Set<Player> players;
    @JsonProperty("rounds")
    private List<Round> rounds;

    public Tournament() {
        this.players = new TreeSet<>();
        this.rounds = new ArrayList<>();
    }

    public Tournament(String name, String location, ESurface surface, LocalDate startingDate, LocalDate endingDate) {
        this.name = name;
        this.location = location;
        this.surface = surface;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.players = new TreeSet<>();
        this.rounds = new ArrayList<>();
    }

    public Tournament(Integer id, String name, String location, ESurface surface, LocalDate startingDate, LocalDate endingDate) {
        this.idTournament = id;
        this.name = name;
        this.location = location;
        this.surface = surface;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.players = new TreeSet<>();
        this.rounds = new ArrayList<>();
    }

    public Integer getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(Integer idTournament) {
        this.idTournament = idTournament;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ESurface getSurface() {
        return surface;
    }

    public void setSurface(ESurface surface) {
        this.surface = surface;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    @Override
    public String toString() {
        return "------------------------------------\n" +
                "|        Detalles del Torneo        |\n" +
                "------------------------------------\n" +
                "| ID                    : " + idTournament + "\n" +
                "| Nombre                : " + name + "\n" +
                "| Lugar                 : " + location + "\n" +
                "| Superficie            : " + surface + "\n" +
                "| Fecha de Inicio       : " + startingDate + "\n" +
                "| Fecha de Finalización : " + endingDate + "\n" +
                "------------------------------------\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tournament that)) return false;
        return Objects.equals(idTournament, that.idTournament) && Objects.equals(name, that.name) && Objects.equals(location, that.location) && surface == that.surface && Objects.equals(startingDate, that.startingDate) && Objects.equals(endingDate, that.endingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTournament, name, location, surface, startingDate, endingDate);
    }

    @Override
    public int compareTo(Tournament o) {
        return this.idTournament.compareTo(o.getIdTournament());
    }
}