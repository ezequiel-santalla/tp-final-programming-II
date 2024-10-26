package model;


import java.util.Objects;

public class Match implements Comparable<Match>{
    private Integer id;
    private Player playerOne;
    private Player playerTwo;
    private Result result;

    public Match() {
    }

    public Match(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public Match(Integer id, Player playerOne, Player playerTwo, Result result) {
        this.id = id;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match match)) return false;
        return Objects.equals(id, match.id) && Objects.equals(playerOne, match.playerOne) && Objects.equals(playerTwo, match.playerTwo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerOne, playerTwo);
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", playerOne=" + playerOne +
                ", playerTwo=" + playerTwo +
                ", result=" + result +
                '}';
    }

    @Override
    public int compareTo(Match o) {
        return this.id.compareTo(o.id);
    }
}