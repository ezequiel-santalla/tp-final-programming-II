package exceptions;

public class MatchNotFoundException extends EntityNotFoundException {

    public MatchNotFoundException(Integer id) {
        super("No match was found with the given ID: " + id);
    }

    public MatchNotFoundException(String message) {
        super(message);
    }
}