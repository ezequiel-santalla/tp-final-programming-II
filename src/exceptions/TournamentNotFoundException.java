package exceptions;

public class TournamentNotFoundException extends EntityNotFoundException {

    public TournamentNotFoundException(Integer id) {
        super("No tournament was found with the given ID: " + id);
    }

    public TournamentNotFoundException(String message) {
        super(message);
    }
}