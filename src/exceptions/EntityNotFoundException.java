package exceptions;

public abstract class EntityNotFoundException extends Exception {

    protected EntityNotFoundException(String message) {
        super(message);
    }
}
