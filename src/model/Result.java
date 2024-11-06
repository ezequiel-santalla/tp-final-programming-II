package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.InvalidResultException;

import java.util.ArrayList;
import java.util.List;


public class Result {

    @JsonProperty("setsScore")
    private final List<SetScore> setsScore;

    public Result() {
        this.setsScore = new ArrayList<>();
    }

    public Result(List<SetScore> setsScore) throws InvalidResultException {
        this();
        validateResult(setsScore);
        this.setsScore.addAll(setsScore);
    }

    public void addSetScore(SetScore setScore) throws InvalidResultException {
        if (this.setsScore.size() >= 3) {
            throw new InvalidResultException("The number of sets cannot exceed 3.");
        }
        this.setsScore.add(setScore);
    }

    private void validateResult(List<SetScore> setsScore) throws InvalidResultException {
        if (setsScore.size() > 3) {
            throw new InvalidResultException("The number of sets cannot exceed 3.");
        }
    }

    public List<SetScore> getSetsScore() {
        return setsScore;
    }

    public boolean thereIsAWinner() {

        return getSetsWonPlayerOne() == 2 || getSetsWonPlayerTwo() == 2;
    }

    public Integer getSetsWonPlayerOne() {

        Integer setsWonPlayerOne = 0;

        for (SetScore score : setsScore) {
            if (score.getPlayerOneScore() > score.getPlayerTwoScore()) {
                setsWonPlayerOne++;
            }
        }
        return setsWonPlayerOne;
    }

    public Integer getSetsWonPlayerTwo() {

        Integer setsWonPlayerTwo = 0;

        for (SetScore score : setsScore) {
            if (score.getPlayerOneScore() > score.getPlayerTwoScore()) {
                setsWonPlayerTwo++;
            }
        }
        return setsWonPlayerTwo;
    }

}