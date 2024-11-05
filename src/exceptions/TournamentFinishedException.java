package exceptions;

public class TournamentFinishedException extends Exception {

    public TournamentFinishedException(String message) {
        super(message);
    }
    public TournamentFinishedException() {
        super("El torneo ha finalizado");
    }

}