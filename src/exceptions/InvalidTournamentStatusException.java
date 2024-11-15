package exceptions;

public class InvalidTournamentStatusException extends Exception {

    public InvalidTournamentStatusException(String message) {
        super(message);
    }
    public InvalidTournamentStatusException() {
        super("El torneo ha finalizado");
    }

}