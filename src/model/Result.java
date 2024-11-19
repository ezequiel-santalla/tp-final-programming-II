package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.InvalidResultException;

import java.util.ArrayList;
import java.util.List;


public class Result {

    @JsonProperty("setsScore")
    private List<SetScore> setsScore;

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

    public boolean thereIsNoWinner() {
        System.out.println("set P1:"+getSetsWonPlayerOne()+" set P2:"+getSetsWonPlayerTwo());
        return getSetsWonPlayerOne() != 2 && getSetsWonPlayerTwo() != 2;
    }

    @JsonIgnore
    public Integer getSetsWonPlayerOne() {

        Integer setsWonPlayerOne = 0;

        for (SetScore score : setsScore) {
            if (score.getPlayerOneScore() > score.getPlayerTwoScore()) {
                setsWonPlayerOne++;
            }
        }
        return setsWonPlayerOne;

    }

    @JsonIgnore
    public Integer getSetsWonPlayerTwo() {

        Integer setsWonPlayerTwo = 0;

        for (SetScore score : setsScore) {
            if (score.getPlayerTwoScore() > score.getPlayerOneScore()) {
                setsWonPlayerTwo++;
            }
        }
        return setsWonPlayerTwo;
    }

    public void setSetsScore(List<SetScore> setsScore) throws InvalidResultException {
        validateResult(setsScore);
        this.setsScore = setsScore;
    }

    @Override
    public String toString() {
        return setsScore.toString();
    }
}