package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public class Player implements Comparable<Player> {
    @JsonProperty("idPlayer")
    private Integer idPlayer;
    @JsonProperty("dni")
    private String dni;
    @JsonProperty("name")
    private String name;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;
    @JsonProperty("points")
    private Integer points;

    public Player() {
    }

    public Player(String name, String lastName, String dni, String nationality, LocalDate dateOfBirth) {
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
    }

    public Player(Integer idPlayer, String dni, String name, String lastName, String nationality, LocalDate dateOfBirth, Integer points) {
        this.idPlayer = idPlayer;
        this.dni = dni;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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
        return "------------------------------------\n" +
                "|          Detalles del Jugador          |\n" +
                "------------------------------------\n" +
                "| ID                  : " + idPlayer + "\n" +
                "| Dni                 : " + dni + "\n" +
                "| Nombre              : " + name + "\n" +
                "| Apellido            : " + lastName + "\n" +
                "| Nacionalidad        : " + nationality + "\n" +
                "| Fecha de Nacimiento : " + dateOfBirth + "\n" +
                "| Puntos              : " + points + "\n" +
                "------------------------------------\n";
    }

    @Override
    public int compareTo(Player o) {
        return this.idPlayer.compareTo(o.idPlayer);
    }
}
