package model;

import enums.ESurface;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Tournament {
    private Integer id;
    private String name;
    private String location;
    private ESurface surface;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private Double prizeMoney;
    private Set<Player> players;
    private List<Round> rounds;

    public Tournament(Integer id, String name, String location, ESurface surface, LocalDate startingDate, LocalDate endingDate, Double prizeMoney, Set<Player> players, List<Round> rounds) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.surface = surface;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.prizeMoney = prizeMoney;
        this.players = players;
        this.rounds = rounds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(Double prizeMoney) {
        this.prizeMoney = prizeMoney;
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
        return "---------------------------------\n" +
                "|        Tournament Details      |\n" +
                "---------------------------------\n" +
                "| ID           : " + id + "\n" +
                "| Name         : '" + name + "'\n" +
                "| Location     : '" + location + "'\n" +
                "| Surface      : " + surface + "\n" +
                "| Starting Date: " + startingDate + "\n" +
                "| Ending Date  : " + endingDate + "\n" +
                "| Prize Money  : $" + prizeMoney + "\n" +
                "---------------------------------\n" +
                "| Players      : " + players + "\n" +
                "| Rounds       : " + rounds + "\n" +
                "---------------------------------";
    }
}