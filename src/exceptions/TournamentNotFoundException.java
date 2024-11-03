package exceptions;

public class TournamentNotFoundException extends EntityNotFoundException {

    public TournamentNotFoundException(String message) {
        super(message);
    }
}