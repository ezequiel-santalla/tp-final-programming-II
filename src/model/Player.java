package model;

import java.time.LocalDate;
import java.util.Objects;

public class Player implements Comparable<Player> {
    private Integer idPlayer;
    private String name;
    private String lastName;
    private String nationality;
    private LocalDate dateOfBirth;
    private Integer points;

    public Player() {
    }

    public Player(String name, String lastName, String nationality, LocalDate dateOfBirth) {
        this.name = name;
        this.lastName = lastName;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
    }

    public Player(Integer idPlayer, String name, String lastName, String nationality, LocalDate dateOfBirth, Integer points) {
        this.idPlayer = idPlayer;
        this.name = name;
        this.lastName = lastName;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.points = points;
    }

    public Integer getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return Objects.equals(idPlayer, player.idPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPlayer);
    }

    @Override
    public String toString() {
        return "Player{" +
                "idPlayer=" + idPlayer +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", points=" + points +
                '}';
    }

    @Override
    public int compareTo(Player o) {
        return this.idPlayer.compareTo(o.idPlayer);
    }
}
