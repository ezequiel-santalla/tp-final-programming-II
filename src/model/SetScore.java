package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.InvalidResultException;
import utils.Utils;

import java.util.Objects;

public class SetScore {

    @JsonProperty("playerOneScore")
    private Integer playerOneScore;

    @JsonProperty("playerTwoScore")
    private Integer playerTwoScore;

    public SetScore() {
    }

    public SetScore(Integer playerOneScore, Integer playerTwoScore) throws InvalidResultException {
        if (Utils.validateFullScore(playerOneScore, playerTwoScore)) {
            this.playerOneScore = playerOneScore;
            this.playerTwoScore = playerTwoScore;
        }
    }

    public Integer getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(Integer playerOneScore) throws InvalidResultException {

        // If the other player's score is available, evaluate the full score
        if (this.playerTwoScore != null) {
            if (Utils.validateFullScore(playerOneScore, this.playerTwoScore)) {
                this.playerOneScore = playerOneScore;
            } else {
                throw new InvalidResultException("Scores do not represent a valid result.");
            }

            // If the other player's score is not available, evaluate the parcial score
        } else if (Utils.validatePartialScore(playerOneScore)) {
            this.playerOneScore = playerOneScore;
        } else {
            throw new InvalidResultException("Invalid score for player one.");
        }
    }

    public Integer getPlayerTwoScore() {
        return playerTwoScore;
    }


    public void setPlayerTwoScore(Integer playerTwoScore) throws InvalidResultException {

        // If the other player's score is available, evaluate the full score
        if (this.playerOneScore != null) {
            if (Utils.validateFullScore(this.playerOneScore, playerTwoScore)) {
                this.playerTwoScore = playerTwoScore;
            } else {
                throw new InvalidResultException("Scores do not represent a valid result.");
            }

            // If the other player's score is not available, evaluate the parcial score
        } else if (Utils.validatePartialScore(playerTwoScore)) {
            this.playerTwoScore = playerTwoScore;
        } else {
            throw new InvalidResultException("Invalid score for player one.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SetScore setScore)) return false;
        return Objects.equals(playerOneScore, setScore.playerOneScore) && Objects.equals(playerTwoScore, setScore.playerTwoScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerOneScore, playerTwoScore);
    }

    @Override
    public String toString() {
        return playerOneScore + "-" + playerTwoScore;
    }
}
