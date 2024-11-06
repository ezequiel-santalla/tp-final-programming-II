package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.InvalidResultException;

import java.util.Objects;

public class SetScore {

    @JsonProperty("playerOneScore")
    private Integer playerOneScore;

    @JsonProperty("playerTwoScore")
    private Integer playerTwoScore;

    public SetScore() {
    }

    public SetScore(Integer playerOneScore, Integer playerTwoScore) throws InvalidResultException {
        if (validateFullScore(playerOneScore, playerTwoScore)) {
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
            if (validateFullScore(playerOneScore, this.playerTwoScore)) {
                this.playerOneScore = playerOneScore;
            } else {
                throw new InvalidResultException("Scores do not represent a valid result.");
            }

            // If the other player's score is not available, evaluate the parcial score
        } else if (validateParcialScore(playerOneScore)) {
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
            if (validateFullScore(this.playerOneScore, playerTwoScore)) {
                this.playerTwoScore = playerTwoScore;
            } else {
                throw new InvalidResultException("Scores do not represent a valid result.");
            }

            // If the other player's score is not available, evaluate the parcial score
        } else if (validateParcialScore(playerTwoScore)) {
            this.playerTwoScore = playerTwoScore;
        } else {
            throw new InvalidResultException("Invalid score for player one.");
        }
    }

    private boolean validateFullScore(Integer playerOneScore, Integer playerTwoScore) throws InvalidResultException {

        // Validate than scores are not negative
        if (playerOneScore < 0 || playerTwoScore < 0) {
            throw new InvalidResultException("Score cannot be negative");
        }

        // Conditions to validate score
        boolean playerOneWinsWithSix = playerOneScore == 6 && playerTwoScore <= 4;
        boolean playerTwoWinsWithSix = playerTwoScore == 6 && playerOneScore <= 4;

        boolean playerOneWinsWithSeven = playerOneScore == 7 && (playerTwoScore == 5 || playerTwoScore == 6);
        boolean playerTwoWinsWithSeven = playerTwoScore == 7 && (playerOneScore == 5 || playerOneScore == 6);

        // Returns true if any of the win conditions are valid
        return playerOneWinsWithSix || playerTwoWinsWithSix || playerOneWinsWithSeven || playerTwoWinsWithSeven;
    }

    private boolean validateParcialScore(Integer score) {
        return score > 0 && score <= 7;
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
