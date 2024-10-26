package exceptions;

public class IncompleteMatchException extends RuntimeException {

    public IncompleteMatchException() {
        super();
    }

    public IncompleteMatchException(String message) {
        super(message);
    }

}