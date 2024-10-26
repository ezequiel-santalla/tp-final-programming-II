package model;

import exceptions.InvalidResultException;

import java.util.Objects;

public class Result {
    private Integer id;
    private Integer setsWonPlayerOne;
    private Integer setsWonPlayerTwo;

    public Result() {
        this.setsWonPlayerOne = 0;
        this.setsWonPlayerTwo = 0;
    }

    //este contructot necesita una verificacion de valores null
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
// falta verificar cuando un valor es null
    private Boolean validateResult(Integer setsWonPlayerOne, Integer setsWonPlayerTwo) {
       //Se reemplazan los valores null por cero para evitar errores
        if(setsWonPlayerOne == null){
            setsWonPlayerOne=0;
            this.setsWonPlayerOne = 0;
        }
        if(setsWonPlayerTwo == null){
            setsWonPlayerTwo=0;
            this.setsWonPlayerTwo = 0;
        }
        // Verifica que el total de sets ganados no supere 3
        boolean totalSetsValid = setsWonPlayerTwo + setsWonPlayerOne == 3;

        // Verifica condiciones específicas para resultados válidos
        boolean specialCaseOne = (setsWonPlayerOne == 0 && setsWonPlayerTwo == 2);
        boolean specialCaseTwo = (setsWonPlayerOne == 2 && setsWonPlayerTwo == 0);

        if (totalSetsValid || specialCaseOne || specialCaseTwo) {
            return true;
        } else {
            //se registra un resulatado de "0-0"
            this.setsWonPlayerOne=0;
            this.setsWonPlayerTwo=0;
            throw new InvalidResultException("Se intentó registrar un resultado NO válido");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result result)) return false;
        return Objects.equals(id, result.id) && Objects.equals(setsWonPlayerOne, result.setsWonPlayerOne) && Objects.equals(setsWonPlayerTwo, result.setsWonPlayerTwo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, setsWonPlayerOne, setsWonPlayerTwo);
    }

    @Override
    public String toString() {
        return "Result: "+setsWonPlayerOne+" - "+setsWonPlayerTwo;
    }
}
