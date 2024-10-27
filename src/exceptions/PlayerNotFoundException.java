package exceptions;

public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException() {
        super();
    }

    public PlayerNotFoundException(String message) {
        super(message);
    }

}