package repository.model;

public class Result {
    private Integer id;
    private Integer setsWonPlayerOne;
    private Integer setsWonPlayerTwo;

    public Result() {
        this.setsWonPlayerOne = 0;
        this.setsWonPlayerTwo = 0;
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
        this.setsWonPlayerOne = setsWonPlayerOne;
    }

    public Integer getSetsWonPlayerTwo() {
        return setsWonPlayerTwo;
    }

    public void setSetsWonPlayerTwo(Integer setsWonPlayerTwo) {
        this.setsWonPlayerTwo = setsWonPlayerTwo;
    }
}
