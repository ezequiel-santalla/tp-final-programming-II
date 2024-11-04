package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import exceptions.InvalidResultException;

import java.util.Objects;

public class Result {
    @JsonProperty("setsWonPlayerOne")
    private Integer setsWonPlayerOne;

    @JsonProperty("setsWonPlayerTwo")
    private Integer setsWonPlayerTwo;

    public Result() {
    }

    public Result(Integer setsWonPlayerOne, Integer setsWonPlayerTwo) throws InvalidResultException {
        validateResult(setsWonPlayerOne, setsWonPlayerTwo);
        this.setsWonPlayerOne = setsWonPlayerOne;
        this.setsWonPlayerTwo = setsWonPlayerTwo;
    }

    public Integer getSetsWonPlayerOne() {
        return setsWonPlayerOne;
    }

    public void setSetsWonPlayerOne(Integer setsWonPlayerOne) throws InvalidResultException {
        validateResult(setsWonPlayerOne, this.setsWonPlayerTwo);
        this.setsWonPlayerOne = setsWonPlayerOne;
    }

    public Integer getSetsWonPlayerTwo() {
        return setsWonPlayerTwo;
    }

    public void setSetsWonPlayerTwo(Integer setsWonPlayerTwo) throws InvalidResultException {
        validateResult(this.setsWonPlayerOne, setsWonPlayerTwo);
        this.setsWonPlayerTwo = setsWonPlayerTwo;
    }

    private void validateResult(Integer setsWonPlayerOne, Integer setsWonPlayerTwo) throws InvalidResultException {
        // Verifica que los valores no sean nulos y reemplaza por cero
        if (setsWonPlayerOne == null || setsWonPlayerTwo == null) {
            throw new InvalidResultException("Los valores no pueden ser nulos.");
        }

        // Verifica que los valores sean no negativos
        if (setsWonPlayerOne < 0 || setsWonPlayerTwo < 0) {
            throw new InvalidResultException("Los valores no pueden ser negativos.");
        }

        // Verifica que el total de sets ganados no supere 3
        boolean totalSetsValid = (setsWonPlayerOne + setsWonPlayerTwo) == 3;

        // Verifica condiciones específicas para resultados válidos
        boolean specialCaseOne = (setsWonPlayerOne == 0 && setsWonPlayerTwo == 2);
        boolean specialCaseTwo = (setsWonPlayerOne == 2 && setsWonPlayerTwo == 0);

        if (!totalSetsValid && !specialCaseOne && !specialCaseTwo) {
            throw new InvalidResultException("Se intentó registrar un resultado NO válido: " +
                    "setsWonPlayerOne: " + setsWonPlayerOne + " - setsWonPlayerTwo: " + setsWonPlayerTwo);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result result)) return false;
        return Objects.equals(setsWonPlayerOne, result.setsWonPlayerOne) &&
                Objects.equals(setsWonPlayerTwo, result.setsWonPlayerTwo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(setsWonPlayerOne, setsWonPlayerTwo);
    }

    @Override
    public String toString() {
        return "Result: " + setsWonPlayerOne + " - " + setsWonPlayerTwo;
    }
}