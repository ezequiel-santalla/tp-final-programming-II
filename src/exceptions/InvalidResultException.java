package exceptions;

public class InvalidResultException extends RuntimeException {

    public InvalidResultException() {
        super();
    }

    public InvalidResultException(String message) {
        super(message);
    }

}