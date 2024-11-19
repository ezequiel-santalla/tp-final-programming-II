package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.ESurface;
import enums.ETournamentStatus;
import model.rounds.Round;
import utils.Utils;

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
    @JsonProperty("status")
    private ETournamentStatus status;
    
    public Tournament() {
        this.players = new TreeSet<>();
        this.rounds = new ArrayList<>();
        this.status = ETournamentStatus.NOT_STARTED;
    }
    
    public Tournament(String name, String location, ESurface surface, LocalDate startingDate, LocalDate endingDate) {
        this();
        this.name = name;
        this.location = location;
        this.surface = surface;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
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

    public ETournamentStatus getStatus() {
        return status;
    }

    public void setStatus(ETournamentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
                "| %-5d | %-20s | %-15s | %-12s | %-12s | %-12s | %-12s |",
                idTournament, name, location, surface.getDisplayName(), Utils.formatLocalDate(startingDate), Utils.formatLocalDate(endingDate), status.getDisplayName()
        );
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