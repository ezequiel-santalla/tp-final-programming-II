package model;

import exceptions.InvalidResultException;

public class Result {
    private Integer id;
    private Integer setsWonPlayerOne;
    private Integer setsWonPlayerTwo;

    public Result() {
        this.setsWonPlayerOne = 0;
        this.setsWonPlayerTwo = 0;
    }

    public Result(Integer setsWonPlayerOne, Integer setsWonPlayerTwo) {
        if (validateResult(setsWonPlayerOne, setsWonPlayerTwo)) {
            this.setsWonPlayerOne = setsWonPlayerOne;
            this.setsWonPlayerTwo = setsWonPlayerTwo;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSetsWonPlayerOne() {
        return setsWonPlayerOne;
    }

    public void setSetsWonPlayerOne(Integer setsWonPlayerOne) {
        if (validateResult(setsWonPlayerOne, this.setsWonPlayerTwo)) {
            this.setsWonPlayerOne = setsWonPlayerOne;
        }
    }

    public Integer getSetsWonPlayerTwo() {
        return setsWonPlayerTwo;
    }

    public void setSetsWonPlayerTwo(Integer setsWonPlayerTwo) {
        if (validateResult(this.setsWonPlayerOne, setsWonPlayerTwo)) {
            this.setsWonPlayerTwo = setsWonPlayerTwo;
        }
    }

    private Boolean validateResult(Integer setsWonPlayerOne, Integer setsWonPlayerTwo) {
        // Verifica que el total de sets ganados no supere 3
        boolean totalSetsValid = this.setsWonPlayerTwo + setsWonPlayerOne <= 3;

        // Verifica condiciones específicas para resultados válidos
        boolean specialCaseOne = (setsWonPlayerOne == 0 && this.setsWonPlayerTwo == 2);
        boolean specialCaseTwo = (setsWonPlayerOne == 2 && this.setsWonPlayerTwo == 0);

        if (totalSetsValid || specialCaseOne || specialCaseTwo) {
            return true;
        } else {
            throw new InvalidResultException("Se intentó registrar un resultado NO válido");
        }
    }
}
