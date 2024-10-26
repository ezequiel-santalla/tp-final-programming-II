package exceptions;

public class MatchNotFoundException extends RuntimeException {

    public MatchNotFoundException() {
        super();
    }

    public MatchNotFoundException(String message) {
        super(message);
    }

}